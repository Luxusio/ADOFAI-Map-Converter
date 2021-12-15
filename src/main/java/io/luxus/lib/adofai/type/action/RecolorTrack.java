package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter @Setter
@ToString
public class RecolorTrack extends Action {

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
	
	public RecolorTrack() {
		super(EventType.RECOLOR_TRACK);
	}

	public RecolorTrack(Long startTileNum, TilePosition startTilePosition, Long endTileNum, TilePosition endTilePosition,
						TrackColorType trackColorType, String trackColor, String secondaryTrackColor, Double trackColorAnimDuration,
						TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle, Double angleOffset, String eventTag) {
		this();
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
