package io.luxus.adofai.converter;

import io.luxus.adofai.converter.converters.*;
import io.luxus.adofai.converter.converters.effect.NonEffectMapConverter;
import io.luxus.adofai.converter.converters.effect.OnlyBpmSetMapConverter;
import io.luxus.adofai.converter.converters.effect.TransparentMapConverter;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

import static io.luxus.adofai.converter.ConverterType.*;

public class MapConverterDispatcher {

    private final Map<ConverterType, MapConverter> converterMap = new EnumMap<>(ConverterType.class);

    public MapConverterDispatcher() {
        converterMap.put(OUTER, new OuterMapConverter());
        converterMap.put(LINEAR, new LinearMapConverter());
        converterMap.put(SHAPED, new ShapedMapConverter());
        converterMap.put(TWIRL_RATIO, new TwirlConverter());
        converterMap.put(NO_EFFECT, new NonEffectMapConverter());
        converterMap.put(TRANSPARENCY, new TransparentMapConverter());
        converterMap.put(BPM_VALUE_ONLY, new OnlyBpmSetMapConverter());
        converterMap.put(NO_SPEED_CHANGE, new NoSpeedChangeMapConverter());
        converterMap.put(BPM_MULTIPLIER, new BpmMultiplyMapConverter());
        converterMap.put(CHAOS, new ChaosBpmMapConverter());
        converterMap.put(MIDSPIN, new AllMidspinMapConverter());
        converterMap.put(PSEUDO, new PseudoMapConverter());
    }

    public Object[] prepareParameters(ConverterType type, Scanner scanner) {
        MapConverter mapConverter = converterMap.get(type);
        if (mapConverter == null) {
            System.err.println("잘못된 converterType 입니다. (" + type + ")");
            return null;
        }

        return mapConverter.prepareParameters(scanner);
    }

    public void convertMapAndSave(String path, ConverterType type, Object... args) {
        MapConverter mapConverter = converterMap.get(type);
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

    public CustomLevel convertCustomLevel(String path, MapConverter mapConverter, Object... args) {
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

    public String getSavePath(String path, MapConverter mapConverter, CustomLevel customLevel, Object... args) {
        if (path.endsWith(".adofai")) {
            path = path.substring(0, path.length() - 7);
        }
        return path + " " + mapConverter.getLevelPostfix(customLevel, args) + ".adofai";
    }

}
