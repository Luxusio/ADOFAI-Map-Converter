package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class AnimateTrack extends Action {
	
	private final TrackAnimation trackAnimation;
	private final Double beatsAhead;
	private final TrackDisappearAnimation trackDisappearAnimation;
	private final Double beatsBehind;

	private AnimateTrack(TrackAnimation trackAnimation, Double beatsAhead, TrackDisappearAnimation trackDisappearAnimation, Double beatsBehind) {
		super(EventType.ANIMATE_TRACK);
		this.trackAnimation = trackAnimation;
		this.beatsAhead = beatsAhead;
		this.trackDisappearAnimation = trackDisappearAnimation;
		this.beatsBehind = beatsBehind;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

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
		public Builder from(AnimateTrack src) {
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
		public Builder self() {
			return this;
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of AnimateTrack Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public Builder trackAnimation(TrackAnimation trackAnimation) {
			Objects.requireNonNull(trackAnimation);
			this.trackAnimation = trackAnimation;
			return self();
		}

		/**
		 * setter of beatsAhead
		 *
		 * @param beatsAhead beatsAhead of AnimateTrack Event
		 * @return self
		 * @throws NullPointerException when beatsAhead is null
		 */
		public Builder beatsAhead(Double beatsAhead) {
			Objects.requireNonNull(beatsAhead);
			this.beatsAhead = beatsAhead;
			return self();
		}

		/**
		 * setter of trackDisappearAnimation
		 *
		 * @param trackDisappearAnimation trackDisappearAnimation of AnimateTrack Event
		 * @return self
		 * @throws NullPointerException when trackDisappearAnimation is null
		 */
		public Builder trackDisappearAnimation(TrackDisappearAnimation trackDisappearAnimation) {
			Objects.requireNonNull(trackDisappearAnimation);
			this.trackDisappearAnimation = trackDisappearAnimation;
			return self();
		}

		/**
		 * setter of beatsBehind
		 *
		 * @param beatsBehind beatsBehind of AnimateTrack Event
		 * @return self
		 * @throws NullPointerException when beatsBehind is null
		 */
		public Builder beatsBehind(Double beatsBehind) {
			Objects.requireNonNull(beatsBehind);
			this.beatsBehind = beatsBehind;
			return self();
		}
	}

}
