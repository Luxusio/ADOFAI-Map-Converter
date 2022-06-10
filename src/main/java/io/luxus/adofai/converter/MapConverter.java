package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;

import java.util.Scanner;

public interface MapConverter<T> {

    T prepareParameters(Scanner scanner);

    boolean impossible(CustomLevel customLevel, T parameters);

    String getLevelPostfix(CustomLevel customLevel, T parameters);

    CustomLevel convert(CustomLevel customLevel, T parameters);

}
