package io.luxus.adofai.converter;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.MoveTrack;
import io.luxus.lib.adofai.action.type.Ease;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.TilePosition;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TransposeMapConverter {
	
	public static CustomLevel convert(String path, int opacity) throws IOException {
		CustomLevel customLevel = CustomLevelParser.readPath(path);
		
		Tile zeroTile = customLevel.getTiles().get(0);
		removeActions(zeroTile, opacity);
		
		zeroTile.getActions(EventType.MOVE_TRACK).add(getTransposeMoveTrack(opacity));
		
		return MapSpeedConverterBase.convert(customLevel, false,
				applyEach -> {
					Tile tile = applyEach.getTile();

					removeActions(tile, opacity);

					Tile newTile = new Tile(tile.getAngle(), new HashMap<>(tile.getActionMap()));
					applyEach.getNewTileList().add(newTile);
					return new ApplyEachReturnValue(
							newTile, tile.getTileMeta().getBpm(),
							1.0, 1.0);
				});
	}
	
	private static MoveTrack getTransposeMoveTrack(long opacity) {
		return new MoveTrack(0L, TilePosition.START, 0L, TilePosition.END,
				0.0, Arrays.asList(0.0, 0.0), 0.0, Arrays.asList(100L, 100L), opacity, 0.0, Ease.LINEAR, "");
	}
	
	private static void removeActions(Tile tile, long opacity) {
		List<Action> actionList;

		actionList = tile.getActions(EventType.MOVE_TRACK);
		List<Action> newActionList = new ArrayList<>();
		for(Action action : actionList) {
			MoveTrack a = (MoveTrack) action;

			newActionList.add(new MoveTrack(a.getStartTileNum(), a.getStartTilePosition(), a.getEndTileNum(), a.getEndTilePosition(),
					a.getDuration(), a.getPositionOffset(), a.getRotationOffset(), a.getScale(), a.getOpacity() == 0 ? 0 : opacity,
					a.getAngleOffset(), a.getEase(), a.getEventTag()));
		}

		actionList.clear();
		actionList.addAll(newActionList);
		tile.getActions(EventType.ANIMATE_TRACK).clear();

 	}
	
}
