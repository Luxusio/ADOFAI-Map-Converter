package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.util.NumberUtil;

import java.io.IOException;

public class ChaosBpmMapConverter {
    public static CustomLevel convert(String path) throws IOException {
        CustomLevel customLevel = CustomLevelParser.readPath(path);

        double levelBaseBpm = getPossibleRandomBpm(customLevel.getTiles().get(0));
        return OnlyBpmConverterBase.convert(customLevel, 0.0,
                levelBaseBpm, applyEach -> getPossibleRandomBpm(applyEach.getTile()));
    }

    private static double getPossibleRandomBpm(Tile tile) {
        TileMeta tileMeta = tile.getTileMeta();
        return OnlyBpmConverterBase.getPossibleMaxBpm(tileMeta) * NumberUtil.randomMinMax(0.000001, 1.0);
    }

}
