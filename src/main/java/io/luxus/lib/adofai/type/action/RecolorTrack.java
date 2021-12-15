package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class RecolorTrack extends Action {

	private final Long startTileNum;
	private final TilePosition startTilePosition;
	private final Long endTileNum;
	private final TilePosition endTilePosition;
	private final TrackColorType trackColorType;
	private final String trackColor;
	private final String secondaryTrackColor;
	private final Double trackColorAnimDuration;
	private final TrackColorPulse trackColorPulse;
	private final Long trackPulseLength;
	private final TrackStyle trackStyle;
	private final Double angleOffset;
	private final String eventTag;

	private RecolorTrack(Long startTileNum, TilePosition startTilePosition, Long endTileNum, TilePosition endTilePosition,
						TrackColorType trackColorType, String trackColor, String secondaryTrackColor, Double trackColorAnimDuration,
						TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle, Double angleOffset, String eventTag) {
		super(EventType.RECOLOR_TRACK);
		this.startTileNum = startTileNum;
		this.startTilePosition = startTilePosition;
		this.endTileNum = endTileNum;
		this.endTilePosition = endTilePosition;
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Long startTileNum = 0L;
		private TilePosition startTilePosition = TilePosition.THIS_TILE;
		private Long endTileNum = 0L;
		private TilePosition endTilePosition = TilePosition.THIS_TILE;
		private TrackColorType trackColorType = TrackColorType.SINGLE;
		private String trackColor = "debb7b";
		private String secondaryTrackColor = "ffffff";
		private Double trackColorAnimDuration = 2.0;
		private TrackColorPulse trackColorPulse = TrackColorPulse.NONE;
		private Long trackPulseLength = 10L;
		private TrackStyle trackStyle = TrackStyle.STANDARD;
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(RecolorTrack src) {
			return self()
					.startTileNum(src.startTileNum)
					.startTilePosition(src.startTilePosition)
					.endTileNum(src.endTileNum)
					.endTilePosition(src.endTilePosition)
					.trackColorType(src.trackColorType)
					.trackColor(src.trackColor)
					.secondaryTrackColor(src.secondaryTrackColor)
					.trackColorAnimDuration(src.trackColorAnimDuration)
					.trackColorPulse(src.trackColorPulse)
					.trackPulseLength(src.trackPulseLength)
					.trackStyle(src.trackStyle)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public RecolorTrack build() {
			return new RecolorTrack(startTileNum, startTilePosition, endTileNum, endTilePosition, trackColorType, trackColor,
					secondaryTrackColor, trackColorAnimDuration, trackColorPulse, trackPulseLength, trackStyle,
					angleOffset, eventTag);
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
		 * setter of startTileNum
		 *
		 * @param startTileNum startTileNum of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when startTileNum is null
		 */
		public Builder startTileNum(Long startTileNum) {
			Objects.requireNonNull(startTileNum);
			this.startTileNum = startTileNum;
			return self();
		}

		/**
		 * setter of startTilePosition
		 *
		 * @param startTilePosition startTilePosition of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when startTilePosition is null
		 */
		public Builder startTilePosition(TilePosition startTilePosition) {
			Objects.requireNonNull(startTilePosition);
			this.startTilePosition = startTilePosition;
			return self();
		}

		/**
		 * setter of endTileNum
		 *
		 * @param endTileNum endTileNum of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when endTileNum is null
		 */
		public Builder endTileNum(Long endTileNum) {
			Objects.requireNonNull(endTileNum);
			this.endTileNum = endTileNum;
			return self();
		}

		/**
		 * setter of endTilePosition
		 *
		 * @param endTilePosition endTilePosition of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when endTilePosition is null
		 */
		public Builder endTilePosition(TilePosition endTilePosition) {
			Objects.requireNonNull(endTilePosition);
			this.endTilePosition = endTilePosition;
			return self();
		}

		/**
		 * setter of trackColorType
		 *
		 * @param trackColorType trackColorType of RecolorTrack Event
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
		 * @param trackColor trackColor of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when trackColor is null
		 * @throws IllegalArgumentException when trackColor with invalid format
		 */
		public Builder trackColor(String trackColor) {
			Objects.requireNonNull(trackColor);
			if (!StringJsonUtil.isRGBCode(trackColor)) {
				throw new IllegalArgumentException("trackColor is not RGB color code!");
			}
			this.trackColor = trackColor;
			return self();
		}

		/**
		 * setter of secondaryTrackColor
		 *
		 * @param secondaryTrackColor secondaryTrackColor of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when secondaryTrackColor is null
		 * @throws IllegalArgumentException when secondaryTrackColor with invalid format
		 */
		public Builder secondaryTrackColor(String secondaryTrackColor) {
			Objects.requireNonNull(secondaryTrackColor);
			if (!StringJsonUtil.isRGBCode(secondaryTrackColor)) {
				throw new IllegalArgumentException("secondaryTrackColor is not RGB color code!");
			}
			this.secondaryTrackColor = secondaryTrackColor;
			return self();
		}

		/**
		 * setter of trackColorAnimDuration
		 *
		 * @param trackColorAnimDuration trackColorAnimDuration of RecolorTrack Event
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
		 * @param trackColorPulse trackColorPulse of RecolorTrack Event
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
		 * @param trackPulseLength trackPulseLength of RecolorTrack Event
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
		 * @param trackStyle trackStyle of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when trackStyle is null
		 */
		public Builder trackStyle(TrackStyle trackStyle) {
			Objects.requireNonNull(trackStyle);
			this.trackStyle = trackStyle;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return self();
		}

		/**
		 * setter of eventTag
		 *
		 * @param eventTag eventTag of RecolorTrack Event
		 * @return self
		 * @throws NullPointerException when eventTag is null
		 */
		public Builder eventTag(String eventTag) {
			Objects.requireNonNull(eventTag);
			this.eventTag = eventTag;
			return self();
		}
	}

}
