package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;

import java.util.Scanner;

public class BpmMultiplyMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {

        System.out.print("배수:");
        double multiplier = scanner.nextDouble();
        scanner.nextLine();

        return new Object[] { multiplier };
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {
        double multiplier = (double) args[0];

        double maxPossibleMultiplier = Double.POSITIVE_INFINITY;
        for (Tile tile : customLevel.getTiles()) {
            TileMeta tileMeta = tile.getTileMeta();

            double possibleMultiplier = 360.0 / tileMeta.getTravelAngle();
            maxPossibleMultiplier = Math.min(possibleMultiplier, maxPossibleMultiplier);
        }

        if (multiplier > maxPossibleMultiplier) {
            System.out.println("배수가 너무 높습니다. " + maxPossibleMultiplier + "배 이하로 해주세요");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel result, Object... args) {
        return "BPM x" + args[0];
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        if (impossible(customLevel, args)) {
            return null;
        }

        double multiplier = (double) args[0];

        return MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> tile.getTileMeta().getTravelAngle() * multiplier);
    }
}
