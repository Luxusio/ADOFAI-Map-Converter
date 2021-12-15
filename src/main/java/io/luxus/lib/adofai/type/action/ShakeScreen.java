package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import io.luxus.lib.adofai.type.TrackAnimation;
import io.luxus.lib.adofai.type.TrackDisappearAnimation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

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
