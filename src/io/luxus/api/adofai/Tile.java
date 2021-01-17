package io.luxus.api.adofai;

import java.util.List;
import java.util.Map;

import io.luxus.api.adofai.action.Action;
import io.luxus.api.adofai.type.EventType;
import io.luxus.api.adofai.type.TileAngle;

public class Tile extends TileData {

	private double bpm;
	private double relativeAngle;
	private boolean reversed;

	public Tile(int floor, TileAngle tileAngle, double bpm, double relativeAngle, boolean reversed) {
		super(floor, tileAngle);

		this.bpm = bpm;
		this.relativeAngle = relativeAngle;
		this.reversed = reversed;
	}
	
	public Tile(int floor, TileAngle tileAngle, Map<EventType, List<Action>> actionListMap, double bpm, double relativeAngle, boolean reversed) {
		super(floor, tileAngle, actionListMap);

		this.bpm = bpm;
		this.relativeAngle = relativeAngle;
		this.reversed = reversed;
	}

	public double getBpm() {
		return bpm;
	}
	
	public boolean isReversed() {
		return reversed;
	}

	public double getTempBPM() {
		return 180.0 * bpm / relativeAngle;
	}

	public double getReversedTempBPM() {
		return getReversedRelativeAngle() * bpm / relativeAngle;
	}

	public double getRelativeAngle() {
		return relativeAngle;
	}

	public double getReversedRelativeAngle() {
		return getRelativeAngle() == 360.0 ? 360.0 : 360.0 - getRelativeAngle();
	}

}
