package io.luxus.adofai.converter;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.json.simple.parser.ParseException;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEach;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.api.adofai.ADOFAIMap;
import io.luxus.api.adofai.MapData;
import io.luxus.api.adofai.Tile;
import io.luxus.api.adofai.TileData;
import io.luxus.api.adofai.converter.AngleConverter;
import io.luxus.api.adofai.module.MapModule;
import io.luxus.api.adofai.type.TileAngle;

public class ShapedMapConverter {
	public static MapData convert(String path, String shape, boolean useCameraOptimization) throws ParseException, IOException {
		
		ADOFAIMap adofaiMap = MapSpeedConverterBase.getMap(path);
		List<Integer> removedTileList = MapSpeedConverterBase.removeNoneTile(adofaiMap);
		
		return MapSpeedConverterBase.convert(path, removedTileList, adofaiMap, useCameraOptimization, 
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					private int count = 0;
					private int len = shape.length();
					private int floor = 1;
					private double staticAngle = 0;
					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {
						TileAngle nowTileAngle = MapModule.getCharTileAngleBiMap().get(shape.charAt(count));
						count = (count + 1) % len;
						TileAngle nextTileAngle = MapModule.getCharTileAngleBiMap().get(shape.charAt(count));
						boolean skipNone = false;
						if(nextTileAngle == TileAngle.NONE) {
							skipNone = true;
							count = (count + 1) % len;
							nextTileAngle = MapModule.getCharTileAngleBiMap().get(shape.charAt(count));
						}

						Tile tile = applyEach.getTile();
						
						AngleConverter.Result result = AngleConverter.convert(staticAngle, nowTileAngle, nextTileAngle, skipNone ? !tile.isReversed() : tile.isReversed(), !skipNone);
						staticAngle = result.getStaticAngle();
						double relativeAngle = result.getRelativeAngle();
						
						
						double mulValue = relativeAngle / tile.getRelativeAngle();
						double nowTempBPM = mulValue * tile.getBpm();

						double bpmMultiplier = mulValue;
						double angleMultiplier = mulValue;
						
						TileData tileData = new TileData(floor++, nowTileAngle, tile.getActionListMap());
						
						applyEach.getNewTileDataList().add(tileData);
						if(skipNone) applyEach.getNewTileDataList().add(new TileData(floor++, TileAngle.NONE));
						return new ApplyEachReturnValue(
								tileData, nowTempBPM,
								bpmMultiplier, angleMultiplier);
					}
				});
	}
}
