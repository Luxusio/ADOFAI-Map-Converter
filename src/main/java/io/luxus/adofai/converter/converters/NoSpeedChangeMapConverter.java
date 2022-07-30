package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.action.Pause;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

import static io.luxus.adofai.converter.i18n.I18nCode.*;

@RequiredArgsConstructor
public class NoSpeedChangeMapConverter implements MapConverter<NoSpeedChangeMapConverter.Parameters> {

    private final I18n i18n;

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        i18n.print(NO_SPEED_CHANGE_MAP_CONVERTER_DEST_BPM);
        double destBpm = scanner.nextDouble();
        scanner.nextLine();

        return new Parameters(destBpm);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {

        if (parameters.destinationBpm <= 0) {
            i18n.printlnErr(NO_SPEED_CHANGE_MAP_CONVERTER_DEST_BPM_ERROR);
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
        return  "NoSpeedChange " + parameters.destinationBpm + "BPM";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {

        Wrapper<Double> restTravelAngle = new Wrapper<>();

        return MapConverterBase.convertBasedOnTravelAngle(
                customLevel,
                false,
                tile -> {
                    double travelAngle = tile.getTileMeta().getTravelAngle() * parameters.destinationBpm /  tile.getTileMeta().getBpm();
                    restTravelAngle.value = Math.max(travelAngle - 360.0, 0.0);

                    return Math.min(travelAngle, 360.0);
                },
                tileBuilder -> {
                    tileBuilder.removeActions(EventType.SET_SPEED);

                    if (restTravelAngle.value > 0.0) {
                        // TravelAngle 360 + Pause 1 beat = no Pause bug.
                        // That I add 1 to valid duration
                        tileBuilder.addAction(new Pause.Builder()
                                .duration(restTravelAngle.value / 180 + 1)
                                .angleCorrectionDir(0L)
                                .build());
                    }
                },
                customLevelBuilder -> customLevelBuilder.getLevelSettingBuilder().bpm(parameters.destinationBpm));
    }


    @RequiredArgsConstructor
    public static class Parameters {
        private final double destinationBpm;
    }

    class Wrapper<T> {
        T value;
    }

}
