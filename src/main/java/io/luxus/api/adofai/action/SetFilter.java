package io.luxus.api.adofai.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class SetFilter extends Action {
	
	private String filter;
	private String enabled;
	private Long intensity;
	private String disableOthers;
	private Double angleOffset;
	private String eventTag;
	
	public SetFilter() {
		super(EventType.SET_FILTER);
	}

	public SetFilter(String filter, String enabled, Long intensity, String disableOthers,
			Double angleOffset, String eventTag) {
		this();
		this.filter = filter;
		this.enabled = enabled;
		this.intensity = intensity;
		this.disableOthers = disableOthers;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.filter = (String) json.get("filter");
		this.enabled = (String) json.get("enabled");
		this.intensity = (Long) json.get("intensity");
		this.disableOthers = (String) json.get("disableOthers");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "filter", filter);
		save(sb, "enabled", enabled);
		save(sb, "intensity", intensity);
		save(sb, "disableOthers", disableOthers);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public String getFilter() {
		return filter;
	}

	public String getEnabled() {
		return enabled;
	}

	public Long getIntensity() {
		return intensity;
	}

	public String getDisableOthers() {
		return disableOthers;
	}

	public Double getAngleOffset() {
		return angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}
	
}
