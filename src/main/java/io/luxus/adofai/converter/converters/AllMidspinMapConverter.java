package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.helper.AngleHelper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static io.luxus.adofai.converter.MapConverterBase.getSameTimingTiles;
import static io.luxus.lib.adofai.type.TileAngle.MIDSPIN;
import static io.luxus.lib.adofai.type.TileAngle.createNormal;

public class AllMidspinMapConverter implements MapConverter<AllMidspinMapConverter.Parameters> {

    @Override
    public Parameters prepareParameters(Scanner scanner) {

        System.out.print("미드스핀 개수:");
        int midspinAmount = scanner.nextInt();
        scanner.nextLine();

        return new Parameters(midspinAmount);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {

        if (parameters.midspinAmount < 0) {
            System.err.println("midspinAmount 가 너무 작습니다! 0 이상의 값을 입력해주세요!");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel result, Parameters parameters) {
        return "all " + parameters.midspinAmount + " midspin";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        if (impossible(customLevel, parameters)) {
            return null;
        }

        return MapConverterBase.convert(customLevel, false,
                new Function<MapConverterBase.ApplyEach, List<Tile.Builder>>() {

                    private double staticAngle = AngleHelper.getNextStaticAngle(
                            0.0,
                            TileMeta.calculateTotalTravelAngle(getSameTimingTiles(customLevel.getTiles(), 0)),
                            customLevel.getTiles().get(0).getTileMeta().getPlanetAngle(),
                            false);

                    @Override
                    public List<Tile.Builder> apply(MapConverterBase.ApplyEach applyEach) {

                        List<Tile> oneTimingTiles = applyEach.getOneTimingTiles();
                        TileMeta lastTileMeta = oneTimingTiles.get(oneTimingTiles.size() - 1).getTileMeta();
                        double travelAngle = TileMeta.calculateTotalTravelAngle(oneTimingTiles);

                        // create new tiles
                        List<Tile.Builder> newTileBuilders = new ArrayList<>(parameters.midspinAmount + 1);
                        newTileBuilders.add(new Tile.Builder().angle(createNormal(staticAngle)));
                        for (int i = 0; i < parameters.midspinAmount; i++) {
                            staticAngle = AngleHelper.getNextStaticAngle(staticAngle, 0.0, lastTileMeta.getPlanetAngle(), lastTileMeta.isReversed());
                            newTileBuilders.add(new Tile.Builder().angle(MIDSPIN));
                        }
                        staticAngle = AngleHelper.getNextStaticAngle(staticAngle, travelAngle, lastTileMeta.getPlanetAngle(), lastTileMeta.isReversed());

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

    @RequiredArgsConstructor
    public static class Parameters {
        private final int midspinAmount;
    }

}
