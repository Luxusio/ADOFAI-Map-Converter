package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.util.NumberUtil;

import java.io.IOException;

public class ChaosBpmMapConverter {
    public static CustomLevel convert(String path) throws IOException {
        CustomLevel customLevel = CustomLevelParser.readPath(path);

        double levelBaseBpm = customLevel.getTiles().get(0).getTileMeta()
                .getPossibleMaxBpm() * NumberUtil.randomMinMax(0.000001, 1.0);

        customLevel.getLevelSetting().setBpm(levelBaseBpm);
        return MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> tile.getTileMeta().getTravelAngle() == 0 ? 0 : 360.0 - NumberUtil.randomMinMax(0, 359.99999));
    }
}
