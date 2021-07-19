package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.type.EventType;

public class SetHitsound extends Action {
	
	private String hitsound;
	private Long hitsoundVolume;
	
	public SetHitsound() {
		super(EventType.SET_HITSOUND);
	}

	public SetHitsound(String hitsound, Long hitsoundVolume) {
		this();
		this.hitsound = hitsound;
		this.hitsoundVolume = hitsoundVolume;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.hitsound = (String) json.get("hitsound");
		this.hitsoundVolume = (Long) json.get("hitsoundVolume");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "hitsound", hitsound);
		save(sb, "hitsoundVolume", hitsoundVolume);
		saveAfter(sb);
	}

	public String getHitsound() {
		return hitsound;
	}

	public Long getHitsoundVolume() {
		return hitsoundVolume;
	}
	
}
