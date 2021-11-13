package io.luxus.adofai.converterv2;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.converter.AngleConverter;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class TwirlConverterV2 {
	public static CustomLevel convert(String path, double rate, boolean useCameraOptimization) throws IOException {
		CustomLevel customLevel = CustomLevelParser.readPath(path);

		return MapConverterBaseV2.convert(customLevel, useCameraOptimization,
				new Function<MapConverterBaseV2.ApplyEach, List<Tile>>() {

			private double currStaticAngle = AngleConverter.getNextStaticAngle(0.0, customLevel.getTiles().get(0).getTileMeta().getTravelAngle(), false);
			private boolean reversed = false;

			@Override
			public List<Tile> apply(MapConverterBaseV2.ApplyEach applyEach) {
				return applyEach.getOneTimingTiles()
						.stream()
						.map(tile -> {
							Tile newTile;
							if (tile.getAngle() != ANGLE_MID_TILE) {
								newTile = new Tile(currStaticAngle, new HashMap<>(tile.getActionMap()));
							}
							else {
								newTile = new Tile(ANGLE_MID_TILE, new HashMap<>(tile.getActionMap()));
							}

							List<Action> actionList = tile.getActions(EventType.TWIRL);
							actionList.clear();
							if (rate > Math.random()) {
								actionList.add(new Twirl());
								reversed = !reversed;
							}

							currStaticAngle = AngleConverter.getNextStaticAngle(currStaticAngle, tile.getTileMeta().getTravelAngle(), reversed);

							return newTile;
						}).collect(Collectors.toList());
			}
		});
	}
	
}
