package io.luxus.adofai.converter;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEach;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
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

public class TwirlConverter {
	public static CustomLevel convert(String path, boolean allTwirl, boolean useCameraOptimization) throws ParseException, IOException {
		CustomLevel customLevel = CustomLevelParser.readPath(path);

		return MapSpeedConverterBase.convert(customLevel, useCameraOptimization,
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {

					private double staticAngle = 0.0;
					private boolean reversed = false;

					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {
						Tile tile = applyEach.getTile();
						TileMeta tileMeta = tile.getTileMeta();

						double mulValue = 1.0;
						double nowTempBPM = mulValue * tileMeta.getBpm();

						List<Action> actionList = tile.getActions(EventType.TWIRL);
						actionList.clear();
						if(allTwirl) actionList.add(new Twirl());
						
						Tile newTile = new Tile(staticAngle, new HashMap<>(tile.getActionMap()));
						applyEach.getNewTileList().add(newTile);


						if(allTwirl) reversed = !reversed;
						staticAngle = AngleConverter.getNextStaticAngle(staticAngle, tileMeta.getRelativeAngle(), reversed);

						return new ApplyEachReturnValue(
								newTile, nowTempBPM,
								mulValue, mulValue);
					}
				});
	}
	
}
