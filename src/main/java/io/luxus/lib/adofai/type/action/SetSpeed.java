package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.SpeedType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SetSpeed extends Action {
	
	private SpeedType speedType = SpeedType.BPM;
	private Double beatsPerMinute = 100.0;
	private Double bpmMultiplier = 1.0;
	
	public SetSpeed() {
		super(EventType.SET_SPEED);
	}
	
	public SetSpeed(SpeedType speedType, Double beatsPerMinute, Double bpmMultiplier) {
		this();
		if (speedType != null) this.speedType = speedType;
		this.beatsPerMinute = beatsPerMinute;
		this.bpmMultiplier = bpmMultiplier;
	}

}
