package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class ShakeScreen extends Action {

	private Double duration;
	private Long strength;
	private Long intensity;
	private String fadeOut;
	private Double angleOffset;
	private String eventTag;

	public ShakeScreen() {
		super(EventType.SHAKE_SCREEN);
	}

	public ShakeScreen(Double duration, Long strength, Long intensity, String fadeOut,
			Double angleOffset, String eventTag) {
		this();
		this.duration = duration;
		this.strength = strength;
		this.intensity = intensity;
		this.fadeOut = fadeOut;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.duration = SafeDatatypeConverter.toDouble(json.get("duration"));
		this.strength = (Long) json.get("strength");
		this.intensity = (Long) json.get("intensity");
		this.fadeOut = (String) json.get("fadeOut");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "duration", duration);
		save(sb, "strength", strength);
		save(sb, "intensity", intensity);
		save(sb, "fadeOut", fadeOut);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public Double getDuration() {
		return duration;
	}

	public Long getStrength() {
		return strength;
	}

	public Long getIntensity() {
		return intensity;
	}

	public String getFadeOut() {
		return fadeOut;
	}

	public Double getAngleOffset() {
		return angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}

}
