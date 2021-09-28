package io.luxus.adofai.converter;

import com.google.common.math.DoubleMath;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEach;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.converter.AngleConverter;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.Constants.EPSILON;

public class TwirlConverter {
	public static CustomLevel convert(String path, boolean allTwirl, boolean useCameraOptimization) throws ParseException, IOException {
		CustomLevel customLevel = CustomLevelParser.readPath(path);

		return MapSpeedConverterBase.convert(customLevel, useCameraOptimization,
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {

					private double prevStaticAngle = 0.0;
					private boolean reversed = false;

					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {

						int floor = applyEach.getFloor();
						Tile tile = applyEach.getTile();

						double relativeAngle = tile.getTileMeta().getTravelAngle();
						if (relativeAngle == 0.0) {
							relativeAngle = applyEach.getTileList().get(floor + 1).getTileMeta().getTravelAngle();
						} else if (tile.getAngle() == ANGLE_MID_TILE) {
							relativeAngle = applyEach.getTileList().get(floor - 1).getTileMeta().getTravelAngle();
						}

						Double currAngle;
						if (DoubleMath.fuzzyEquals(relativeAngle, 0.0, EPSILON)) {
							currAngle = ANGLE_MID_TILE;
						} else {
							currAngle = prevStaticAngle;
						}

						double mulValue = 1.0;
						double nowTempBPM = mulValue * tile.getTileMeta().getBpm();

						List<Action> actionList = tile.getActions(EventType.TWIRL);
						actionList.clear();
						if (allTwirl) {
							actionList.add(new Twirl());
							reversed = !reversed;
						}

						prevStaticAngle = AngleConverter.getNextStaticAngle(prevStaticAngle, tile.getTileMeta().getTravelAngle(), reversed);

						Tile newTile = new Tile(currAngle, new HashMap<>(tile.getActionMap()));
						applyEach.getNewTileList().add(newTile);

						return new ApplyEachReturnValue(
								newTile, nowTempBPM,
								mulValue, mulValue);
					}
				});
	}
	
}
