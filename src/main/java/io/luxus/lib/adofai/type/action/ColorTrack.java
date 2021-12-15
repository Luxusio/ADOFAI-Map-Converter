package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter @Setter
@ToString
public class ColorTrack extends Action {
	
	private TrackColorType trackColorType = TrackColorType.SINGLE;
	private String trackColor = "debb7b";
	private String secondaryTrackColor = "ffffff";
	private Double trackColorAnimDuration = 2.0;
	private TrackColorPulse trackColorPulse = TrackColorPulse.NONE;
	private Long trackPulseLength = 10L;
	private TrackStyle trackStyle = TrackStyle.STANDARD;
	private String trackTexture = "";
	private Double trackTextureScale = 1.0;
	
	public ColorTrack() {
		super(EventType.COLOR_TRACK);
	}

	public ColorTrack(TrackColorType trackColorType, String trackColor, String secondaryTrackColor, Double trackColorAnimDuration, TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle,
					  String trackTexture, Double trackTextureScale) {
		this();
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
		this.trackTexture = trackTexture;
		this.trackTextureScale = trackTextureScale;
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
