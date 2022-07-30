package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.type.TileAngle;

import java.util.Scanner;

import static java.util.Collections.singletonList;

public class LinearMapConverter extends ShapedMapConverter {

    public static final ShapedMapConverter.Parameters PARAMETERS = new Parameters("", null, singletonList(TileAngle.ZERO));

    public LinearMapConverter(I18n i18n) {
        super(i18n);
    }

    @Override
    public ShapedMapConverter.Parameters prepareParameters(Scanner scanner) {
        return PARAMETERS;
    }

    @Override
    public boolean impossible(CustomLevel customLevel, ShapedMapConverter.Parameters args) {
        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, ShapedMapConverter.Parameters args) {
        return "Linear";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, ShapedMapConverter.Parameters parameters) {
        return super.convert(customLevel, PARAMETERS);
    }

}
