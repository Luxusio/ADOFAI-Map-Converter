package io.luxus.adofai.converter;

import io.luxus.lib.adofai.Constants;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.converter.AngleConverter;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class NoSpeedChangeMapConverter {
    public static CustomLevel convert(String path, double bpm) throws IOException {

        // TODO : 무변속 맵 만들기만들기만들기만들기

        CustomLevel customLevel = CustomLevelParser.readPath(path);
        List<Tile> tileList = customLevel.getTiles();

        if (tileList.size() <= 1) {
            System.err.println("E> Map is too short!");
            return null;
        }

        double possibleBpm = Double.POSITIVE_INFINITY;
        for (Tile tile : customLevel.getTiles()) {
            TileMeta tileMeta = tile.getTileMeta();

            double currentPossibleBpm = tileMeta.getBpm() * 360 / tileMeta.getTravelAngle();
            possibleBpm = Math.min(currentPossibleBpm, possibleBpm);
        }

        if (bpm > possibleBpm) {
            System.err.println("bpm이 너무 빠릅니다. " + possibleBpm + "bpm 이하로 입력 해 주세요.");
            return null;
        }

        customLevel.getLevelSetting().setBpm(bpm);
        return MapSpeedConverterBase.convert(customLevel, false,
                new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {

                    private double prevStaticAngle = 0;

                    @Override
                    public MapSpeedConverterBase.ApplyEachReturnValue apply(MapSpeedConverterBase.ApplyEach applyEach) {
                        Tile tile = applyEach.getTile();
                        TileMeta tileMeta = tile.getTileMeta();

                        double mulValue = bpm / tileMeta.getBpm();
                        double newTravelAngle = tileMeta.getTravelAngle() * mulValue;

                        Tile newTile = tile.getAngle() == ANGLE_MID_TILE ?
                                new Tile(ANGLE_MID_TILE, new HashMap<>(tile.getActionMap())) :
                                new Tile(prevStaticAngle, new HashMap<>(tile.getActionMap()));
                        applyEach.getNewTileList().add(newTile);

                        System.out.println(prevStaticAngle + ", " + newTravelAngle + ", " + tileMeta.getBpm() + ", " + tileMeta.getTravelAngle());
                        prevStaticAngle = AngleConverter.getNextStaticAngle(prevStaticAngle, newTravelAngle, tileMeta.isReversed());

                        return new MapSpeedConverterBase.ApplyEachReturnValue(
                                newTile, bpm,
                                mulValue, mulValue);
                    }
                });
    }
}
