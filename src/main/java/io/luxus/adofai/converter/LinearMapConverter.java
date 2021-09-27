package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Collections;

public class LinearMapConverter {
	public static CustomLevel convert(String path, boolean useCameraOptimization) throws ParseException, IOException {
		return ShapedMapConverter.convert(path, Collections.singletonList(0.0), useCameraOptimization);
	}
}
