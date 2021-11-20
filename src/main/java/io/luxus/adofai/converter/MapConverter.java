package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;

import java.util.Scanner;

public interface MapConverter {

    Object[] prepareParameters(Scanner scanner);

    boolean impossible(CustomLevel customLevel, Object... args);

    String getLevelPostfix(CustomLevel customLevel, Object... args);

    CustomLevel convert(CustomLevel customLevel, Object... args);

}
