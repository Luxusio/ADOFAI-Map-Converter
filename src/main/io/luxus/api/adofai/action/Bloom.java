package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class Bloom extends Action {
	
	private String enabled;
	private Long threshold;
	private Long intensity;
	private String color;
	private Double angleOffset;
	private String eventTag;
	
	public Bloom() {
		super(EventType.BLOOM);
	}

	public Bloom(String enabled, Long threshold, Long intensity, String color, Double angleOffset,
			String eventTag) {
		this();
		this.enabled = enabled;
		this.threshold = threshold;
		this.intensity = intensity;
		this.color = color;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.enabled = (String) json.get("enabled");
		this.threshold = (Long) json.get("threshold");
		this.intensity = (Long) json.get("intensity");
		this.color = (String) json.get("color");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "enabled", enabled);
		save(sb, "threshold", threshold);
		save(sb, "intensity", intensity);
		save(sb, "color", color);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public String getEnabled() {
		return enabled;
	}

	public Long getThreshold() {
		return threshold;
	}

	public Long getIntensity() {
		return intensity;
	}

	public String getColor() {
		return color;
	}

	public Double getAngleOffset() {
		return angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}
	
}
