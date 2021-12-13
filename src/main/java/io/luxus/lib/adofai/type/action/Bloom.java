package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Bloom extends Action {
	
	private Toggle enabled = Toggle.ENABLED;
	private Long threshold = 50L;
	private Long intensity = 100L;
	private String color = "ffffff";
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public Bloom() {
		super(EventType.BLOOM);
	}

	public Bloom(Toggle enabled, Long threshold, Long intensity, String color, Double angleOffset,
			String eventTag) {
		this();
		this.enabled = enabled;
		this.threshold = threshold;
		this.intensity = intensity;
		this.color = color;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	
}
