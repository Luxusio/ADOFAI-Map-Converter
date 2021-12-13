package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.type.TileAngle;
import io.luxus.lib.adofai.util.NumberUtil;
import io.luxus.lib.adofai.util.StringJsonUtil;

import java.util.*;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.util.NumberUtil.generalizeAngleExclude360;

public class FlowFactory {

    private static final Map<Character, TileAngle> angleCharMap = new HashMap<>();

    static {
        for (TileAngle value : TileAngle.values()) angleCharMap.put(value.getCode(), value);
    }

    public static List<Double> readPathData(JsonNode node) {
        return readPathData(node.asText());
    }

    public static List<Double> readPathData(String nodeText) {
        List<TileAngle> pathData = nodeText.chars()
                .mapToObj(c -> (char) c)
                .map(angleCharMap::get)
                .collect(Collectors.toList());

        for (TileAngle angle : pathData) {
            if (angle == null) {
                return null;
            }
        }

        return pathDataToAngleData(pathData);
    }

    public static List<Double> pathDataToAngleData(List<TileAngle> pathData) {
        double staticAngle = 0;
        List<Double> angleData = new ArrayList<>(pathData.size() + 1);

        for (TileAngle angle : pathData) {
            if (angle == TileAngle.NONE) {
                angleData.add(ANGLE_MID_TILE);
            } else {
                if (angle.isRelative()) {
                    staticAngle = generalizeAngleExclude360(staticAngle + 180 - angle.getSize());
                }
                else staticAngle = angle.getSize();
                angleData.add(staticAngle);
            }
        }

        return angleData;
    }

    public static List<Double> readAngleData(JsonNode node) {
        List<Double> angleData = new ArrayList<>(node.size() + 1);
        Iterator<JsonNode> it = node.elements();
        while (it.hasNext()) {
            JsonNode next = it.next();
            angleData.add(next.asDouble());
        }
        return angleData;
    }

    public static boolean writePathData(StringBuilder sb, List<Double> angleData) {

        StringBuilder tempSb = new StringBuilder();
        tempSb.append("\t\"pathData\": \"");

        Iterator<Double> it = angleData.stream()
                .map(angle -> angle == ANGLE_MID_TILE ? 999.0 : angle)
                .iterator();

        Double currAngle;
        if (it.hasNext()) it.next();
        else return false;
        if (it.hasNext()) currAngle = it.next();
        else return false;

        while (it.hasNext()) {
            Double nextAngle = it.next();
            TileAngle tileAngle = getCurrTileAngle(currAngle, nextAngle);
            if (tileAngle == null) return false;

            tempSb.append(tileAngle.getCode());

            currAngle = nextAngle;
        }
        TileAngle tileAngle = getCurrTileAngle(currAngle, 999.0);
        if (tileAngle == null) return false;
        tempSb.append(tileAngle.getCode());

        tempSb.append("\"");
        sb.append(tempSb);
        return true;
    }

    public static void writeAngleData(StringBuilder sb, List<Double> angleData) {
        sb.append("\t\"angleData\": ");
        Iterator<Double> it = angleData.stream()
                .map(angle -> angle == ANGLE_MID_TILE ? 999.0 : angle)
                .iterator();

        if (it.hasNext()) it.next();
        StringJsonUtil.writeIt(sb, it);
    }


    private static TileAngle getCurrTileAngle(Double currAngle, Double nextAngle) {

        if (NumberUtil.fuzzyEquals(currAngle, 999.0)) {
            return TileAngle.NONE;
        }

        double relativeAngle = generalizeAngleExclude360(180 - nextAngle + currAngle);

        for (TileAngle angle : TileAngle.values()) {
            if (NumberUtil.fuzzyEquals(
                    angle.isRelative() ? relativeAngle : currAngle,
                    angle.getSize()))
                return angle;
        }

        return null;
    }

}
