package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.Objects;

@Getter
@ToString
public class RecolorTrack extends Action {

	private final Pair<Long, TilePosition> startTile;
	private final Pair<Long, TilePosition> endTile;
	private final TrackColorType trackColorType;
	private final String trackColor;
	private final String secondaryTrackColor;
	private final Double trackColorAnimDuration;
	private final TrackColorPulse trackColorPulse;
	private final Long trackPulseLength;
	private final TrackStyle trackStyle;
	private final Double angleOffset;
	private final String eventTag;

	private RecolorTrack(Boolean active, Pair<Long, TilePosition> startTile, Pair<Long, TilePosition> endTile,
						TrackColorType trackColorType, String trackColor, String secondaryTrackColor, Double trackColorAnimDuration,
						TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle, Double angleOffset, String eventTag) {
		super(EventType.RECOLOR_TRACK, active);
		this.startTile = startTile;
		this.endTile = endTile;
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

		private Pair<Long, TilePosition> startTile = Pair.with(0L, TilePosition.THIS_TILE);
		private Pair<Long, TilePosition> endTile = Pair.with(0L, TilePosition.THIS_TILE);
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
					.startTile(src.startTile)
					.endTile(src.endTile)
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
			return new RecolorTrack(active, startTile, endTile, trackColorType, trackColor,
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
		 * return eventType of Action Builder
		 *
		 * @return eventType
		 */
		@Override
		public EventType getEventType() {
			return EventType.RECOLOR_TRACK;
		}


		public Builder startTile(Pair<Long, TilePosition> startTile) {
			Objects.requireNonNull(startTile);
			this.startTile = startTile;
			return self();
		}

		public Builder endTile(Pair<Long, TilePosition> endTile) {
			Objects.requireNonNull(endTile);
			this.endTile = endTile;
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
