package io.luxus.adofai.converter.converters;

import io.luxus.lib.adofai.CustomLevel;

import java.util.*;

public class LinearMapConverter extends ShapedMapConverter {

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
        return "Linear";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        return super.convert(customLevel, "", null, Collections.singletonList(0.0));
    }

}
