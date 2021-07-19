package io.luxus.adofai.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.json.simple.parser.ParseException;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEach;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.api.adofai.ADOFAIMap;
import io.luxus.api.adofai.MapData;
import io.luxus.api.adofai.Tile;
import io.luxus.api.adofai.TileData;
import io.luxus.api.adofai.action.Action;
import io.luxus.api.adofai.action.MoveTrack;
import io.luxus.api.adofai.type.EventType;

public class TransposeMapConverter {
	
	public static MapData convert(String path, int opacity) throws ParseException, IOException {
		ADOFAIMap adofaiMap = MapSpeedConverterBase.getMap(path);
		
		Tile zeroTile = adofaiMap.getTileList().get(0);
		removeActions(zeroTile, opacity);
		
		zeroTile.getActionList(EventType.MOVE_TRACK).add(getTransposeMoveTrack(opacity));
		
		return MapSpeedConverterBase.convert(path, new ArrayList<>(), adofaiMap, false, 
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {
						int floor = applyEach.getFloor();
						Tile tile = applyEach.getTile();
						
						removeActions(tile, opacity);
						
						TileData tileData = new TileData(floor, tile.getTileAngle(), tile.getActionListMap());
						applyEach.getNewTileDataList().add(tileData);
						return new ApplyEachReturnValue(
								tileData, tile.getBpm(),
								1.0, 1.0);
					}
				});
	}
	
	private static MoveTrack getTransposeMoveTrack(long opacity) {
		List<Object> startTile = new ArrayList<>();
		List<Object> endTile = new ArrayList<>();
		List<Long> positionOffset = new ArrayList<>();
		
		startTile.add(0L);
		startTile.add("Start");
		
		endTile.add(0L);
		endTile.add("End");

		positionOffset.add(0L);
		positionOffset.add(0L);
		return new MoveTrack(startTile, endTile, 0.0, positionOffset, 0.0, 100L, opacity, 0.0, "Linear", "");
	}
	
	private static void removeActions(Tile tile, long opacity) {
		List<Action> actionList;
		
		@SuppressWarnings("unused")
		boolean added = false;
		actionList = tile.getActionListIfNotEmpty(EventType.MOVE_TRACK);
		if(actionList != null) {
			List<Action> newActionList = new ArrayList<>();
			for(Action action : actionList) {
				MoveTrack a = (MoveTrack) action;
				
				newActionList.add(new MoveTrack(a.getStartTile(), a.getEndTile(), a.getDuration(), a.getPositionOffset(), a.getRotationOffset(), a.getScale(), a.getOpacity() == 0 ? 0 : opacity, a.getAngleOffset(), a.getEase(), a.getEventTag()));
			}
			
			actionList.clear();
			actionList.addAll(newActionList);
			added = true;
		}
		
		actionList = tile.getActionListIfNotEmpty(EventType.ANIMATE_TRACK);
		if(actionList != null) {
			
			//if(!added) {
			//	tile.getActionList(EventType.MOVE_TRACK).add(getTransposeMoveTrack(opacity));
			//	added = true;
			//}
			actionList.clear();
		}
		
 	}
	
}
