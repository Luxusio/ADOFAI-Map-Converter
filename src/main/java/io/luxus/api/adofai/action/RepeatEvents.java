package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class RepeatEvents extends Action {

	private Long repetitions;
	private Double interval;
	private String tag;

	public RepeatEvents() {
		super(EventType.REPEAT_EVENTS);
	}
	
	public RepeatEvents(Long repetitions, Double interval, String tag) {
		this();
		this.repetitions = repetitions;
		this.interval = interval;
		this.tag = tag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.repetitions = (Long) json.get("repetitions");
		this.interval = SafeDatatypeConverter.toDouble(json.get("interval"));
		this.tag = (String) json.get("tag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "repetitions", repetitions);
		save(sb, "interval", interval);
		save(sb, "tag", tag);
		saveAfter(sb);
	}

	public Long getRepetitions() {
		return repetitions;
	}

	public Double getInterval() {
		return interval;
	}

	public String getTag() {
		return tag;
	}

}
