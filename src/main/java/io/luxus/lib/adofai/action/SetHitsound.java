package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.HitSound;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SetHitsound extends Action {
	
	private HitSound hitsound = HitSound.KICK;
	private Long hitsoundVolume = 100L;
	
	public SetHitsound() {
		super(EventType.SET_HITSOUND);
	}

	public SetHitsound(HitSound hitsound, Long hitsoundVolume) {
		this();
		this.hitsound = hitsound;
		this.hitsoundVolume = hitsoundVolume;
	}

}
