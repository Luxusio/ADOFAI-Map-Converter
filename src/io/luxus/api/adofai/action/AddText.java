package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class AddText extends Action {

	private String decText;
	private String font;
	private List<Double> position;
	private String relativeTo;
	private List<Double> pivotOffset;
	private Double rotation;
	private Long scale;
	private String color;
	private Long depth;
	private String tag;
	
	public AddText() {
		super(EventType.ADD_TEXT);
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.decText = (String) json.get("decText");
		this.font = (String) json.get("font");
		this.position = SafeDatatypeConverter.toDoubleList(json.get("position"));
		this.relativeTo = (String) json.get("relativeTo");
		this.pivotOffset = SafeDatatypeConverter.toDoubleList(json.get("pivotOffset"));
		this.rotation = SafeDatatypeConverter.toDouble(json.get("rotation"));
		this.scale = (Long) json.get("scale");
		this.color = (String) json.get("color");
		this.depth = (Long) json.get("depth");
		this.tag = (String) json.get("tag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "decText", decText);
		save(sb, "font", font);
		save(sb, "position", position);
		save(sb, "relativeTo", relativeTo);
		save(sb, "pivotOffset", pivotOffset);
		save(sb, "rotation", rotation);
		save(sb, "scale", scale);
		save(sb, "color", color);
		save(sb, "depth", depth);
		save(sb, "tag", tag);
		saveAfter(sb);
	}
	
	public String getDecText() {
		return decText;
	}

	public void setDecText(String decText) {
		this.decText = decText;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public List<Double> getPosition() {
		return position;
	}

	public void setPosition(List<Double> position) {
		this.position = position;
	}

	public String getRelativeTo() {
		return relativeTo;
	}

	public void setRelativeTo(String relativeTo) {
		this.relativeTo = relativeTo;
	}

	public List<Double> getPivotOffset() {
		return pivotOffset;
	}

	public void setPivotOffset(List<Double> pivotOffset) {
		this.pivotOffset = pivotOffset;
	}

	public Double getRotation() {
		return rotation;
	}

	public void setRotation(Double rotation) {
		this.rotation = rotation;
	}

	public Long getScale() {
		return scale;
	}

	public void setScale(Long scale) {
		this.scale = scale;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
