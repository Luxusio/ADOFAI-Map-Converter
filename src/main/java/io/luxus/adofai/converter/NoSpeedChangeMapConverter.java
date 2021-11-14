package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.List;

public class NoSpeedChangeMapConverter {
    public static CustomLevel convert(String path, double destBpm) throws IOException {

        CustomLevel customLevel = CustomLevelParser.readPath(path);

        double possibleMaxBpm = getPossibleMaxBpm(customLevel.getTiles());
        if (destBpm > possibleMaxBpm) {
            System.err.println("destBpm이 너무 빠릅니다. " + possibleMaxBpm + "bpm 이하로 입력 해 주세요.");
            return null;
        }

        customLevel.getLevelSetting().setBpm(destBpm);
        CustomLevel result = MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> tile.getTileMeta().getTravelAngle() * destBpm /  tile.getTileMeta().getBpm());

        result.getTiles().forEach(tile -> tile.getActions(EventType.SET_SPEED).clear());
        return result;
    }

    public static double getPossibleMaxBpm(List<Tile> tiles) {
        double possibleMaxBpm = Double.POSITIVE_INFINITY;
        for (Tile tile : tiles) {
            TileMeta tileMeta = tile.getTileMeta();
            possibleMaxBpm = Math.min(tileMeta.getPossibleMaxBpm(), possibleMaxBpm);
        }
        return possibleMaxBpm;
    }
}
