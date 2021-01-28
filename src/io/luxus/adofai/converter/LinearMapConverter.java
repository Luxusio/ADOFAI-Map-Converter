package io.luxus.adofai.converter;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.MapData;

public class LinearMapConverter {
	public static MapData convert(String path, boolean useCameraOptimization) throws ParseException, IOException {
		return ShapedMapConverter.convert(path, "R", useCameraOptimization);
	}
}
