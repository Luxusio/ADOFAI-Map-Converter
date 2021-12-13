package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.GameSound;
import io.luxus.lib.adofai.type.HitSound;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SetHitsound extends Action {

	private GameSound gameSound = GameSound.HITSOUND;
	private HitSound hitsound = HitSound.KICK;
	private Long hitsoundVolume = 100L;
	
	public SetHitsound() {
		super(EventType.SET_HITSOUND);
	}

	public SetHitsound(GameSound gameSound, HitSound hitsound, Long hitsoundVolume) {
		this();
		this.gameSound = gameSound;
		this.hitsound = hitsound;
		this.hitsoundVolume = hitsoundVolume;
	}

}
