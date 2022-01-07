package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class ChangeTrack extends Action {

	private final TrackColorType trackColorType;
	private final String trackColor;
	private final String secondaryTrackColor;
	private final Double trackColorAnimDuration;
	private final TrackColorPulse trackColorPulse;
	private final Long trackPulseLength;
	private final TrackStyle trackStyle;
	private final TrackAnimation trackAnimation;
	private final Double beatsAhead;
	private final TrackDisappearAnimation trackDisappearAnimation;
	private final Double beatsBehind;

	private ChangeTrack(TrackColorType trackColorType, String trackColor, String secondaryTrackColor,
					   Double trackColorAnimDuration, TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle,
					   TrackAnimation trackAnimation, Double beatsAhead, TrackDisappearAnimation trackDisappearAnimation, Double beatsBehind) {
		super(EventType.CHANGE_TRACK);
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
		this.trackAnimation = trackAnimation;
		this.beatsAhead = beatsAhead;
		this.trackDisappearAnimation = trackDisappearAnimation;
		this.beatsBehind = beatsBehind;
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
		public Builder from(ChangeTrack src) {
			return self()
					.trackColorType(src.trackColorType)
					.trackColor(src.trackColor)
					.secondaryTrackColor(src.secondaryTrackColor)
					.trackColorAnimDuration(src.trackColorAnimDuration)
					.trackColorPulse(src.trackColorPulse)
					.trackPulseLength(src.trackPulseLength)
					.trackStyle(src.trackStyle)
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
		public ChangeTrack build() {
			return new ChangeTrack(trackColorType, trackColor, secondaryTrackColor, trackColorAnimDuration, trackColorPulse,
					trackPulseLength, trackStyle, trackAnimation, beatsAhead, trackDisappearAnimation, beatsBehind);
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
		 * return eventType of Action Builder
		 *
		 * @return eventType
		 */
		@Override
		public EventType getEventType() {
			return EventType.CHANGE_TRACK;
		}

		/**
		 * setter of trackColorType
		 *
		 * @param trackColorType trackColorType of ChangeTrack Event
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
		 * @param trackColor trackColor of ChangeTrack Event
		 * @return self
		 * @throws NullPointerException when trackColor is null
		 * @throws IllegalArgumentException when trackColor is not rgb code
		 */
		public Builder trackColor(String trackColor) {
			Objects.requireNonNull(trackColor);
			if (!StringJsonUtil.isRGBCode(trackColor)) {
				throw new IllegalArgumentException("color is not rgb code");
			}
			this.trackColor = trackColor;
			return self();
		}

		/**
		 * setter of secondaryTrackColor
		 *
		 * @param secondaryTrackColor secondaryTrackColor of ChangeTrack Event
		 * @return self
		 * @throws NullPointerException when secondaryTrackColor is null
		 * @throws IllegalArgumentException when secondaryTrackColor is not rgb code
		 */
		public Builder secondaryTrackColor(String secondaryTrackColor) {
			Objects.requireNonNull(secondaryTrackColor);
			if (!StringJsonUtil.isRGBCode(secondaryTrackColor)) {
				throw new IllegalArgumentException("color is not rgb code");
			}
			this.secondaryTrackColor = secondaryTrackColor;
			return self();
		}

		/**
		 * setter of trackColorAnimDuration
		 *
		 * @param trackColorAnimDuration trackColorAnimDuration of ChangeTrack Event
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
		 * @param trackColorPulse trackColorPulse of ChangeTrack Event
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
		 * @param trackPulseLength trackPulseLength of ChangeTrack Event
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
		 * @param trackStyle trackStyle of ChangeTrack Event
		 * @return self
		 * @throws NullPointerException when trackStyle is null
		 */
		public Builder trackStyle(TrackStyle trackStyle) {
			Objects.requireNonNull(trackStyle);
			this.trackStyle = trackStyle;
			return self();
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of ChangeTrack Event
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
		 * @param beatsAhead beatsAhead of ChangeTrack Event
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
		 * @param trackDisappearAnimation trackDisappearAnimation of ChangeTrack Event
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
		 * @param beatsBehind beatsBehind of ChangeTrack Event
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
