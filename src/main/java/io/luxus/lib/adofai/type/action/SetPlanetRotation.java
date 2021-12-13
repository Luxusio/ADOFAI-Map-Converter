package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.Ease;
import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SetPlanetRotation extends Action {

	private Ease ease = Ease.LINEAR;
	private Long easeParts = 1L;

	public SetPlanetRotation() {
		super(EventType.SET_PLANET_ROTATION);
	}

	public SetPlanetRotation(Ease ease, Long easeParts) {
		this();
		this.ease = ease;
		this.easeParts = easeParts;
	}

}
