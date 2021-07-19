package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class Flash extends Action {
	
	private Double duration; 
	private String plane;
	private String startColor;
	private Long startOpacity; 
	private String endColor;
	private Long endOpacity;
	private Double angleOffset;
	private String eventTag;
	
	public Flash() {
		super(EventType.FLASH);
	}

	public Flash(Double duration, String plane, String startColor, Long startOpacity,
			String endColor, Long endOpacity, Double angleOffset, String eventTag) {
		this();
		this.duration = duration;
		this.plane = plane;
		this.startColor = startColor;
		this.startOpacity = startOpacity;
		this.endColor = endColor;
		this.endOpacity = endOpacity;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.duration = SafeDatatypeConverter.toDouble(json.get("duration"));
		this.plane = (String) json.get("plane");
		this.startColor = (String) json.get("startColor");
		this.startOpacity = (Long) json.get("startOpacity");
		this.endColor = (String) json.get("endColor");
		this.endOpacity = (Long) json.get("endOpacity");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "duration", duration);
		save(sb, "plane", plane);
		save(sb, "startColor", startColor);
		save(sb, "startOpacity", startOpacity);
		save(sb, "endColor", endColor);
		save(sb, "endOpacity", endOpacity);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public Double getDuration() {
		return duration;
	}

	public String getPlane() {
		return plane;
	}

	public String getStartColor() {
		return startColor;
	}

	public Long getStartOpacity() {
		return startOpacity;
	}

	public String getEndColor() {
		return endColor;
	}

	public Long getEndOpacity() {
		return endOpacity;
	}

	public Double getAngleOffset() {
		return angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}
	
}
