package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.util.NumberUtil;

import java.util.Scanner;
import java.util.function.Function;

public class ChaosBpmMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {

        System.out.print("진동할 각도 비율:");
        double rate = scanner.nextDouble();
        scanner.nextLine();

        return new Object[] { rate };
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {
        double rate = (double) args[0];

        return rate >= 1.0 || rate < 0.0;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Object... args) {
        return "Chaos Rate " + args[0]  + "%";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        double rate = (double) args[0];

        double levelBaseBpm = customLevel.getTiles().get(0).getTileMeta()
                .getPossibleMaxBpm() * NumberUtil.randomMinMax(0.000001, 1.0);

        return MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> {
                    double travelAngle = tile.getTileMeta().getTravelAngle();
                    return travelAngle == 0 ? 0.0 :
                            travelAngle + NumberUtil.randomMinMax(-rate, rate) * travelAngle;
                },
                noop -> {},
                customLevelBuilder -> customLevelBuilder.getLevelSettingBuilder()
                        .bpm(levelBaseBpm));
    }

}
