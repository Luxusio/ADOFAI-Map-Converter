package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@ToString
public class CustomBackground extends Action {
	
	private String color = "000000";
	private String bgImage = "";
	private String imageColor = "ffffff";
	private List<Double> parallax = Arrays.asList(100.0, 100.0);
	private BGDisplayModeType bgDisplayMode = BGDisplayModeType.FIT_TO_SCREEN;
	private Toggle lockRot = Toggle.DISABLED;
	private Toggle loopBG = Toggle.DISABLED;
	private Long unscaledSize = 100L;
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public CustomBackground() {
		super(EventType.CUSTOM_BACKGROUND);
	}

	public CustomBackground(String color, String bgImage, String imageColor, List<Double> parallax, BGDisplayModeType bgDisplayMode, Toggle lockRot, Toggle loopBG, Long unscaledSize, Double angleOffset, String eventTag) {
		this();
		this.color = color;
		this.bgImage = bgImage;
		this.imageColor = imageColor;
		this.parallax = parallax;
		this.bgDisplayMode = bgDisplayMode;
		this.lockRot = lockRot;
		this.loopBG = loopBG;
		this.unscaledSize = unscaledSize;
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
