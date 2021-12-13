package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ShakeScreen extends Action {

	private Double duration = 1.0;
	private Long strength = 100L;
	private Long intensity = 100L;
	private Toggle fadeOut = Toggle.ENABLED;
	private Double angleOffset = 0.0;
	private String eventTag = "";

	public ShakeScreen() {
		super(EventType.SHAKE_SCREEN);
	}

	public ShakeScreen(Double duration, Long strength, Long intensity, Toggle fadeOut, Double angleOffset, String eventTag) {
		this();
		this.duration = duration;
		this.strength = strength;
		this.intensity = intensity;
		this.fadeOut = fadeOut;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

}
