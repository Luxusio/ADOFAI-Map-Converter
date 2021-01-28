package io.luxus.adofai.converter;

import java.io.IOException;
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

public class MapShapedMapConverter {
	public static MapData convert(String path, MapData shape, boolean useCameraOptimization) throws ParseException, IOException {

		ADOFAIMap adofaiMap = MapSpeedConverterBase.getMap(path);
		List<Integer> removedTileList = MapSpeedConverterBase.removeNoneTile(adofaiMap);

		ADOFAIMap adofaiShapeMap = new ADOFAIMap(shape);
		adofaiShapeMap.getTileList().remove(0);
		return MapSpeedConverterBase.convert(path, removedTileList, adofaiMap, useCameraOptimization, 
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					private List<Tile> tileList = adofaiShapeMap.getTileList();
					private int len = tileList.size();
					private int count = 0;
					private int floor = 1;
					private double nowStaticAngle = 0;

					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {

						Tile nowAngleTile = tileList.get(count);
						count = (count + 1) % len;
						Tile nextAngleTile = tileList.get(count);

						boolean end = count + 1 == len;
						boolean skipNone = false;

						if (nextAngleTile.getTileAngle() == TileAngle.NONE) {
							skipNone = true;
							count = (count + 1) % len;
							nextAngleTile = tileList.get(count);
							end = count + 1 == len;
						}

						Tile tile = applyEach.getTile();

						AngleConverter.Result result = AngleConverter.convert(nowStaticAngle,
								nowAngleTile.getTileAngle(), nextAngleTile.getTileAngle(), nowAngleTile.isReversed(), !skipNone);
						nowStaticAngle = result.getStaticAngle();
						double relativeAngle = result.getRelativeAngle();
						
						
						double mulValue = relativeAngle / tile.getRelativeAngle();
						double nowTempBPM = mulValue * tile.getBpm();

						double bpmMultiplier = mulValue;
						double angleMultiplier = mulValue;

						List<Action> actionList = tile.getActionListIfNotEmpty(EventType.TWIRL);
						if (actionList != null) {
							actionList.clear();
						}

						boolean addTwirl = nowAngleTile.getActionListIfNotEmpty(EventType.TWIRL) != null;
						if (end && nowAngleTile.isReversed()) {
							addTwirl = !addTwirl;
						}

						if (addTwirl) {
							tile.getActionList(EventType.TWIRL).add(new Twirl());
						}

						TileData tileData = new TileData(floor++, nowAngleTile.getTileAngle(), tile.getActionListMap());
						applyEach.getNewTileDataList().add(tileData);

						if (skipNone)
							applyEach.getNewTileDataList().add(new TileData(floor++, TileAngle.NONE));
						return new ApplyEachReturnValue(tileData, nowTempBPM, bpmMultiplier, angleMultiplier);
					}
				});
	}
	
}
