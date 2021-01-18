package io.luxus.adofai.converter;

import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.MapData;

public class LinearMapConverter {
	public static MapData convert(MapData mapData) throws ParseException {
		return ShapedMapConverter.convert(mapData, "R");
	}
}
