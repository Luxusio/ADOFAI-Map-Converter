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
import io.luxus.api.adofai.type.EventType;

public class OuterMapConverter {

	public static MapData convert(String path) throws ParseException, IOException {
		ADOFAIMap adofaiMap = MapSpeedConverterBase.getMap(path);
		List<Tile> tileList = adofaiMap.getTileList();

		if (tileList.size() <= 1) {
			System.out.println("E> Map is too short!");
			return null;
		}

		// Twirl
		Tile tile = tileList.get(1);
		List<Action> actionList = tile.getActionList(EventType.TWIRL);
		if (actionList.isEmpty()) {
			actionList.add(new Twirl());
		} else {
			actionList.clear();
		}

		return MapSpeedConverterBase.convert(path, new ArrayList<>(), adofaiMap, false, 
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {

						int floor = applyEach.getFloor();
						Tile tile = applyEach.getTile();
						
						double mulValue = tile.getRelativeAngle() != 0.0 ? tile.getReversedRelativeAngle() / tile.getRelativeAngle() : 1.0;
						double nowTempBPM = tile.getBpm() * mulValue;
						double bpmMultiplier = mulValue;
						double angleMultiplier = mulValue;
						
						TileData tileData = new TileData(floor, tile.getTileAngle(), tile.getActionListMap());
						applyEach.getNewTileDataList().add(tileData);
						return new ApplyEachReturnValue(
								tileData, nowTempBPM,
								bpmMultiplier, angleMultiplier);
					}
				});
	}

}
