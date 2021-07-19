package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class AddDecoration extends Action {
	
	private String decorationImage;
	private List<Long> position;
	private String relativeTo;
	private List<Long> pivotOffset;
	private Double rotation;
	private Long scale;
	private Long depth;
	private String tag;
	
	public AddDecoration() {
		super(EventType.ADD_DECORATION);
	}
	
	public AddDecoration(String decorationImage, List<Long> position, String relativeTo,
			List<Long> pivotOffset, Double rotation, Long scale, Long depth, String tag) {
		this();
		this.decorationImage = decorationImage;
		this.position = position;
		this.relativeTo = relativeTo;
		this.pivotOffset = pivotOffset;
		this.rotation = rotation;
		this.scale = scale;
		this.depth = depth;
		this.tag = tag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(JSONObject json) throws ParseException {
		this.decorationImage = (String) json.get("decorationImage");
		this.position = (List<Long>) json.get("position");
		this.relativeTo = (String) json.get("relativeTo");
		this.pivotOffset = (List<Long>) json.get("pivotOffset");
		this.rotation = SafeDatatypeConverter.toDouble(json.get("rotation"));
		this.scale = (Long) json.get("scale");
		this.depth = (Long) json.get("depth");
		this.tag = (String) json.get("tag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "decorationImage", decorationImage);
		save(sb, "position", position);
		save(sb, "relativeTo", relativeTo);
		save(sb, "pivotOffset", pivotOffset);
		save(sb, "rotation", rotation);
		save(sb, "scale", scale);
		save(sb, "depth", depth);
		save(sb, "tag", tag);
		saveAfter(sb);
	}

	public String getDecorationImage() {
		return decorationImage;
	}

	public List<Long> getPosition() {
		return position;
	}

	public String getRelativeTo() {
		return relativeTo;
	}

	public List<Long> getPivotOffset() {
		return pivotOffset;
	}

	public Double getRotation() {
		return rotation;
	}

	public Long getScale() {
		return scale;
	}

	public Long getDepth() {
		return depth;
	}

	public String getTag() {
		return tag;
	}
	
}
