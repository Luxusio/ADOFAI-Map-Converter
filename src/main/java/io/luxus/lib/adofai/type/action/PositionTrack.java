package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class PositionTrack extends Action {

	private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
	private Toggle editorOnly = Toggle.DISABLED;

	public PositionTrack() {
		super(EventType.POSITION_TRACK);
	}

	public PositionTrack(List<Double> positionOffset, Toggle editorOnly) {
		this();
		this.positionOffset = positionOffset;
		this.editorOnly = editorOnly;
	}

}
