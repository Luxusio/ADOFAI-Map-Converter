package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.util.NumberUtil;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

import static io.luxus.adofai.converter.i18n.I18nCode.CHAOS_BPM_MAP_CONVERTER_VIBRATE_RATE;

@RequiredArgsConstructor
public class ChaosBpmMapConverter implements MapConverter<ChaosBpmMapConverter.Parameters> {

    private final I18n i18n;

    @Override
    public Parameters prepareParameters(Scanner scanner) {

        i18n.print(CHAOS_BPM_MAP_CONVERTER_VIBRATE_RATE);
        double rate = scanner.nextDouble();
        scanner.nextLine();

        return new Parameters(rate);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        return parameters.vibrationRate >= 1.0 || parameters.vibrationRate < 0.0;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
        return "Chaos Rate " + parameters.vibrationRate + "%";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        double levelBaseBpm = customLevel.getTiles().get(0).getTileMeta()
                .getPossibleMaxBpm() * NumberUtil.randomMinMax(0.000001, 1.0);

        return MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> {
                    double travelAngle = tile.getTileMeta().getTravelAngle();
                    return travelAngle == 0 ? 0.0 :
                            travelAngle + NumberUtil.randomMinMax(-parameters.vibrationRate, parameters.vibrationRate) * travelAngle;
                },
                noop -> {},
                customLevelBuilder -> customLevelBuilder.getLevelSettingBuilder()
                        .bpm(levelBaseBpm));
    }

    @RequiredArgsConstructor
    public static class Parameters {
        private final double vibrationRate;
    }

}
