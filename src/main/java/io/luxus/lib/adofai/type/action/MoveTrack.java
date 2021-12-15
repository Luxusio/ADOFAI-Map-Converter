package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class MoveTrack extends Action {

	private final Long startTileNum;
	private final TilePosition startTilePosition;
	private final Long endTileNum;
	private final TilePosition endTilePosition;
	private final Double duration;
	private final List<Double> positionOffset;
	private final Double rotationOffset;
	private final List<Long> scale;
	private final Long opacity;
	private final Double angleOffset;
	private final Ease ease;
	private final String eventTag;

	private MoveTrack(Long startTileNum, TilePosition startTilePosition, Long endTileNum, TilePosition endTilePosition,
					 Double duration, List<Double> positionOffset, Double rotationOffset, List<Long> scale, Long opacity,
					 Double angleOffset, Ease ease, String eventTag) {
		super(EventType.MOVE_TRACK);
		this.startTileNum = startTileNum;
		this.startTilePosition = startTilePosition;
		this.endTileNum = endTileNum;
		this.endTilePosition = endTilePosition;
		this.duration = duration;
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
		this.opacity = opacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Long startTileNum = 0L;
		private TilePosition startTilePosition = TilePosition.THIS_TILE;
		private Long endTileNum = 0L;
		private TilePosition endTilePosition = TilePosition.THIS_TILE;
		private Double duration = 1.0;
		private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
		private Double rotationOffset = 0.0;
		private List<Long> scale = Arrays.asList(100L, 100L);
		private Long opacity = 100L;
		private Double angleOffset = 0.0;
		private Ease ease = Ease.LINEAR;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(MoveTrack src) {
			return self()
					.startTileNum(src.startTileNum)
					.startTilePosition(src.startTilePosition)
					.endTileNum(src.endTileNum)
					.endTilePosition(src.endTilePosition)
					.duration(src.duration)
					.positionOffset(src.positionOffset)
					.rotationOffset(src.rotationOffset)
					.scale(src.scale)
					.opacity(src.opacity)
					.angleOffset(src.angleOffset)
					.ease(src.ease)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public MoveTrack build() {
			return new MoveTrack(startTileNum, startTilePosition, endTileNum, endTilePosition, duration,
					positionOffset, rotationOffset, scale, opacity, angleOffset, ease, eventTag);
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
		 * @param startTileNum startTileNum of MoveTrack Event
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
		 * @param startTilePosition startTilePosition of MoveTrack Event
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
		 * @param endTileNum endTileNum of MoveTrack Event
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
		 * @param endTilePosition endTilePosition of MoveTrack Event
		 * @return self
		 * @throws NullPointerException when endTilePosition is null
		 */
		public Builder endTilePosition(TilePosition endTilePosition) {
			Objects.requireNonNull(endTilePosition);
			this.endTilePosition = endTilePosition;
			return self();
		}

		/**
		 * setter of duration
		 *
		 * @param duration duration of MoveTrack Event
		 * @return self
		 * @throws NullPointerException when duration is null
		 */
		public Builder duration(Double duration) {
			Objects.requireNonNull(duration);
			this.duration = duration;
			return self();
		}

		/**
		 * setter of positionOffset
		 *
		 * @param positionOffset positionOffset of MoveTrack Event
		 * @return self
		 * @throws NullPointerException when positionOffset is null
		 * @throws IllegalArgumentException when size of positionOffset is not 2
		 */
		public Builder positionOffset(List<Double> positionOffset) {
			Objects.requireNonNull(positionOffset);
			if (positionOffset.size() != 2) {
				throw new IllegalArgumentException("size of positionOffset must be 2");
			}
			this.positionOffset = positionOffset;
			return self();
		}

		/**
		 * setter of rotationOffset
		 *
		 * @param rotationOffset rotationOffset of MoveTrack Event
		 * @return self
		 * @throws NullPointerException when rotationOffset is null
		 */
		public Builder rotationOffset(Double rotationOffset) {
			Objects.requireNonNull(rotationOffset);
			this.rotationOffset = rotationOffset;
			return self();
		}

		/**
		 * setter of scale
		 *
		 * @param scale scale of MoveTrack Event
		 * @return self
		 * @throws NullPointerException when scale is null
		 * @throws IllegalArgumentException when size of scale is not 2
		 */
		public Builder scale(List<Long> scale) {
			Objects.requireNonNull(scale);
			if (scale.size() != 2) {
				throw new IllegalArgumentException("size of scale must be 2");
			}
			this.scale = scale;
			return self();
		}

		/**
		 * setter of opacity
		 *
		 * @param opacity opacity of MoveTrack Event
		 * @return self
		 * @throws NullPointerException when opacity is null
		 */
		public Builder opacity(Long opacity) {
			Objects.requireNonNull(opacity);
			this.opacity = opacity;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of MoveTrack Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return self();
		}

		/**
		 * setter of ease
		 *
		 * @param ease ease of MoveTrack Event
		 * @return self
		 * @throws NullPointerException when ease is null
		 */
		public Builder ease(Ease ease) {
			Objects.requireNonNull(ease);
			this.ease = ease;
			return self();
		}

		/**
		 * setter of eventTag
		 *
		 * @param eventTag eventTag of MoveTrack Event
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
