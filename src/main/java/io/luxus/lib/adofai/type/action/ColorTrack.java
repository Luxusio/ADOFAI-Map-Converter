package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class ColorTrack extends Action {
	
	private final TrackColorType trackColorType;
	private final String trackColor;
	private final String secondaryTrackColor;
	private final Double trackColorAnimDuration;
	private final TrackColorPulse trackColorPulse;
	private final Long trackPulseLength;
	private final TrackStyle trackStyle;
	private final String trackTexture;
	private final Double trackTextureScale;

	public ColorTrack(TrackColorType trackColorType, String trackColor, String secondaryTrackColor, Double trackColorAnimDuration, TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle,
					  String trackTexture, Double trackTextureScale) {
		super(EventType.COLOR_TRACK);
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
	public static final class Builder extends Action.Builder<Builder> {

		private TrackColorType trackColorType = TrackColorType.SINGLE;
		private String trackColor = "debb7b";
		private String secondaryTrackColor = "ffffff";
		private Double trackColorAnimDuration = 2.0;
		private TrackColorPulse trackColorPulse = TrackColorPulse.NONE;
		private Long trackPulseLength = 10L;
		private TrackStyle trackStyle = TrackStyle.STANDARD;
		private String trackTexture = "";
		private Double trackTextureScale = 1.0;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(ColorTrack src) {
			return self()
					.trackColorType(src.trackColorType)
					.trackColor(src.trackColor)
					.secondaryTrackColor(src.secondaryTrackColor)
					.trackColorAnimDuration(src.trackColorAnimDuration)
					.trackColorPulse(src.trackColorPulse)
					.trackPulseLength(src.trackPulseLength)
					.trackStyle(src.trackStyle)
					.trackTexture(src.trackTexture)
					.trackTextureScale(src.trackTextureScale);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public ColorTrack build() {
			return new ColorTrack(trackColorType, trackColor, secondaryTrackColor, trackColorAnimDuration, trackColorPulse,
					trackPulseLength, trackStyle, trackTexture, trackTextureScale);
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
		 * setter of trackColorType
		 *
		 * @param trackColorType trackColorType of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when trackColorType is null
		 */
		public Builder trackColorType(TrackColorType trackColorType) {
			Objects.requireNonNull(trackColorType);
			this.trackColorType = trackColorType;
			return self();
		}

		/**
		 * setter of trackColor
		 *
		 * @param trackColor trackColor of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when trackColor is null
		 * @throws IllegalArgumentException when trackColor is not rgb code
		 */
		public Builder trackColor(String trackColor) {
			Objects.requireNonNull(trackColor);
			if (StringJsonUtil.isRGBCode(trackColor)) {
				throw new IllegalArgumentException("color is not rgb code");
			}
			this.trackColor = trackColor;
			return self();
		}

		/**
		 * setter of secondaryTrackColor
		 *
		 * @param secondaryTrackColor secondaryTrackColor of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when secondaryTrackColor is null
		 * @throws IllegalArgumentException when secondaryTrackColor is not rgb code
		 */
		public Builder secondaryTrackColor(String secondaryTrackColor) {
			Objects.requireNonNull(secondaryTrackColor);
			if (StringJsonUtil.isRGBCode(secondaryTrackColor)) {
				throw new IllegalArgumentException("color is not rgb code");
			}
			this.secondaryTrackColor = secondaryTrackColor;
			return self();
		}

		/**
		 * setter of trackColorAnimDuration
		 *
		 * @param trackColorAnimDuration trackColorAnimDuration of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when trackColorAnimDuration is null
		 */
		public Builder trackColorAnimDuration(Double trackColorAnimDuration) {
			Objects.requireNonNull(trackColorAnimDuration);
			this.trackColorAnimDuration = trackColorAnimDuration;
			return self();
		}

		/**
		 * setter of trackColorPulse
		 *
		 * @param trackColorPulse trackColorPulse of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when trackColorPulse is null
		 */
		public Builder trackColorPulse(TrackColorPulse trackColorPulse) {
			Objects.requireNonNull(trackColorPulse);
			this.trackColorPulse = trackColorPulse;
			return self();
		}

		/**
		 * setter of trackPulseLength
		 *
		 * @param trackPulseLength trackPulseLength of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when trackPulseLength is null
		 */
		public Builder trackPulseLength(Long trackPulseLength) {
			Objects.requireNonNull(trackPulseLength);
			this.trackPulseLength = trackPulseLength;
			return self();
		}

		/**
		 * setter of trackStyle
		 *
		 * @param trackStyle trackStyle of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when trackStyle is null
		 */
		public Builder trackStyle(TrackStyle trackStyle) {
			Objects.requireNonNull(trackStyle);
			this.trackStyle = trackStyle;
			return self();
		}

		/**
		 * setter of trackTexture
		 *
		 * @param trackTexture trackTexture of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when trackTexture is null
		 */
		public Builder trackTexture(String trackTexture) {
			Objects.requireNonNull(trackTexture);
			this.trackTexture = trackTexture;
			return self();
		}

		/**
		 * setter of trackTextureScale
		 *
		 * @param trackTextureScale trackTextureScale of ColorTrack Event
		 * @return self
		 * @throws NullPointerException when trackTextureScale is null
		 */
		public Builder trackTextureScale(Double trackTextureScale) {
			Objects.requireNonNull(trackTextureScale);
			this.trackTextureScale = trackTextureScale;
			return self();
		}

	}

}
