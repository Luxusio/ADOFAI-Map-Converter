package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class ScreenTile extends Action {

	private List<Double> tile = Arrays.asList(1.0, 1.0);
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public ScreenTile() {
		super(EventType.SCREEN_TILE);
	}

	public ScreenTile(List<Double> tile, Double angleOffset, String eventTag) {
		this();
		this.tile = tile;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

}
