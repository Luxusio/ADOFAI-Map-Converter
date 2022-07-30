package io.luxus.adofai.converter;

import io.luxus.adofai.converter.converters.*;
import io.luxus.adofai.converter.converters.effect.NonEffectMapConverter;
import io.luxus.adofai.converter.converters.effect.OnlyBpmSetMapConverter;
import io.luxus.adofai.converter.converters.effect.TransparentMapConverter;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapConverterDispatcher {

    private final Map<Class<? extends MapConverter<?>>, MapConverter<?>> converterMap;

    public MapConverterDispatcher(I18n i18n) {
        this.converterMap = Collections.unmodifiableMap(Stream.of(
                OuterMapConverter.class,
                LinearMapConverter.class,
                ShapedMapConverter.class,
                TwirlConverter.class,
                NonEffectMapConverter.class,
                TransparentMapConverter.class,
                OnlyBpmSetMapConverter.class,
                NoSpeedChangeMapConverter.class,
                BpmMultiplyMapConverter.class,
                ChaosBpmMapConverter.class,
                AllMidspinMapConverter.class,
                PseudoMapConverter.class,
                PlanetNumberMapConverter.class
        ).collect(Collectors.toMap(
                Function.identity(),
                clazz -> {
                    try {
                        return clazz.getConstructor(I18n.class)
                                .newInstance(i18n);
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })));
    }

    public <CT extends MapConverter<T>, T> T prepareParameters(Class<CT> type, Scanner scanner) {
        @SuppressWarnings("unchecked")
        MapConverter<T> mapConverter = (MapConverter<T>) converterMap.get(type);
        if (mapConverter == null) {
            System.err.println("잘못된 converterType 입니다. (" + type + ")");
            return null;
        }

        return mapConverter.prepareParameters(scanner);
    }

    public <CT extends MapConverter<T>, T> void convertMapAndSave(String path, Class<CT> type, T args) {
        @SuppressWarnings("unchecked")
        MapConverter<T> mapConverter = (MapConverter<T>) converterMap.get(type);
        if (mapConverter == null) {
            System.err.println("잘못된 converterType 입니다. (" + type + ")");
            return;
        }

        CustomLevel result = convertCustomLevel(path, mapConverter, args);
        if (result == null) {
            System.err.println("변환 실패 (" + path + ")");
            return;
        }

        String savePath = getSavePath(path, mapConverter, result, args);
        try {
            CustomLevelParser.write(result, savePath);
        } catch (IOException exception) {
            System.err.println("저장 실패 (" + savePath + ")");
            exception.printStackTrace();
        }
    }

    private <T> CustomLevel convertCustomLevel(String path, MapConverter<T> mapConverter, T args) {
        CustomLevel customLevel;
        try {
            customLevel = CustomLevelParser.readPath(path);
        } catch (IOException exception) {
            System.err.println("customLevel을 읽을 수 없습니다. (path=" + path + ")");
            exception.printStackTrace();
            return null;
        }

        if (mapConverter.impossible(customLevel, args)) {
            return null;
        }

        return mapConverter.convert(customLevel, args);
    }

    private <T> String getSavePath(String path, MapConverter<T> mapConverter, CustomLevel customLevel, T args) {
        if (path.endsWith(".adofai")) {
            path = path.substring(0, path.length() - 7);
        }
        return path + " " + mapConverter.getLevelPostfix(customLevel, args) + ".adofai";
    }

}
