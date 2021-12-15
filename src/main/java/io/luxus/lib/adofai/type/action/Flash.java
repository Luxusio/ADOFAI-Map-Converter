package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter @Setter
@ToString
public class Flash extends Action {
	
	private Double duration = 1.0;
	private Plane plane = Plane.BACKGROUND;
	private String startColor = "ffffff";
	private Long startOpacity = 100L;
	private String endColor = "ffffff";
	private Long endOpacity = 0L;
	private Double angleOffset = 0.0;
	private Ease ease = Ease.LINEAR;
	private String eventTag = "";
	
	public Flash() {
		super(EventType.FLASH);
	}

	public Flash(Double duration, Plane plane, String startColor, Long startOpacity, String endColor, Long endOpacity, Double angleOffset, Ease ease, String eventTag) {
		this();
		this.duration = duration;
		this.plane = plane;
		this.startColor = startColor;
		this.startOpacity = startOpacity;
		this.endColor = endColor;
		this.endOpacity = endOpacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
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
