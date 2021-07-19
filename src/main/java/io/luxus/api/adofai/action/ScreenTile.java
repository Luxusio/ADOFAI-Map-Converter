package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class ScreenTile extends Action {

	private List<Double> tile;
	private Double angleOffset;
	private String eventTag;
	
	public ScreenTile() {
		super(EventType.SCREEN_TILE);
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.tile = SafeDatatypeConverter.toDoubleList(json.get("tile"));
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "tile", tile);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}
	

	public List<Double> getTile() {
		return tile;
	}

	public void setTile(List<Double> tile) {
		this.tile = tile;
	}

	public Double getAngleOffset() {
		return angleOffset;
	}

	public void setAngleOffset(Double angleOffset) {
		this.angleOffset = angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}

	public void setEventTag(String eventTag) {
		this.eventTag = eventTag;
	}
	
}
