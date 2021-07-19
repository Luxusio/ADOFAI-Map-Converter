package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.type.EventType;

public class ChangeTrack extends Action {

	private String trackColorType;
	private String trackColor;
	private String secondaryTrackColor;
	private Long trackColorAnimDuration;
	private String trackColorPulse;
	private Long trackPulseLength;
	private String trackStyle;
	private String trackAnimation;
	private Long beatsAhead;
	private String trackDisappearAnimation;
	private Long beatsBehind;

	public ChangeTrack() {
		super(EventType.CHANGE_TRACK);
	}

	public ChangeTrack(String trackColorType, String trackColor, String secondaryTrackColor,
			Long trackColorAnimDuration, String trackColorPulse, Long trackPulseLength, String trackStyle,
			String trackAnimation, Long beatsAhead, String trackDisappearAnimation, Long beatsBehind) {
		this();
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
		this.trackAnimation = trackAnimation;
		this.beatsAhead = beatsAhead;
		this.trackDisappearAnimation = trackDisappearAnimation;
		this.beatsBehind = beatsBehind;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.trackColorType = (String) json.get("trackColorType");
		this.trackColor = (String) json.get("trackColor");
		this.secondaryTrackColor = (String) json.get("secondaryTrackColor");
		this.trackColorAnimDuration = (Long) json.get("trackColorAnimDuration");
		this.trackColorPulse = (String) json.get("trackColorPulse");
		this.trackPulseLength = (Long) json.get("trackPulseLength");
		this.trackStyle = (String) json.get("trackStyle");
		this.trackAnimation = (String) json.get("trackAnimation");
		this.beatsAhead = (Long) json.get("beatsAhead");
		this.trackDisappearAnimation = (String) json.get("trackDisappearAnimation");
		this.beatsBehind = (Long) json.get("beatsBehind");
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
		save(sb, "trackAnimation", trackAnimation);
		save(sb, "beatsAhead", beatsAhead);
		save(sb, "trackDisappearAnimation", trackDisappearAnimation);
		save(sb, "beatsBehind", beatsBehind);
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

	public Long getTrackColorAnimDuration() {
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

	public String getTrackAnimation() {
		return trackAnimation;
	}

	public Long getBeatsAhead() {
		return beatsAhead;
	}

	public String getTrackDisappearAnimation() {
		return trackDisappearAnimation;
	}

	public Long getBeatsBehind() {
		return beatsBehind;
	}

}
