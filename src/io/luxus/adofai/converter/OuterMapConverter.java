package io.luxus.adofai.converter;

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
import io.luxus.api.adofai.type.TileAngle;

public class OuterMapConverter {

	public static MapData convert(MapData mapData) throws ParseException {
		ADOFAIMap adofaiMap = new ADOFAIMap(mapData);
		List<Tile> tileList = adofaiMap.getTileList();

		if (tileList.size() <= 1) {
			System.out.println("E> Map is too short!");
			return mapData;
		}

		// Twirl
		Tile tile = tileList.get(1);
		List<Action> actionList = tile.getActionList(EventType.TWIRL);
		if (actionList.isEmpty()) {
			actionList.add(new Twirl());
		} else {
			actionList.clear();
		}

		return MapSpeedConverterBase.convert(adofaiMap,
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {

						int floor = applyEach.getFloor();
						Tile tile = applyEach.getTile();
						List<Tile> tileList = applyEach.getTileList();
						double nowTempBPM = applyEach.getPrevTempBPM();

						if (tile.getTileAngle() != TileAngle.NONE) {
							nowTempBPM = tile.getReversedTempBPM();
						}

						double bpmMultiplier = nowTempBPM / tile.getBpm();
						double angleMultiplier = tile.getReversedRelativeAngle()
								/ (tile.getRelativeAngle() == 0.0 ? tileList.get(floor - 1).getRelativeAngle()
										: tile.getRelativeAngle());

						return new ApplyEachReturnValue(
								new TileData(floor, tile.getTileAngle(), tile.getActionListMap()), nowTempBPM,
								bpmMultiplier, angleMultiplier);
					}
				});
	}

}
