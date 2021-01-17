package io.luxus.adofai.converter;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.json.simple.parser.ParseException;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEach;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.api.adofai.ADOFAIMap;
import io.luxus.api.adofai.MapData;
import io.luxus.api.adofai.Tile;
import io.luxus.api.adofai.TileData;
import io.luxus.api.adofai.converter.RelativeAngleConverter;
import io.luxus.api.adofai.module.MapModule;
import io.luxus.api.adofai.type.TileAngle;

public class ShapedMapConverter {
	public static MapData convert(MapData mapData, String shape) throws ParseException {
		
		ADOFAIMap adofaiMap = new ADOFAIMap(mapData);
		
		List<Tile> tileList = adofaiMap.getTileList();
		List<TileData> tileDataList = adofaiMap.getTileDataList();
		
		Iterator<Tile> tileIt = tileList.iterator();
		Iterator<TileData> tileDataIt = tileDataList.iterator();
		
		tileIt.next();
		tileDataIt.next();
		
		TileData prev = null;
		while(tileDataIt.hasNext()) {
			tileIt.next();
			TileData now = tileDataIt.next();
			
			if(now.getTileAngle() == TileAngle.NONE) {
				tileIt.remove();
				tileDataIt.remove();
				prev.addActionListMap(now.getActionListMap());
			}
			prev = now;
		}
		
		return MapSpeedConverterBase.convert(adofaiMap,
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					private int count = 0;
					private int len = shape.length();
					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {
						TileAngle nowTileAngle = MapModule.getCharTileAngleBiMap().get(shape.charAt(count));
						count = (count + 1) % len;
						TileAngle nextTileAngle = MapModule.getCharTileAngleBiMap().get(shape.charAt(count));
						
						int floor = applyEach.getFloor();
						Tile tile = applyEach.getTile();
						
						double newRelativeAngle = RelativeAngleConverter.convert(nowTileAngle, nextTileAngle, null, tile.isReversed());
						
						double result = newRelativeAngle / tile.getRelativeAngle();
						double nowTempBPM = result * tile.getBpm();

						double bpmMultiplier = result;
						double angleMultiplier = result;

						return new ApplyEachReturnValue(
								new TileData(floor, nowTileAngle, tile.getActionListMap()), nowTempBPM,
								bpmMultiplier, angleMultiplier);
					}
				});
	}
}
