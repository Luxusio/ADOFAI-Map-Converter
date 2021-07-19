package io.luxus.api.adofai;

import java.util.List;
import java.util.Map;

import io.luxus.api.adofai.action.Action;
import io.luxus.api.adofai.type.EventType;
import io.luxus.api.adofai.type.TileAngle;

public class Tile extends TileData {

	private double bpm;
	private double relativeAngle;
	private double staticAngle;
	private boolean reversed;

	private double x;
	private double y;

//	public Tile(int floor, TileAngle tileAngle, double bpm, double relativeAngle, boolean reversed, double x, double y) {
//		super(floor, tileAngle);
//
//		this.bpm = bpm;
//		this.relativeAngle = relativeAngle;
//		this.reversed = reversed;
//		this.x = x;
//		this.y = y;
//	}

	public Tile(int floor, TileAngle tileAngle, Map<EventType, List<Action>> actionListMap, double bpm,
			double relativeAngle, double staticAngle, boolean reversed, double x, double y) {
		super(floor, tileAngle, actionListMap);

		this.bpm = bpm;
		this.relativeAngle = relativeAngle;
		this.staticAngle = staticAngle;
		this.reversed = reversed;
		this.x = x;
		this.y = y;
	}

	public void setRelativeAngle(double relativeAngle) {
		if (relativeAngle < 0 || relativeAngle > 360) {
			System.out.println("E> relativeAngle should be bigger than 0, and smaller than 360.");
			return;
		}
		this.relativeAngle = relativeAngle;
	}

	public double getBpm() {
		return bpm;
	}

	public boolean isReversed() {
		return reversed;
	}

	public double getRelativeAngle() {
		return relativeAngle;
	}

	public double getStaticAngle() {
		return staticAngle;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getTempBPM() {
		return 180.0 * bpm / relativeAngle;
	}

	public double getReversedTempBPM() {
		return getReversedRelativeAngle() * bpm / relativeAngle;
	}

	public double getReversedRelativeAngle() {
		return getRelativeAngle() == 360.0 ? 360.0 : 360.0 - getRelativeAngle();
	}

	public double getTileDurationMS() {
		return 60000.0 / (180.0 * bpm) * relativeAngle;
	}

}
