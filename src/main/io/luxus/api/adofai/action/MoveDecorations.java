package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class MoveDecorations extends Action {

	private Double duration;
	private String tag;
	private List<Double> positionOffset;
	private Double rotationOffset;
	private Long scale;
	private Double angleOffset;
	private String ease;
	private String eventTag;

	public MoveDecorations() {
		super(EventType.MOVE_DECORATIONS);
	}

	public MoveDecorations(Double duration, String tag, List<Double> positionOffset,
			Double rotationOffset, Long scale, Double angleOffset, String ease, String eventTag) {
		this();
		this.duration = duration;
		this.tag = tag;
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.duration = SafeDatatypeConverter.toDouble(json.get("duration"));
		this.tag = (String) json.get("tag");
		this.positionOffset = SafeDatatypeConverter.toDoubleList(json.get("positionOffset"));
		this.rotationOffset = SafeDatatypeConverter.toDouble(json.get("rotationOffset"));
		this.scale = (Long) json.get("scale");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.ease = (String) json.get("ease");
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "duration", duration);
		save(sb, "tag", tag);
		save(sb, "positionOffset", positionOffset);
		save(sb, "rotationOffset", rotationOffset);
		save(sb, "scale", scale);
		save(sb, "angleOffset", angleOffset);
		save(sb, "ease", ease);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public Double getDuration() {
		return duration;
	}

	public String getTag() {
		return tag;
	}

	public List<Double> getPositionOffset() {
		return positionOffset;
	}

	public Double getRotationOffset() {
		return rotationOffset;
	}

	public Long getScale() {
		return scale;
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
