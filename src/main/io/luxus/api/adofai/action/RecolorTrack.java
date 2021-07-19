package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class RecolorTrack extends Action {
	
	private List<Object> startTile;
	private List<Object> endTile;
	private String trackColorType;
	private String trackColor;
	private String secondaryTrackColor;
	private Double trackColorAnimDuration;
	private String trackColorPulse;
	private Long trackPulseLength;
	private String trackStyle;
	private Double angleOffset;
	private String eventTag;
	
	public RecolorTrack() {
		super(EventType.RECOLOR_TRACK);
	}

	public RecolorTrack(List<Object> startTile, List<Object> endTile, String trackColorType,
			String trackColor, String secondaryTrackColor, Double trackColorAnimDuration, String trackColorPulse,
			Long trackPulseLength, String trackStyle, Double angleOffset, String eventTag) {
		this();
		this.startTile = startTile;
		this.endTile = endTile;
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(JSONObject json) throws ParseException {
		this.startTile = (List<Object>) json.get("startTile");
		this.endTile = (List<Object>) json.get("endTile");
		this.trackColorType = (String) json.get("trackColorType");
		this.trackColor = (String) json.get("trackColor");
		this.secondaryTrackColor = (String) json.get("secondaryTrackColor");
		this.trackColorAnimDuration = SafeDatatypeConverter.toDouble(json.get("trackColorAnimDuration"));
		this.trackColorPulse = (String) json.get("trackColorPulse");
		this.trackPulseLength = (Long) json.get("trackPulseLength");
		this.trackStyle = (String) json.get("trackStyle");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "startTile", startTile);
		save(sb, "endTile", endTile);
		save(sb, "trackColorType", trackColorType);
		save(sb, "trackColor", trackColor);
		save(sb, "secondaryTrackColor", secondaryTrackColor);
		save(sb, "trackColorAnimDuration", trackColorAnimDuration);
		save(sb, "trackColorPulse", trackColorPulse);
		save(sb, "trackPulseLength", trackPulseLength);
		save(sb, "trackStyle", trackStyle);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public List<Object> getStartTile() {
		return startTile;
	}

	public List<Object> getEndTile() {
		return endTile;
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

	public Double getAngleOffset() {
		return angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}
	
}
