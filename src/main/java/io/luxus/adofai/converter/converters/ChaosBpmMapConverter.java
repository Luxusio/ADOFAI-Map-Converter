package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.util.NumberUtil;

import java.util.Scanner;

public class ChaosBpmMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {
        return new Object[0];
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {
        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Object... args) {
        return "Chaos BPM";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        double levelBaseBpm = customLevel.getTiles().get(0).getTileMeta()
                .getPossibleMaxBpm() * NumberUtil.randomMinMax(0.000001, 1.0);

        return MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> tile.getTileMeta().getTravelAngle() == 0 ? 0 : 360.0 - NumberUtil.randomMinMax(0, 359.99999),
                noop -> {},
                customLevelBuilder -> customLevelBuilder.getLevelSettingBuilder()
                        .bpm(levelBaseBpm));
    }

}
