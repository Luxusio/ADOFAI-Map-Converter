package io.luxus.lib.adofai;

import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.util.NumberUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

@ToString
@Getter
public class Tile {

    private Double angle = 0.0;
    private Map<EventType, List<Action>> actionMap;
    private final TileMeta tileMeta = new TileMeta();

    public Tile() {
        this.actionMap = new HashMap<>();
    }

    public Tile(Double angle) {
        this();
        setAngle(angle);
    }

    public Tile(Double angle, Map<EventType, List<Action>> actionMap) {
        this(angle);
        this.actionMap = actionMap;
    }

    public boolean addAction(Action action) {
        List<Action> actions = getActions(action.getEventType());
        if (action.getEventType().isSingleOnly() && !actions.isEmpty()) {
            return false;
        }
        actions.add(action);
        return true;
    }

    public List<Action> getActions(EventType eventType) {
        return actionMap.computeIfAbsent(eventType, k -> new ArrayList<>());
    }

    public void setAngle(Double angle) {
        if (angle != null && NumberUtil.fuzzyEquals(angle, 999.0)) angle = ANGLE_MID_TILE;
        this.angle = angle;
    }

    public void update(LevelSetting levelSetting, Double nextAngle) {
        this.tileMeta.update(actionMap, levelSetting, nextAngle);
    }

    public void update(TileMeta prevTileMeta, Double nextStaticAngle) {
        this.tileMeta.update(actionMap, angle, prevTileMeta, nextStaticAngle);
    }

}
