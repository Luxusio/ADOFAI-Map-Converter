package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class ScreenScroll extends Action {

	private List<Double> scroll = Arrays.asList(0.0, 0.0);
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public ScreenScroll() {
		super(EventType.SCREEN_SCROLL);
	}

	public ScreenScroll(List<Double> scroll, Double angleOffset, String eventTag) {
		this();
		this.scroll = scroll;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

}
