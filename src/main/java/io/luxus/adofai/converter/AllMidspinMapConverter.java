package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.helper.TileHelper;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static io.luxus.adofai.converter.MapConverterBase.getSameTimingTiles;
import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class AllMidspinMapConverter {
    public static CustomLevel convert(String path, int midspinAmount) throws IOException {

        if (midspinAmount < 0) {
            System.err.println("midspinAmount 가 너무 작습니다! 0 이상의 값을 입력해주세요!");
            return null;
        }

        // TODO : 집가서 test 하기
        CustomLevel customLevel = CustomLevelParser.readPath(path);

        return MapConverterBase.convert(customLevel, false,
                new Function<MapConverterBase.ApplyEach, List<Tile>>() {

                    private double staticAngle = AngleHelper.getNextStaticAngle(0.0, TileMeta.calculateTotalTravelAngle(getSameTimingTiles(customLevel.getTiles(), 0)), false);

                    @Override
                    public List<Tile> apply(MapConverterBase.ApplyEach applyEach) {

                        List<Tile> oneTimingTiles = applyEach.getOneTimingTiles();
                        TileMeta lastTileMeta = oneTimingTiles.get(oneTimingTiles.size() - 1).getTileMeta();
                        double travelAngle = TileMeta.calculateTotalTravelAngle(oneTimingTiles);

                        // create new tiles
                        List<Tile> newTiles = new ArrayList<>(midspinAmount + 1);
                        newTiles.add(new Tile(staticAngle));
                        for (int i = 0; i < midspinAmount; i++) {
                            staticAngle = AngleHelper.getNextStaticAngle(staticAngle, 0.0, lastTileMeta.isReversed());
                            newTiles.add(new Tile(ANGLE_MID_TILE));
                        }
                        staticAngle = AngleHelper.getNextStaticAngle(staticAngle, travelAngle, lastTileMeta.isReversed());

                        // add events to new tiles
                        for (int i = 0; i < oneTimingTiles.size(); i++) {
                            Tile timingTile = oneTimingTiles.get(i);
                            Tile newTile = newTiles.get(Math.min(i, newTiles.size() - 1));

                            TileHelper.combineTile(newTile, timingTile);
                        }

                        return newTiles;
                    }
                });
    }

}
