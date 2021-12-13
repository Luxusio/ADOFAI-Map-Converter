package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TrackAnimation;
import io.luxus.lib.adofai.type.TrackDisappearAnimation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class AnimateTrack extends Action {
	
	private TrackAnimation trackAnimation = TrackAnimation.NONE;
	private Double beatsAhead = 3.0;
	private TrackDisappearAnimation trackDisappearAnimation = TrackDisappearAnimation.NONE;
	private Double beatsBehind = 4.0;
	
	public AnimateTrack() {
		super(EventType.ANIMATE_TRACK);
	}

	public AnimateTrack(TrackAnimation trackAnimation, Double beatsAhead, TrackDisappearAnimation trackDisappearAnimation, Double beatsBehind) {
		this();
		this.trackAnimation = trackAnimation;
		this.beatsAhead = beatsAhead;
		this.trackDisappearAnimation = trackDisappearAnimation;
		this.beatsBehind = beatsBehind;
	}

}
