package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class AnimateTrack extends Action {
	
	private String trackAnimation;
	private Double beatsAhead;
	private String trackDisappearAnimation;
	private Double beatsBehind;
	
	public AnimateTrack() {
		super(EventType.ANIMATE_TRACK);
	}

	public AnimateTrack(String trackAnimation, Double beatsAhead, String trackDisappearAnimation,
			Double beatsBehind) {
		this();
		this.trackAnimation = trackAnimation;
		this.beatsAhead = beatsAhead;
		this.trackDisappearAnimation = trackDisappearAnimation;
		this.beatsBehind = beatsBehind;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.trackAnimation = (String) json.get("trackAnimation");
		this.beatsAhead = SafeDatatypeConverter.toDouble(json.get("beatsAhead"));
		this.trackDisappearAnimation = (String) json.get("trackDisappearAnimation");
		this.beatsBehind = SafeDatatypeConverter.toDouble(json.get("beatsBehind"));
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "trackAnimation", trackAnimation);
		save(sb, "beatsAhead", beatsAhead);
		save(sb, "trackDisappearAnimation", trackDisappearAnimation);
		save(sb, "beatsBehind", beatsBehind);
		saveAfter(sb);
	}

	public String getTrackAnimation() {
		return trackAnimation;
	}

	public Double getBeatsAhead() {
		return beatsAhead;
	}

	public String getTrackDisappearAnimation() {
		return trackDisappearAnimation;
	}

	public Double getBeatsBehind() {
		return beatsBehind;
	}
	
}
