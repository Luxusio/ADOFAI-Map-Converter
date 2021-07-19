package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class PositionTrack extends Action {

	private List<Double> positionOffset;
	private String editorOnly;

	public PositionTrack() {
		super(EventType.POSITION_TRACK);
	}

	public PositionTrack(List<Double> positionOffset, String editorOnly) {
		this();
		this.positionOffset = positionOffset;
		this.editorOnly = editorOnly;
	}

	@Override
	public void load(JSONObject json) throws ParseException {
		this.positionOffset = SafeDatatypeConverter.toDoubleList(json.get("positionOffset"));
		this.editorOnly = (String) json.get("editorOnly");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "positionOffset", positionOffset);
		save(sb, "editorOnly", editorOnly);
		saveAfter(sb);
	}

	public List<Double> getPositionOffset() {
		return positionOffset;
	}

	public String getEditorOnly() {
		return editorOnly;
	}

}
