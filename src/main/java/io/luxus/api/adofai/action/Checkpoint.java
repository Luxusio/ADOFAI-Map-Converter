package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.type.EventType;

public class Checkpoint extends Action {
	
	public Checkpoint() {
		super(EventType.CHECK_POINT);
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
