package io.luxus.adofai.converter;

import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.MapData;

public class LinearMapConverter {
	public static MapData convert(MapData mapData) throws ParseException {
		return ShapedMapConverter.convert(mapData, "R");
//		ADOFAIMap adofaiMap = new ADOFAIMap(mapData);
//		
//		List<Tile> tileList = adofaiMap.getTileList();
//		List<TileData> tileDataList = adofaiMap.getTileDataList();
//		
//		Iterator<Tile> tileIt = tileList.iterator();
//		Iterator<TileData> tileDataIt = tileDataList.iterator();
//		
//		tileIt.next();
//		tileDataIt.next();
//		
//		TileData prev = null;
//		while(tileDataIt.hasNext()) {
//			tileIt.next();
//			TileData now = tileDataIt.next();
//			
//			if(now.getTileAngle() == TileAngle.NONE) {
//				tileIt.remove();
//				tileDataIt.remove();
//				prev.addActionListMap(now.getActionListMap());
//			}
//			prev = now;
//		}
//		
//		return MapSpeedConverterBase.convert(adofaiMap, 
//				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
//					@Override
//					public ApplyEachReturnValue apply(ApplyEach applyEach) {
//
//						int floor = applyEach.getFloor();
//						Tile tile = applyEach.getTile();
//						
//						double result = 180.0 / tile.getRelativeAngle();
//						double nowTempBPM = result * tile.getBpm();
//
//						double bpmMultiplier = result;
//						double angleMultiplier = result;
//
//						return new ApplyEachReturnValue(
//								new TileData(floor, TileAngle._0, tile.getActionListMap()), nowTempBPM,
//								bpmMultiplier, angleMultiplier);
//					}
//				});
	}
}
