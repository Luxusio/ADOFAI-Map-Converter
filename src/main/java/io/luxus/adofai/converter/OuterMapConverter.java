package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OuterMapConverter {

	public static CustomLevel convert(String path) throws IOException {
		CustomLevel customLevel = CustomLevelParser.readPath(path);
		List<Tile> tileList = customLevel.getTiles();

		if (tileList.size() <= 1) {
			System.err.println("E> Map is too short!");
			return null;
		}

		return MapConverterBase.convert(customLevel, false,
				applyEach -> {
					List<Tile> oneTimingTiles = applyEach.getOneTimingTiles();

					List<Tile> newTiles = oneTimingTiles.stream()
							.map(tile -> new Tile(tile.getAngle(), new HashMap<>(tile.getActionMap())))
							.collect(Collectors.toList());

					// add twirl to first tile
					if (applyEach.getFloor() == 1) {
						Tile firstTile = newTiles.get(0);
						List<Action> actionList = firstTile.getActions(EventType.TWIRL);
						if (actionList.isEmpty()) {
							actionList.add(new Twirl());
						} else {
							actionList.clear();
						}
					}

					return newTiles;
				});
	}

}
