package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

public class BpmMultiplyMapConverter implements MapConverter<BpmMultiplyMapConverter.Parameters> {

    @Override
    public Parameters prepareParameters(Scanner scanner) {

        System.out.print("배수:");
        double multiplier = scanner.nextDouble();
        scanner.nextLine();

        return new Parameters(multiplier);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        double maxPossibleMultiplier = Double.POSITIVE_INFINITY;
        for (Tile tile : customLevel.getTiles()) {
            TileMeta tileMeta = tile.getTileMeta();

            double possibleMultiplier = 360.0 / tileMeta.getTravelAngle();
            maxPossibleMultiplier = Math.min(possibleMultiplier, maxPossibleMultiplier);
        }

        if (parameters.multiplier > maxPossibleMultiplier) {
            System.out.println("배수가 너무 높습니다. " + maxPossibleMultiplier + "배 이하로 해주세요");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel result, Parameters parameters) {
        return "BPM x" + parameters.multiplier;
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        if (impossible(customLevel, parameters)) {
            return null;
        }

        return MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> tile.getTileMeta().getTravelAngle() * parameters.multiplier,
                noop -> {});
    }

    @RequiredArgsConstructor
    public static class Parameters {
        private final double multiplier;
    }

}
