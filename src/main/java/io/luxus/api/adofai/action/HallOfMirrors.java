package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class HallOfMirrors extends Action {

	private String enabled;
	private Double angleOffset;
	private String eventTag;
	
	public HallOfMirrors() {
		super(EventType.HALL_OF_MIRRORS);
	}

	public HallOfMirrors(String enabled, Double angleOffset, String eventTag) {
		this();
		this.enabled = enabled;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.enabled = (String) json.get("enabled");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "enabled", enabled);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public String getEnabled() {
		return enabled;
	}

	public Double getAngleOffset() {
		return angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}
	
}
