package io.luxus.adofai.converter;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEach;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.converter.AngleConverter;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class ShapedMapConverter {
	public static CustomLevel convert(String path, List<Double> angleData, boolean useCameraOptimization) throws IOException {

		final List<Double> angles = angleData.stream()
				.map(angle -> angle == 999.0 ? ANGLE_MID_TILE : angle)
				.collect(Collectors.toList());

		CustomLevel customLevel = CustomLevelParser.readPath(path);
		MapSpeedConverterBase.removeNoneTile(customLevel);
		
		return MapSpeedConverterBase.convert(customLevel, useCameraOptimization,
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {

					private final int size = angles.size();
					private int count = 0;
					private double prevStaticAngle = 0;

					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {
						Double nowAngle = angles.get(count);
						count = (count + 1) % size;
						Double nextAngle = angles.get(count);
						boolean skipNone = false;
						if (nextAngle == ANGLE_MID_TILE) {
							skipNone = true;
							count = (count + 1) % size;
							nextAngle = angles.get(count);
						}

						Tile tile = applyEach.getTile();
						TileMeta tileMeta = tile.getTileMeta();

						AngleConverter.Result result = AngleConverter.convert(prevStaticAngle, nowAngle, nextAngle, skipNone != tile.getTileMeta().isReversed(), !skipNone);
						prevStaticAngle = result.getCurrStaticAngle();
						double relativeAngle = result.getCurrTravelAngle();
						
						double mulValue = relativeAngle / tileMeta.getTravelAngle();
						double nowTempBPM = mulValue * tileMeta.getBpm();

						Tile newTile = new Tile(nowAngle, new HashMap<>(tile.getActionMap()));
						
						applyEach.getNewTileList().add(newTile);
						if(skipNone) applyEach.getNewTileList().add(new Tile(ANGLE_MID_TILE));
						return new ApplyEachReturnValue(
								newTile, nowTempBPM,
								mulValue, mulValue);
					}
				});
	}
}
