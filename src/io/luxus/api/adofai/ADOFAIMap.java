package io.luxus.api.adofai;

import java.util.ArrayList;
import java.util.List;

import io.luxus.api.adofai.action.Action;
import io.luxus.api.adofai.action.SetSpeed;
import io.luxus.api.adofai.converter.RelativeAngleConverter;
import io.luxus.api.adofai.type.EventType;
import io.luxus.api.adofai.type.SpeedType;
import io.luxus.api.adofai.type.TileAngle;

public class ADOFAIMap extends MapData {
	
	private List<Tile> tileList;
	
	public ADOFAIMap(MapData mapData) {
		super(mapData.getSetting(), mapData.getTileDataList());
		this.tileList = new ArrayList<>();
		init();
	}

	private void init() {
		double bpm = getSetting().getBpm();
		boolean reverse = false;
		TileData now = getTileDataList().get(0);
		TileData next = null;
		int size = getTileDataList().size();
	
		for(int i=1;i<size;i++) {
			next = getTileDataList().get(i);
			List<Action> actionList = now.getActionListIfNotEmpty(EventType.SET_SPEED);
			if(actionList != null) {
				SetSpeed setSpeed = (SetSpeed) actionList.get(0);
				if(setSpeed.getSpeedType() == null || SpeedType.BPM.getName().equals(setSpeed.getSpeedType())) {
					bpm = setSpeed.getBeatsPerMinute().doubleValue();
				}
				else if(SpeedType.MULTIPLIER.getName().equals(setSpeed.getSpeedType())) {
					bpm *= setSpeed.getBpmMultiplier().doubleValue();
				}
				else {
					System.out.println("E> Wrong speed type: " + setSpeed.getSpeedType());
				}
			}
			
			if(now.containsAction(EventType.TWIRL)) {
				reverse = !reverse;
			}
			
			if(now.getTileAngle() == TileAngle.NONE) {
				Tile tile = new Tile(now.getFloor(), now.getTileAngle(), now.getActionListMap(), bpm, 0, reverse);
				this.tileList.add(tile);
			}
			else {
				double relativeAngle = RelativeAngleConverter.convert(now.getTileAngle(), next.getTileAngle(), 
						i + 1 == size ? null : getTileDataList().get(i+1).getTileAngle(), reverse);

				Tile tile = new Tile(now.getFloor(), now.getTileAngle(), now.getActionListMap(), bpm, relativeAngle, reverse);
				this.tileList.add(tile);
			}
			
			now = next;
		}
		
		if(next != null) {
			if(next.containsAction(EventType.TWIRL)) {
				reverse = !reverse;
			}
			Tile tile = new Tile(next.getFloor(), next.getTileAngle(), next.getActionListMap(), bpm, 180, reverse);
			this.tileList.add(tile);
		}
	}

	public List<Tile> getTileList() {
		return tileList;
	}
	
}
