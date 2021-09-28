package io.luxus.adofai.converter;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OuterMapConverter {

	public static CustomLevel convert(String path) throws ParseException, IOException {
		CustomLevel customLevel = CustomLevelParser.readPath(path);
		List<Tile> tileList = customLevel.getTiles();

		if (tileList.size() <= 1) {
			System.err.println("E> Map is too short!");
			return null;
		}

		// Twirl
		Tile firstTile = tileList.get(1);
		List<Action> actionList = firstTile.getActions(EventType.TWIRL);
		if (actionList.isEmpty()) {
			actionList.add(new Twirl());
		} else {
			actionList.clear();
		}

		return MapSpeedConverterBase.convert(customLevel, false,
				applyEach -> {
					Tile tile = applyEach.getTile();
					TileMeta tileMeta = tile.getTileMeta();

					double mulValue = tileMeta.getTravelAngle() != 0.0 ? tileMeta.getReversedTravelAngle() / tileMeta.getTravelAngle() : 1.0;
					double nowTempBPM = tileMeta.getBpm() * mulValue;

					Tile newTile = new Tile(tile.getAngle(), new HashMap<>(tile.getActionMap()));
					applyEach.getNewTileList().add(newTile);
					return new ApplyEachReturnValue(
							newTile, nowTempBPM,
							mulValue, mulValue);
				});
	}

}
