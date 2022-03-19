package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.helper.TileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static io.luxus.adofai.converter.MapConverterBase.getSameTimingTiles;
import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class AllMidspinMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {

        System.out.print("미드스핀 개수:");
        int midspinAmount = scanner.nextInt();
        scanner.nextLine();

        return new Object[] { midspinAmount };
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {

        if ((int) args[0] < 0) {
            System.err.println("midspinAmount 가 너무 작습니다! 0 이상의 값을 입력해주세요!");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel result, Object... args) {
        return "all " + args[0] + " midspin";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        if (impossible(customLevel, args)) {
            return null;
        }

        int midspinAmount = (int) args[0];

        return MapConverterBase.convert(customLevel, false,
                new Function<MapConverterBase.ApplyEach, List<Tile.Builder>>() {

                    private double staticAngle = AngleHelper.getNextStaticAngle(0.0, TileMeta.calculateTotalTravelAngle(getSameTimingTiles(customLevel.getTiles(), 0)), false);

                    @Override
                    public List<Tile.Builder> apply(MapConverterBase.ApplyEach applyEach) {

                        List<Tile> oneTimingTiles = applyEach.getOneTimingTiles();
                        TileMeta lastTileMeta = oneTimingTiles.get(oneTimingTiles.size() - 1).getTileMeta();
                        double travelAngle = TileMeta.calculateTotalTravelAngle(oneTimingTiles);

                        // create new tiles
                        List<Tile.Builder> newTileBuilders = new ArrayList<>(midspinAmount + 1);
                        newTileBuilders.add(new Tile.Builder().angle(staticAngle));
                        for (int i = 0; i < midspinAmount; i++) {
                            staticAngle = AngleHelper.getNextStaticAngle(staticAngle, 0.0, lastTileMeta.isReversed());
                            newTileBuilders.add(new Tile.Builder().angle(ANGLE_MID_TILE));
                        }
                        staticAngle = AngleHelper.getNextStaticAngle(staticAngle, travelAngle, lastTileMeta.isReversed());

                        // add events to new tiles
                        for (int i = 0; i < oneTimingTiles.size(); i++) {
                            Tile timingTile = oneTimingTiles.get(i);
                            newTileBuilders.get(Math.min(i, newTileBuilders.size() - 1))
                                    .combineTile(timingTile);
                        }

                        return newTileBuilders;
                    }
                });
    }

}
