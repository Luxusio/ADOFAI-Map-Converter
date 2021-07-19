package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class ColorTrack extends Action {
	
	private String trackColorType;
	private String trackColor;
	private String secondaryTrackColor;
	private Double trackColorAnimDuration;
	private String trackColorPulse;
	private Long trackPulseLength;
	private String trackStyle;
	
	public ColorTrack() {
		super(EventType.COLOR_TRACK);
	}

	public ColorTrack(String trackColorType, String trackColor, String secondaryTrackColor,
			Double trackColorAnimDuration, String trackColorPulse, Long trackPulseLength, String trackStyle) {
		this();
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.trackColorType = (String) json.get("trackColorType");
		this.trackColor = (String) json.get("trackColor");
		this.secondaryTrackColor = (String) json.get("secondaryTrackColor");
		this.trackColorAnimDuration = SafeDatatypeConverter.toDouble(json.get("trackColorAnimDuration"));
		this.trackColorPulse = (String) json.get("trackColorPulse");
		this.trackPulseLength = (Long) json.get("trackPulseLength");
		this.trackStyle = (String) json.get("trackStyle");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "trackColorType", trackColorType);
		save(sb, "trackColor", trackColor);
		save(sb, "secondaryTrackColor", secondaryTrackColor);
		save(sb, "trackColorAnimDuration", trackColorAnimDuration);
		save(sb, "trackColorPulse", trackColorPulse);
		save(sb, "trackPulseLength", trackPulseLength);
		save(sb, "trackStyle", trackStyle);
		saveAfter(sb);
	}

	public String getTrackColorType() {
		return trackColorType;
	}

	public String getTrackColor() {
		return trackColor;
	}

	public String getSecondaryTrackColor() {
		return secondaryTrackColor;
	}

	public Double getTrackColorAnimDuration() {
		return trackColorAnimDuration;
	}

	public String getTrackColorPulse() {
		return trackColorPulse;
	}

	public Long getTrackPulseLength() {
		return trackPulseLength;
	}

	public String getTrackStyle() {
		return trackStyle;
	}
	
}
