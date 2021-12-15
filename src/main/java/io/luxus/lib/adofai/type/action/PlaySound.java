package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.HitSound;
import io.luxus.lib.adofai.type.TrackAnimation;
import io.luxus.lib.adofai.type.TrackDisappearAnimation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

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


	@Getter
	@ToString
	public static final class Builder extends Action.Builder<AnimateTrack.Builder> {

		private TrackAnimation trackAnimation = TrackAnimation.NONE;
		private Double beatsAhead = 3.0;
		private TrackDisappearAnimation trackDisappearAnimation = TrackDisappearAnimation.NONE;
		private Double beatsBehind = 4.0;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public AnimateTrack.Builder from(AnimateTrack src) {
			return self()
					.trackAnimation(src.trackAnimation)
					.beatsAhead(src.beatsAhead)
					.trackDisappearAnimation(src.trackDisappearAnimation)
					.beatsBehind(src.beatsBehind);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public AnimateTrack build() {
			return new AnimateTrack(trackAnimation, beatsAhead, trackDisappearAnimation, beatsBehind);
		}

		/**
		 * return self
		 *
		 * @return self
		 */
		@Override
		public AnimateTrack.Builder self() {
			return this;
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of AnimateTrack Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public AnimateTrack.Builder trackAnimation(TrackAnimation trackAnimation) {
			Objects.requireNonNull(trackAnimation);
			this.trackAnimation = trackAnimation;
			return self();
		}
	}

}
