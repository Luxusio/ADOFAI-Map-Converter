package io.luxus.adofai.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.json.simple.parser.ParseException;

import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEach;
import io.luxus.adofai.converter.MapSpeedConverterBase.ApplyEachReturnValue;
import io.luxus.api.adofai.ADOFAIMap;
import io.luxus.api.adofai.MapData;
import io.luxus.api.adofai.Tile;
import io.luxus.api.adofai.TileData;
import io.luxus.api.adofai.action.Action;
import io.luxus.api.adofai.action.Twirl;
import io.luxus.api.adofai.converter.AngleConverter;
import io.luxus.api.adofai.type.EventType;
import io.luxus.api.adofai.type.TileAngle;

public class TwirlConverter {
	public static MapData convert(String path, boolean allTwirl, boolean useCameraOptimization) throws ParseException, IOException {
		ADOFAIMap adofaiMap = MapSpeedConverterBase.getMap(path);

		return MapSpeedConverterBase.convert(path, new ArrayList<>(), adofaiMap, useCameraOptimization, 
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					private double staticAngle = 0.0;
					private boolean reversed = false;
					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {
						int floor = applyEach.getFloor();
						Tile tile = applyEach.getTile();
						
						double relativeAngle = tile.getRelativeAngle();
						if (tile.getRelativeAngle() == 0.0) {
							relativeAngle = applyEach.getTileList().get(floor + 1).getRelativeAngle();
						} else if(tile.getTileAngle() == TileAngle.NONE) {
							relativeAngle = applyEach.getTileList().get(floor - 1).getRelativeAngle();
						}
						
						//TileAngle newTileAngle = AngleConverter.getNextAngle(staticAngle, relativeAngle, tile.isReversed());
						//staticAngle = AngleConverter.getNextStaticAngle(staticAngle, tile.getRelativeAngle(), tile.isReversed());
						
						TileAngle newTileAngle = AngleConverter.getNextAngle(staticAngle, relativeAngle, tile.isReversed());
						if(allTwirl) reversed = !reversed;
						staticAngle = AngleConverter.getNextStaticAngle(staticAngle, tile.getRelativeAngle(), reversed);
						
						double mulValue = 1.0;
						double nowTempBPM = mulValue * tile.getBpm();
						double bpmMultiplier = mulValue;
						double angleMultiplier = mulValue;
						
						List<Action> actionList = tile.getActionList(EventType.TWIRL);
						actionList.clear();
						if(allTwirl) actionList.add(new Twirl());
						
						TileData tileData = new TileData(floor, newTileAngle, tile.getActionListMap());
						applyEach.getNewTileDataList().add(tileData);
						return new ApplyEachReturnValue(
								tileData, nowTempBPM,
								bpmMultiplier, angleMultiplier);
					}
				});
	}
	
}
