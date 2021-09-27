package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.Toggle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class HallOfMirrors extends Action {

	private Toggle enabled = Toggle.ENABLED;
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public HallOfMirrors() {
		super(EventType.HALL_OF_MIRRORS);
	}

	public HallOfMirrors(Toggle enabled, Double angleOffset, String eventTag) {
		this();
		this.enabled = enabled;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

}
