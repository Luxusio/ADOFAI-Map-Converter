package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;

import java.util.Scanner;
import java.util.stream.Collectors;

public class OnlyBpmSetMapConverter implements MapConverter {
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
        return "Only Value SetSpeed";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        return MapConverterBase.convert(customLevel, false,
                applyEach -> applyEach.getOneTimingTiles()
                        .stream()
                        .map(tile -> new Tile.Builder().from(tile))
                        .collect(Collectors.toList()));
    }

}
