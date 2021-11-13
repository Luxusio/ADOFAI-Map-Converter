package io.luxus.adofai.converterv2;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.helper.TileHelper;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;


public class ShapedMapConverterV2 {

	public static CustomLevel linearConvert(String path, boolean useCameraOptimization) throws IOException {
		return convert(path, Collections.singletonList(0.0), useCameraOptimization);
	}

	public static CustomLevel convert(String path, List<Double> rawAngles, boolean useCameraOptimization) throws IOException {
		final List<Tile> tiles = rawAngles.stream()
				.map(angle -> angle == 999.0 ? ANGLE_MID_TILE : angle)
				.map(Tile::new)
				.collect(Collectors.toList());
		tiles.add(0, new Tile(0.0));
		final CustomLevel shapeLevel = new CustomLevel(new LevelSetting(), tiles);
		return convert(path, shapeLevel, useCameraOptimization);
	}

	public static CustomLevel convert(String path, CustomLevel shapeLevel, boolean useCameraOptimization) throws IOException {

		CustomLevel customLevel = CustomLevelParser.readPath(path);

		final List<Tile> shapeTiles = shapeLevel.getTiles();
		shapeTiles.remove(0);

		return MapConverterBaseV2.convert(customLevel, useCameraOptimization,
				new Function<MapConverterBaseV2.ApplyEach, List<Tile>>() {

					private int index = 0;

					@Override
					public List<Tile> apply(MapConverterBaseV2.ApplyEach applyEach) {

						List<Tile> nowTimingTiles = applyEach.getOneTimingTiles();
						List<Tile> nowShapeTiles = MapConverterBaseV2.getSameTimingTiles(shapeTiles, index);

						List<Tile> newTiles = nowShapeTiles.stream()
								.map(tile -> {
									Tile newTile = new Tile(tile.getAngle());

									tile.getActions(EventType.TWIRL)
											.forEach(newTile::addAction);

									return newTile;
								}).collect(Collectors.toList());

						int newTileIdx = 0;
						for (Tile timingTile : nowTimingTiles) {
							Tile newTile = newTiles.get(newTileIdx);

							timingTile.getActions(EventType.TWIRL).clear();

							TileHelper.combineTile(newTile, timingTile);
							if (++newTileIdx >= newTiles.size()) {
								newTileIdx--;
							}
						}

						index = index + nowShapeTiles.size();
						if (index >= shapeTiles.size()) {
							index = 0;
							if (shapeTiles.get(shapeTiles.size() - 1).getTileMeta().isReversed()) {
								List<Action> twirls = newTiles.get(newTiles.size() - 1).getActions(EventType.TWIRL);
								if (twirls.isEmpty()) {
									twirls.add(new Twirl());
								}
								else {
									twirls.clear();
								}
							}
						}

						return newTiles;
					}
				});
	}
	
}
