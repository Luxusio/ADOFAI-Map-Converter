package io.luxus.adofai.converter;

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

public class OnlyBpmConverterBase {
    public static CustomLevel convert(CustomLevel customLevel, double minUseBpm, double levelBaseBpm, Function<MapSpeedConverterBase.ApplyEach, Double> bpmMapper) throws IOException {

        // TODO : 무변속 맵 만들기만들기만들기만들기

        List<Tile> tileList = customLevel.getTiles();

        if (tileList.size() <= 1) {
            System.err.println("E> Map is too short!");
            return null;
        }

        double possibleBpm = getPossibleMaxBpm(customLevel.getTiles());

        if (minUseBpm > possibleBpm) {
            System.err.println("minUseBpm이 너무 빠릅니다. " + possibleBpm + "bpm 이하로 입력 해 주세요.");
            return null;
        }

        if (levelBaseBpm > getPossibleMaxBpm(
                customLevel.getTiles().get(0).getTileMeta())) {
            System.err.println("levelBaseBpm이 너무 빠릅니다. " + getPossibleMaxBpm(
                    customLevel.getTiles().get(0).getTileMeta()) + "bpm 이하로 입력 해 주세요.");
            return null;
        }


        customLevel.getLevelSetting().setBpm(levelBaseBpm);
        return MapSpeedConverterBase.convert(customLevel, false,
                new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {

                    private double prevStaticAngle = 0;

                    @Override
                    public MapSpeedConverterBase.ApplyEachReturnValue apply(MapSpeedConverterBase.ApplyEach applyEach) {
                        Tile tile = applyEach.getTile();
                        TileMeta tileMeta = tile.getTileMeta();

                        double newBpm = bpmMapper.apply(applyEach);

                        double mulValue = newBpm / tileMeta.getBpm();
                        double newTravelAngle = tileMeta.getTravelAngle() * mulValue;

                        Tile newTile = tile.getAngle() == ANGLE_MID_TILE ?
                                new Tile(ANGLE_MID_TILE, new HashMap<>(tile.getActionMap())) :
                                new Tile(prevStaticAngle, new HashMap<>(tile.getActionMap()));
                        applyEach.getNewTileList().add(newTile);

                        prevStaticAngle = AngleConverter.getNextStaticAngle(prevStaticAngle, newTravelAngle, tileMeta.isReversed());

                        return new MapSpeedConverterBase.ApplyEachReturnValue(
                                newTile, newBpm,
                                mulValue, mulValue);
                    }
                });
    }

    public static double getPossibleMaxBpm(List<Tile> tiles) {
        double possibleBpm = Double.POSITIVE_INFINITY;
        for (Tile tile : tiles) {
            TileMeta tileMeta = tile.getTileMeta();
            possibleBpm = Math.min(getPossibleMaxBpm(tileMeta), possibleBpm);
        }
        return possibleBpm;
    }

    public static double getPossibleMaxBpm(TileMeta tileMeta) {
        return tileMeta.getBpm() * 360 / tileMeta.getTravelAngle();
    }

}
