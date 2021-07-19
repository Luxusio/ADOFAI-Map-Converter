package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.type.EventType;

public class SetConditionalEvents extends Action {

	private String perfectTag;
	private String hitTag;
	private String barelyTag;
	private String missTag;
	private String lossTag;

	public SetConditionalEvents() {
		super(EventType.SET_CONDITIONAL_EVENTS);
	}

	public SetConditionalEvents(String perfectTag, String hitTag, String barelyTag, String missTag,
			String lossTag) {
		this();
		this.perfectTag = perfectTag;
		this.hitTag = hitTag;
		this.barelyTag = barelyTag;
		this.missTag = missTag;
		this.lossTag = lossTag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.perfectTag = (String) json.get("perfectTag");
		this.hitTag = (String) json.get("hitTag");
		this.barelyTag = (String) json.get("barelyTag");
		this.missTag = (String) json.get("missTag");
		this.lossTag = (String) json.get("lossTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "perfectTag", perfectTag);
		save(sb, "hitTag", hitTag);
		save(sb, "barelyTag", barelyTag);
		save(sb, "missTag", missTag);
		save(sb, "lossTag", lossTag);
		saveAfter(sb);
	}

	public String getPerfectTag() {
		return perfectTag;
	}

	public String getHitTag() {
		return hitTag;
	}

	public String getBarelyTag() {
		return barelyTag;
	}

	public String getMissTag() {
		return missTag;
	}

	public String getLossTag() {
		return lossTag;
	}

}
