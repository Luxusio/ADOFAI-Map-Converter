package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.type.EventType;

public class Twirl extends Action {
	
	public Twirl() {
		super(EventType.TWIRL);
	}

	@Override
	public void load(JSONObject json) throws ParseException {
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		saveAfter(sb);
	}
	
}
