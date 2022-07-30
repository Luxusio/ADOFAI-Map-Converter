package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OnlyBpmSetMapConverter implements MapConverter<OnlyBpmSetMapConverter.Parameters> {

    private final I18n i18n;

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        return new Parameters();
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
        return "Only Value SetSpeed";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        return MapConverterBase.convert(customLevel, false,
                applyEach -> applyEach.getOneTimingTiles()
                        .stream()
                        .map(tile -> new Tile.Builder().from(tile))
                        .collect(Collectors.toList()));
    }

    @RequiredArgsConstructor
    public static class Parameters {
    }

}
