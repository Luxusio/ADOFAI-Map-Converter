package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TileAngle;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static io.luxus.adofai.converter.i18n.I18nCode.*;

@RequiredArgsConstructor
public class PseudoMapConverter implements MapConverter<PseudoMapConverter.Parameters> {

    private final I18n i18n;

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        i18n.print(PSEUDO_MAP_CONVERTER_PSEUDO_AMOUNT);
        int pseudo = scanner.nextInt();
        scanner.nextLine();

        i18n.print(PSEUDO_MAP_CONVERTER_PSEUDO_MAX_ANGLE);
        double pseudoAngle = scanner.nextDouble();
        scanner.nextLine();

        i18n.print(PSEUDO_MAP_CONVERTER_REMOVE_COLOR_TRACK_QUESTION);
        boolean removeColorTrack = scanner.nextLine().equalsIgnoreCase("y");

        return new Parameters(pseudo, pseudoAngle, removeColorTrack);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        if (parameters.pseudo < 1) {
            i18n.printlnErr(PSEUDO_MAP_CONVERTER_PSEUDO_AMOUNT_ERROR);
            return true;
        }
        if (parameters.pseudoAngle <= 0) {
            i18n.printlnErr(PSEUDO_MAP_CONVERTER_PSEUDO_MAX_ANGLE_ERROR);
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
