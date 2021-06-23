package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class ScreenScroll extends Action {

	private List<Double> scroll;
	private Double angleOffset;
	private String eventTag;
	
	public ScreenScroll() {
		super(EventType.SCREEN_SCROLL);
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.scroll = SafeDatatypeConverter.toDoubleList(json.get("scroll"));
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "scroll", scroll);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}
	

	public List<Double> getScroll() {
		return scroll;
	}

	public void setScroll(List<Double> scroll) {
		this.scroll = scroll;
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
