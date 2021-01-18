package io.luxus.adofai.converter;

import java.util.Iterator;
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
import io.luxus.api.adofai.converter.RelativeAngleConverter;
import io.luxus.api.adofai.type.EventType;
import io.luxus.api.adofai.type.TileAngle;

public class MapShapedMapConverter {
public static MapData convert(MapData mapData, MapData shape) throws ParseException {
		
		ADOFAIMap adofaiMap = new ADOFAIMap(mapData);
		
		List<Tile> tileList = adofaiMap.getTileList();
		List<TileData> tileDataList = adofaiMap.getTileDataList();
		
		Iterator<Tile> tileIt = tileList.iterator();
		Iterator<TileData> tileDataIt = tileDataList.iterator();
		
		tileIt.next();
		tileDataIt.next();
		
		TileData prev = null;
		while(tileDataIt.hasNext()) {
			tileIt.next();
			TileData now = tileDataIt.next();
			
			if(now.getTileAngle() == TileAngle.NONE) {
				tileIt.remove();
				tileDataIt.remove();
				prev.addNextTileActionListMap(now.getActionListMap());
			}
			prev = now;
		}
		
		ADOFAIMap adofaiShapeMap = new ADOFAIMap(shape);
		adofaiShapeMap.getTileList().remove(0);
		return MapSpeedConverterBase.convert(adofaiMap,
				new Function<MapSpeedConverterBase.ApplyEach, MapSpeedConverterBase.ApplyEachReturnValue>() {
					private List<Tile> tileList = adofaiShapeMap.getTileList();
					private int size = tileList.size();
					private int count = -1;
					private int floor = 1;
					
					@Override
					public ApplyEachReturnValue apply(ApplyEach applyEach) {
						
						// TODO : 맵 속의 Twirl까지 계산해서 각도 계산을 진행함
						// TODO : 맵 끝에서 다시 처음으로 넘어가는 구간에 계산 오류가 발생함. 해결필요

						count = (count + 1) % size;
						Tile nowAngleTile = tileList.get(count);
						Tile nextAngleTile = tileList.get((count + 1) % size);
						Tile moreNextAngleTile = tileList.get((count + 2) % size);
						//moreNextAngleTile = tileList.get(count);
						
						boolean end = count + 1 == size;
						
						//Tile nowAngleTile = tileList.get(count);
						//count = (count + 1) % len;
						boolean skipNone = false;
						//System.out.println(count + ", " + RelativeAngleConverter.convert(nowAngleTile.getTileAngle(), nextAngleTile.getTileAngle(), moreNextAngleTile.getTileAngle(), nowAngleTile.isReversed()) + ", " + nowAngleTile.getTileAngle().getAngle());
						if(nowAngleTile.getTileAngle() == TileAngle.NONE) {
							skipNone = true;
							//nowAngleTile = tileList.get(count);
							count = (count + 1) % size;
							nowAngleTile = nextAngleTile;
							nextAngleTile = moreNextAngleTile;
							moreNextAngleTile = tileList.get((count + 2) % size);
							end = count + 1 == size;
							//System.out.println("c:" + RelativeAngleConverter.convert(nowAngleTile.getTileAngle(), nextAngleTile.getTileAngle(), moreNextAngleTile.getTileAngle(), nowAngleTile.isReversed()) + ", " + nowAngleTile.getTileAngle().getAngle());
						}
						//double newRelativeAngle = nowAngleTile.getRelativeAngle();
						Tile tile = applyEach.getTile();
						
						double newRelativeAngle = RelativeAngleConverter.convert(nowAngleTile.getTileAngle(), nextAngleTile.getTileAngle(), moreNextAngleTile.getTileAngle(), nowAngleTile.isReversed());
						
						double result = newRelativeAngle / tile.getRelativeAngle();
						double nowTempBPM = result * tile.getBpm();

						double bpmMultiplier = result;
						double angleMultiplier = result;
						
						
						List<Action> actionList = tile.getActionListIfNotEmpty(EventType.TWIRL);
						if(actionList != null) {
							actionList.clear();
						}
						
						boolean addTwirl = nowAngleTile.getActionListIfNotEmpty(EventType.TWIRL) != null;
						if(end && nowAngleTile.isReversed()) {
							addTwirl = !addTwirl;
						}
						
						if(addTwirl) {
							tile.getActionList(EventType.TWIRL).add(new Twirl());
						}

						if(skipNone) applyEach.getNewTileDataList().add(new TileData(floor++, TileAngle.NONE));
						TileData tileData = new TileData(floor++, nowAngleTile.getTileAngle(), tile.getActionListMap());
						applyEach.getNewTileDataList().add(tileData);
						return new ApplyEachReturnValue(
								tileData, nowTempBPM,
								bpmMultiplier, angleMultiplier);
					}
				});
	}
}
