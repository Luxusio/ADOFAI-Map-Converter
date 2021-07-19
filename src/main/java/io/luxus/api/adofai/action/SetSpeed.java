package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class SetSpeed extends Action {
	
	private String speedType;
	private Double beatsPerMinute;
	private Double bpmMultiplier;
	
	public SetSpeed() {
		super(EventType.SET_SPEED);
	}
	
	public SetSpeed(String speedType, Double beatsPerMinute, Double bpmMultiplier) {
		this();
		this.speedType = speedType;
		this.beatsPerMinute = beatsPerMinute;
		this.bpmMultiplier = bpmMultiplier;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.speedType = (String) json.get("speedType");
		this.beatsPerMinute = SafeDatatypeConverter.toDouble(json.get("beatsPerMinute"));
		this.bpmMultiplier = SafeDatatypeConverter.toDouble(json.get("bpmMultiplier"));
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "speedType", speedType);
		save(sb, "beatsPerMinute", beatsPerMinute);
		save(sb, "bpmMultiplier", bpmMultiplier);
		saveAfter(sb);
	}

	public String getSpeedType() {
		return speedType;
	}

	public Double getBeatsPerMinute() {
		return beatsPerMinute;
	}

	public Double getBpmMultiplier() {
		return bpmMultiplier;
	}

}
