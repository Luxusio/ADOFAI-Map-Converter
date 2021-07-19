package io.luxus.api.adofai;

import java.util.ArrayList;
import java.util.List;

import io.luxus.api.adofai.action.Action;
import io.luxus.api.adofai.action.PositionTrack;
import io.luxus.api.adofai.action.SetSpeed;
import io.luxus.api.adofai.converter.AngleConverter;
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
		
		double staticAngle = 0;
		
		double x = 0.0;
		double y = 0.0;
		
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
			
			AngleConverter.Result result = AngleConverter.convert(staticAngle, now.getTileAngle(), next.getTileAngle(), reverse, now.getTileAngle() != TileAngle.NONE);
			double relativeAngle = result.getRelativeAngle();
			
			Tile tile = new Tile(now.getFloor(), now.getTileAngle(), now.getActionListMap(), bpm, relativeAngle, staticAngle, reverse, x, y);
			this.tileList.add(tile);

			staticAngle = result.getStaticAngle();
			
			double rad = Math.toRadians(staticAngle);
			x += Math.cos(rad);
			y += Math.sin(rad);
			
			actionList = next.getActionListIfNotEmpty(EventType.POSITION_TRACK);
			if(actionList != null) {
				PositionTrack positionTrack = (PositionTrack) actionList.get(0);
				if("Disabled".equals(positionTrack.getEditorOnly())) {
					x += positionTrack.getPositionOffset().get(0);
					y += positionTrack.getPositionOffset().get(1);
				}
			}
			
			now = next;
		}
		
		if(next != null) {
			if(next.containsAction(EventType.TWIRL)) {
				reverse = !reverse;
			}
			Tile tile = new Tile(next.getFloor(), next.getTileAngle(), next.getActionListMap(), bpm, 180, staticAngle, reverse, x, y);
			this.tileList.add(tile);
		}
	}
	

	public List<Tile> getTileList() {
		return tileList;
	}
	
}
