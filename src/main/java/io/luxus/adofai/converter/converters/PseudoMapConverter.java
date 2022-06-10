package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.type.TileAngle;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PseudoMapConverter implements MapConverter<PseudoMapConverter.Parameters> {

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        System.out.print("동타 수 : ");
        int pseudo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("동타 최대 각도 : ");
        double pseudoAngle = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("colorTrack을 제거하시겠습니까? (y/n) (ColorTrack이 많을 시 게임이 멈출 수 있습니다.) : ");
        boolean removeColorTrack = scanner.nextLine().equalsIgnoreCase("y");

        return new Parameters(pseudo, pseudoAngle, removeColorTrack);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        if (parameters.pseudo < 1) {
            System.err.println("Pseudo 값이 너무 낮습니다! 1 이상으로 해주세요!");
            return true;
        }
        if (parameters.pseudoAngle <= 0) {
            System.err.println("동타 최대 각도는 0도보다 커야합니다.");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
        return parameters.pseudo + " Pseudo";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        if (impossible(customLevel, parameters)) {
            return null;
        }

//        int pseudo = (int) args[0];
//        double pseudoAngle = (double) args[1];
//        boolean removeColorTrackEvents = (boolean) args[2];

        return MapConverterBase.convert(customLevel, false,
                applyEach -> {
                    List<Tile> oneTimingTiles = applyEach.getOneTimingTiles();
                    List<Tile.Builder> newTileBuilders = oneTimingTiles.stream()
                            .map(tile -> new Tile.Builder().from(tile))
                            .collect(Collectors.toList());

                    double travelAngle = TileMeta.calculateTotalTravelAngle(oneTimingTiles);

                    double eachHitTravelAngle = Math.min(parameters.pseudoAngle, travelAngle / 2 / parameters.pseudo);

                    TileMeta lastTileMeta = oneTimingTiles.get(oneTimingTiles.size() - 1).getTileMeta();
                    double currStaticAngle = lastTileMeta.getStaticAngle();

                    if (oneTimingTiles.size() % 2 == 0) {
                        currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, 0.0, lastTileMeta.getPlanetAngle(), lastTileMeta.isReversed());
                    }

                    currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, eachHitTravelAngle, lastTileMeta.getPlanetAngle(), lastTileMeta.isReversed());

                    if (parameters.removeColorTrackEvents) {
                        newTileBuilders.forEach(newTileBuilder -> {
                            newTileBuilder.removeActions(EventType.RECOLOR_TRACK);
                            newTileBuilder.removeActions(EventType.COLOR_TRACK);
                        });
                    }

                    for (int i = 1; i < parameters.pseudo; i++) {
                        newTileBuilders.add(new Tile.Builder().angle(TileAngle.createNormal(currStaticAngle)));
                        newTileBuilders.add(new Tile.Builder().angle(TileAngle.MIDSPIN));
                        currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, eachHitTravelAngle, lastTileMeta.getPlanetAngle(), lastTileMeta.isReversed());
                        currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, 0, lastTileMeta.getPlanetAngle(), lastTileMeta.isReversed());
                    }

                    return newTileBuilders;
                });
    }

    @RequiredArgsConstructor
    public static class Parameters {
        private final int pseudo;
        private final double pseudoAngle;
        private final boolean removeColorTrackEvents;
    }

}
