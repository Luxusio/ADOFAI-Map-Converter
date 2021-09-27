package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.type.EventType;

import java.util.*;
import java.util.stream.Collectors;

public class CustomLevelFactory {

    public static CustomLevel read(JsonNode node) {

        JsonNode pathDataNode = node.get("pathData");
        JsonNode angleDataNode = node.get("angleData");
        JsonNode settingsNode = node.get("settings");
        JsonNode actionsNode = node.get("actions");

        if (pathDataNode == null && angleDataNode == null) {
            throw new IllegalStateException("There's no pathData, angleData");
        }

        if (settingsNode == null) {
            throw new IllegalStateException("settings not found");
        }

        if (actionsNode == null) {
            throw new IllegalStateException("actions not found");
        }

        List<Double> angleData = pathDataNode != null ?
                FlowFactory.readPathData(pathDataNode) :
                FlowFactory.readAngleData(angleDataNode);

        List<Tile> tiles = angleData.stream()
                .map(Tile::new)
                .collect(Collectors.toList());

        LevelSetting levelSetting = LevelSettingFactory.read(settingsNode);

        Iterator<JsonNode> it = actionsNode.elements();
        while (it.hasNext()) {
            JsonNode jsonNode = it.next();

            final int floor = jsonNode.get("floor").asInt();
            tiles.get(floor).addAction(ActionFactory.read(jsonNode));
        }

        return new CustomLevel(levelSetting, tiles);
    }

    public static String write(CustomLevel customLevel) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        List<Double> angleData = customLevel.getTiles().stream()
                .map(Tile::getAngle)
                .collect(Collectors.toList());
        if (customLevel.getLevelSetting().getVersion() >= 5) {
            FlowFactory.writeAngleData(sb, angleData);
        }
        else {
            FlowFactory.writePathData(sb, angleData);
        }

        sb.append(",\n");

        LevelSettingFactory.write(sb, customLevel.getLevelSetting());

        sb.append(",\n\t\"actions\":\n\t[");
        List<Tile> tiles = customLevel.getTiles();

        Set<EventType> eventTypes = new HashSet<>(Arrays.asList(EventType.values()));
        List<EventType> eventTypeOrder = new ArrayList<>();
        eventTypeOrder.add(EventType.SET_SPEED);
        eventTypeOrder.add(EventType.TWIRL);
        eventTypeOrder.add(EventType.CHECK_POINT);

        for (EventType eventType : eventTypeOrder) eventTypes.remove(eventType);
        eventTypeOrder.addAll(eventTypes);

        int size = tiles.size();
        boolean isFirst = true;
        for (int floor = 0; floor < size; floor++) {
            Map<EventType, List<Action>> actionMap = tiles.get(floor).getActionMap();

            for (EventType eventType : eventTypeOrder) {
                List<Action> actions = actionMap.get(eventType);
                if (actions != null) {
                    for (Action action : actions) {
                        if (isFirst) {
                            isFirst = false;
                            sb.append('\n');
                        }
                        else {
                            sb.append(",\n");
                        }
                        ActionFactory.write(sb, floor, action);
                    }
                }
            }
        }

        sb.append("\n\t]\n}\n");
        return sb.toString();
    }

}