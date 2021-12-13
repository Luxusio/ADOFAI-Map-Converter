package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.HitSound;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class PlaySound extends Action {

	private HitSound hitsound = HitSound.KICK;
	private Long hitsoundVolume = 100L;
	private Double angleOffset = 0.0;

	public PlaySound() {
		super(EventType.PLAY_SOUND);
	}

	public PlaySound(HitSound hitsound, Long hitsoundVolume, Double angleOffset) {
		this();
		this.hitsound = hitsound;
		this.hitsoundVolume = hitsoundVolume;
		this.angleOffset = angleOffset;
	}

}
