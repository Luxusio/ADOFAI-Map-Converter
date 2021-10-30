package io.luxus.adofai.converter;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEach;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.converter.AngleConverter;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class MapShapedMapConverter {
	public static CustomLevel convert(String path, CustomLevel shapeLevel, boolean useCameraOptimization) throws IOException {

		CustomLevel customLevel = CustomLevelParser.readPath(path);
		MapSpeedConverterBase.removeNoneTile(customLevel);

		shapeLevel.getTiles().remove(0);
		return MapSpeedConverterBase.convert(customLevel, useCameraOptimization,
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {

					private final List<Tile> tileList = shapeLevel.getTiles();
					private final int size = tileList.size();
					private int count = 0;
					private double prevStaticAngle = 0;

					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {

						Tile nowAngleTile = tileList.get(count);
						count = (count + 1) % size;
						Tile nextAngleTile = tileList.get(count);

						boolean end = count + 1 == size;
						boolean skipNone = false;

						if (nextAngleTile.getAngle() == ANGLE_MID_TILE) {
							skipNone = true;
							count = (count + 1) % size;
							nextAngleTile = tileList.get(count);
							end = count + 1 == size;
						}

						Tile tile = applyEach.getTile();

						AngleConverter.Result result = AngleConverter.convert(prevStaticAngle, nowAngleTile.getAngle(), nextAngleTile.getAngle(), nowAngleTile.getTileMeta().isReversed(), !skipNone);
						prevStaticAngle = result.getCurrStaticAngle();
						double relativeAngle = result.getCurrTravelAngle();

						double mulValue = relativeAngle / tile.getTileMeta().getTravelAngle();
						double nowTempBPM = mulValue * tile.getTileMeta().getBpm();

						tile.getActions(EventType.TWIRL).clear();

						boolean addTwirl = !nowAngleTile.getActions(EventType.TWIRL).isEmpty();
						if (end && nowAngleTile.getTileMeta().isReversed()) {
							addTwirl = !addTwirl;
						}

						if (addTwirl) {
							tile.getActions(EventType.TWIRL).add(new Twirl());
						}

						Tile newTile = new Tile(nowAngleTile.getAngle(), new HashMap<>(tile.getActionMap()));
						applyEach.getNewTileList().add(newTile);

						if (skipNone)
							applyEach.getNewTileList().add(new Tile(ANGLE_MID_TILE));
						return new ApplyEachReturnValue(newTile, nowTempBPM, mulValue, mulValue);
					}
				});
	}
	
}
