package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.action.Twirl;
import io.luxus.lib.adofai.type.EventType;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OuterMapConverter implements MapConverter {

	@Override
	public Object[] prepareParameters(Scanner scanner) {
		return new Object[0];
	}

	@Override
	public boolean impossible(CustomLevel customLevel, Object... args) {
		return false;
	}

	@Override
	public String getLevelPostfix(CustomLevel customLevel, Object... args) {
		return "Outer";
	}

	@Override
	public CustomLevel convert(CustomLevel customLevel, Object... args) {
		return MapConverterBase.convert(customLevel, false,
				applyEach -> {
					List<Tile> oneTimingTiles = applyEach.getOneTimingTiles();

					List<Tile.Builder> newTileBuilders = oneTimingTiles.stream()
							.map(tile -> new Tile.Builder().angle(tile.getAngle()).actionMap(tile.getActionMap()))
							.collect(Collectors.toList());

					// add twirl to first tile
					if (applyEach.getFloor() == 1) {
						Tile.Builder firstTileBuilder = newTileBuilders.get(0);
						if (firstTileBuilder.getActions(EventType.TWIRL).isEmpty()) {
							firstTileBuilder.addAction(new Twirl.Builder().build());
						} else {
							firstTileBuilder.removeActions(EventType.TWIRL);
						}
					}

					return newTileBuilders;
				});
	}
}
