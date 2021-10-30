package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;

public class NoSpeedChangeMapConverter {
    public static CustomLevel convert(String path, double destBpm) throws IOException {
        return OnlyBpmConverterBase.convert(CustomLevelParser.readPath(path), destBpm, destBpm, applyEach -> destBpm);
    }
}
