package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;

public class BpmMultiplyMapConverter {
    public static CustomLevel convert(String path, double multiplier) throws IOException {
        CustomLevel customLevel = CustomLevelParser.readPath(path);

        double maxPossibleMultiplier = Double.POSITIVE_INFINITY;
        for (Tile tile : customLevel.getTiles()) {
            TileMeta tileMeta = tile.getTileMeta();

            double possibleMultiplier = 360.0 / tileMeta.getTravelAngle();
            maxPossibleMultiplier = Math.min(possibleMultiplier, maxPossibleMultiplier);
        }

        if (multiplier > maxPossibleMultiplier) {
            System.out.println("배수가 너무 높습니다. " + maxPossibleMultiplier + "배 이하로 해주세요");
            return null;
        }


        return OnlyBpmConverterBase.convert(customLevel, 0,
                customLevel.getLevelSetting().getBpm() * multiplier,
                applyEach -> applyEach.getTile().getTileMeta().getBpm() * multiplier);
    }
}
