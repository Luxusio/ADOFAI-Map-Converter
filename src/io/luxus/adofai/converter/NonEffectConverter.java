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
import io.luxus.api.adofai.type.EventType;

public class NonEffectConverter {
	public static MapData convert(String path, boolean removeDecoration, boolean removeTileMove, boolean removeCameraEvents) throws ParseException, IOException {
		ADOFAIMap adofaiMap = MapSpeedConverterBase.getMap(path);
		removeActions(adofaiMap.getTileList().get(0), removeDecoration, removeTileMove, removeCameraEvents);
		return MapSpeedConverterBase.convert(path, new ArrayList<>(), adofaiMap, false, 
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {
						int floor = applyEach.getFloor();
						Tile tile = applyEach.getTile();
						
						removeActions(tile, removeDecoration, removeTileMove, removeCameraEvents);
						
						TileData tileData = new TileData(floor, tile.getTileAngle(), tile.getActionListMap());
						applyEach.getNewTileDataList().add(tileData);
						return new ApplyEachReturnValue(
								tileData, tile.getBpm(),
								1.0, 1.0);
					}
				});
	}
	
	private static void removeActions(Tile tile, boolean removeDecoration, boolean removeTileMove, boolean removeCameraEvents) {
		List<Action> actionList;
		
		actionList = tile.getActionListIfNotEmpty(EventType.BLOOM);
		if(actionList != null) actionList.clear();

		//actionList = tile.getActionListIfNotEmpty(EventType.MOVE_TRACK);
		//if(actionList != null) actionList.clear();

		actionList = tile.getActionListIfNotEmpty(EventType.ANIMATE_TRACK);
		if(actionList != null) actionList.clear();
		
		actionList = tile.getActionListIfNotEmpty(EventType.HALL_OF_MIRRORS);
		if(actionList != null) actionList.clear();
		
		actionList = tile.getActionListIfNotEmpty(EventType.SET_FILTER);
		if(actionList != null) actionList.clear();
		
		if(removeDecoration) {
			actionList = tile.getActionListIfNotEmpty(EventType.ADD_DECORATION);
			if(actionList != null) actionList.clear();
			
			actionList = tile.getActionListIfNotEmpty(EventType.MOVE_DECORATIONS);
			if(actionList != null) actionList.clear();
		}
		
		if(removeTileMove) {
			actionList = tile.getActionListIfNotEmpty(EventType.MOVE_TRACK);
			if(actionList != null) actionList.clear();
		}
		
		if(removeCameraEvents) {
			actionList = tile.getActionListIfNotEmpty(EventType.MOVE_CAMERA);
			if(actionList != null) actionList.clear();
		}
		
 	}
	
}
