package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.type.EventType;

public class SetPlanetRotation extends Action {

	private String ease;
	private Long easeParts;

	public SetPlanetRotation() {
		super(EventType.SET_PLANET_ROTATION);
	}

	public SetPlanetRotation(String ease, Long easeParts) {
		this();
		this.ease = ease;
		this.easeParts = easeParts;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.ease = (String) json.get("ease");
		this.easeParts = (Long) json.get("easeParts");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "ease", ease);
		save(sb, "easeParts", easeParts);
		saveAfter(sb);
	}

	public String getEase() {
		return ease;
	}

	public Long getEaseParts() {
		return easeParts;
	}

}
