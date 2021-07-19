package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class MoveTrack extends Action {
	
	private List<Object> startTile;
	private List<Object> endTile;
	private Double duration;
	private List<Long> positionOffset;
	private Double rotationOffset;
	private Long scale;
	private Long opacity;
	private Double angleOffset;
	private String ease;
	private String eventTag;
	
	public MoveTrack() {
		super(EventType.MOVE_TRACK);
	}

	public MoveTrack(List<Object> startTile, List<Object> endTile, Double duration,
			List<Long> positionOffset, Double rotationOffset, Long scale, Long opacity, Double angleOffset, String ease,
			String eventTag) {
		this();
		this.startTile = startTile;
		this.endTile = endTile;
		this.duration = duration;
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
		this.opacity = opacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(JSONObject json) throws ParseException {
		this.startTile = (List<Object>) json.get("startTile");
		this.endTile = (List<Object>) json.get("endTile");
		this.duration = SafeDatatypeConverter.toDouble(json.get("duration"));
		this.positionOffset = (List<Long>) json.get("positionOffset");
		this.rotationOffset = SafeDatatypeConverter.toDouble(json.get("rotationOffset"));
		this.scale = (Long) json.get("scale");
		this.opacity = (Long) json.get("opacity");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.ease = (String) json.get("ease");
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "startTile", startTile);
		save(sb, "endTile", endTile);
		save(sb, "duration", duration);
		save(sb, "positionOffset", positionOffset);
		save(sb, "rotationOffset", rotationOffset);
		save(sb, "scale", scale);
		save(sb, "opacity", opacity);
		save(sb, "angleOffset", angleOffset);
		save(sb, "ease", ease);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public List<Object> getStartTile() {
		return startTile;
	}

	public List<Object> getEndTile() {
		return endTile;
	}

	public Double getDuration() {
		return duration;
	}

	public List<Long> getPositionOffset() {
		return positionOffset;
	}

	public Double getRotationOffset() {
		return rotationOffset;
	}

	public Long getScale() {
		return scale;
	}

	public Long getOpacity() {
		return opacity;
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
