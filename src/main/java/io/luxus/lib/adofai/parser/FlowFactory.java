package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.math.DoubleMath;
import io.luxus.lib.adofai.action.type.TileAngle;
import io.luxus.lib.adofai.util.StringJsonUtil;

import java.util.*;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.Constants.EPSILON;

public class FlowFactory {

    private static final Map<Character, TileAngle> angleCharMap = new HashMap<>();

    static {
        for (TileAngle value : TileAngle.values()) angleCharMap.put(value.getCode(), value);
    }

    public static List<Double> readPathData(JsonNode node) {
        List<TileAngle> pathData = node.asText().chars()
                .mapToObj(c -> (char) c)
                .map(angleCharMap::get)
                .collect(Collectors.toList());

        double staticAngle = 0;
        int size = pathData.size();
        List<Double> angleData = new ArrayList<>(size + 1);
        angleData.add(ANGLE_MID_TILE);

        for (TileAngle angle : pathData) {
            if (angle == TileAngle.NONE) {
                angleData.add(ANGLE_MID_TILE);
            } else {
                if (angle.isRelative()) {
                    staticAngle += angle.getSize();
                    if (staticAngle >= 360.0) staticAngle -= 360.0;
                }
                else staticAngle = angle.getSize();
                angleData.add(staticAngle);
            }
        }

        return angleData;

    }

    public static List<Double> readAngleData(JsonNode node) {
        List<Double> angleData = new ArrayList<>(node.size() + 1);
        angleData.add(0.0);
        Iterator<JsonNode> it = node.elements();
        while (it.hasNext()) {
            JsonNode next = it.next();
            angleData.add(next.asDouble());
        }
        return angleData;
    }

    public static boolean writePathData(StringBuilder sb, List<Double> angleData) {
        sb.append("\t\"pathData\": \"");

        Iterator<Double> it = angleData.stream()
                .map(angle -> angle == ANGLE_MID_TILE ? 0.0 : angle)
                .iterator();
        double prevAngle;
        if (it.hasNext()) prevAngle = it.next();
        else return false;

        while (it.hasNext()) {
            double currAngle = it.next();
            TileAngle tile = getTileAngle(prevAngle, currAngle);
            if (tile == null) return false;

            sb.append(tile.getCode());

            prevAngle = currAngle;
        }


        sb.append("\"");
        return true;
    }

    public static boolean writeAngleData(StringBuilder sb, List<Double> angleData) {
        sb.append("\t\"angleData\": ");
        Iterator<Double> it = angleData.stream()
                .map(angle -> angle == ANGLE_MID_TILE ? 999.0 : angle)
                .iterator();

        if (it.hasNext()) it.next();
        else return false;

        if (it.hasNext()) StringJsonUtil.writeIt(sb, it);

        return true;
    }


    private static TileAngle getTileAngle(double prevAngle, double currAngle) {
        double relativeAngle = currAngle - prevAngle;
        if (relativeAngle < 0) {
            relativeAngle += 360.0;
        }

        for (TileAngle angle : TileAngle.values()) {
            if (DoubleMath.fuzzyEquals(
                    angle.isRelative() ? relativeAngle : currAngle,
                    angle.getSize(), EPSILON))
                return angle;
        }

        return null;
    }

}
