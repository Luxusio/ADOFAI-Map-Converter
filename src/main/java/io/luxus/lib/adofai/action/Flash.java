package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.Ease;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.Plane;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Flash extends Action {
	
	private Double duration = 1.0;
	private Plane plane = Plane.BACKGROUND;
	private String startColor = "ffffff";
	private Long startOpacity = 100L;
	private String endColor = "ffffff";
	private Long endOpacity = 0L;
	private Double angleOffset = 0.0;
	private Ease ease = Ease.LINEAR;
	private String eventTag = "";
	
	public Flash() {
		super(EventType.FLASH);
	}

	public Flash(Double duration, Plane plane, String startColor, Long startOpacity, String endColor, Long endOpacity, Double angleOffset, Ease ease, String eventTag) {
		this();
		this.duration = duration;
		this.plane = plane;
		this.startColor = startColor;
		this.startOpacity = startOpacity;
		this.endColor = endColor;
		this.endOpacity = endOpacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

}
