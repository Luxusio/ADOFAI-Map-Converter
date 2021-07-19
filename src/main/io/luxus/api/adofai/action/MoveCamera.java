package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class MoveCamera extends Action {

	private Double duration;
	private String relativeTo;
	private List<Double> position;
	private Double rotation;
	private Long zoom;
	private Double angleOffset;
	private String ease;
	private String eventTag;

	public MoveCamera() {
		super(EventType.MOVE_CAMERA);
	}

	public MoveCamera(Double duration, String relativeTo, List<Double> position, Double rotation, Long zoom,
			Double angleOffset, String ease, String eventTag) {
		this();
		this.duration = duration;
		this.relativeTo = relativeTo;
		this.position = position;
		this.rotation = rotation;
		this.zoom = zoom;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.duration = SafeDatatypeConverter.toDouble(json.get("duration"));
		this.relativeTo = (String) json.get("relativeTo");
		this.position = SafeDatatypeConverter.toDoubleList(json.get("position"));
		this.rotation = SafeDatatypeConverter.toDouble(json.get("rotation"));
		this.zoom = (Long) json.get("zoom");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.ease = (String) json.get("ease");
		this.eventTag = (String) json.get("eventTag");
	}

	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "duration", duration);
		save(sb, "relativeTo", relativeTo);
		save(sb, "position", position);
		save(sb, "rotation", rotation);
		save(sb, "zoom", zoom);
		save(sb, "angleOffset", angleOffset);
		save(sb, "ease", ease);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public Double getDuration() {
		return duration;
	}

	public String getRelativeTo() {
		return relativeTo;
	}

	public List<Double> getPosition() {
		return position;
	}

	public Double getRotation() {
		return rotation;
	}

	public Long getZoom() {
		return zoom;
	}

	public Double getAngleOffset() {
		return angleOffset;
	}

	public String getEase() {
		return ease;
	}

	public String getEventTag() {
		return eventTag;
	}

}
