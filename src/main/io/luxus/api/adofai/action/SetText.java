package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class SetText extends Action {
	
	private String decText;
	private String tag;
	private Double angleOffset;
	private String eventTag;
	
	public SetText() {
		super(EventType.SET_TEXT);
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.decText = (String) json.get("decText");
		this.tag = (String) json.get("tag");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "decText", decText);
		save(sb, "tag", tag);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}
	

	public String getDecText() {
		return decText;
	}

	public void setDecText(String decText) {
		this.decText = decText;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Double getAngleOffset() {
		return angleOffset;
	}

	public void setAngleOffset(Double angleOffset) {
		this.angleOffset = angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}

	public void setEventTag(String eventTag) {
		this.eventTag = eventTag;
	}
	
}
