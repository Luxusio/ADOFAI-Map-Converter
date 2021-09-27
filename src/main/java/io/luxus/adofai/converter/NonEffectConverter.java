package io.luxus.adofai.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.json.simple.parser.ParseException;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;

public class NonEffectConverter {
	public static CustomLevel convert(String path, boolean removeDecoration, boolean removeTileMove, boolean removeCameraEvents, boolean removeFlash) throws ParseException, IOException {
		CustomLevel customLevel = CustomLevelParser.readPath(path);
		removeActions(customLevel.getTiles().get(0), removeDecoration, removeTileMove, removeCameraEvents, removeFlash);
		return MapSpeedConverterBase.convert(path, new ArrayList<>(), customLevel, false,
				applyEach -> {
					Tile tile = applyEach.getTile();

					removeActions(tile, removeDecoration, removeTileMove, removeCameraEvents, removeFlash);

					Tile newTile = new Tile(tile.getAngle(), new HashMap<>(tile.getActionMap()));
					applyEach.getNewTileList().add(newTile);
					return new ApplyEachReturnValue(
							newTile, tile.getTileMeta().getBpm(),
							1.0, 1.0);
				});
	}
	
	private static void removeActions(Tile tile, boolean removeDecoration, boolean removeTileMove, boolean removeCameraEvents, boolean removeFlash) {

		tile.getActions(EventType.BLOOM).clear();
		tile.getActions(EventType.ANIMATE_TRACK).clear();
		tile.getActions(EventType.HALL_OF_MIRRORS).clear();
		tile.getActions(EventType.SET_FILTER).clear();
		
		if (removeDecoration) {
			tile.getActions(EventType.ADD_DECORATION).clear();
			tile.getActions(EventType.MOVE_DECORATIONS).clear();
		}
		
		if (removeTileMove) {
			tile.getActions(EventType.MOVE_TRACK).clear();
		}
		
		if (removeCameraEvents) {
			tile.getActions(EventType.MOVE_CAMERA).clear();
		}

		if (removeFlash) {
			tile.getActions(EventType.FLASH).clear();
		}
		
 	}
	
}
