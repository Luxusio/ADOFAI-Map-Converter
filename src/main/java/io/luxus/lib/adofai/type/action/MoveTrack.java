package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.Ease;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TilePosition;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.Objects;

@Getter
@ToString
public class MoveTrack extends Action {

	private final Pair<Long, TilePosition> startTile;
	private final Pair<Long, TilePosition> endTile;
	private final Double duration;
	private final Pair<Double, Double> positionOffset;
	private final Double rotationOffset;
	private final Pair<Double, Double> scale;
	private final Double opacity;
	private final Double angleOffset;
	private final Ease ease;
	private final Toggle maxVfxOnly;
	private final String eventTag;

	private MoveTrack(Boolean active, Pair<Long, TilePosition> startTile, Pair<Long, TilePosition> endTile,
					  Double duration, Pair<Double, Double> positionOffset, Double rotationOffset, Pair<Double, Double> scale, Double opacity,
					  Double angleOffset, Ease ease, Toggle maxVfxOnly, String eventTag) {
		super(EventType.MOVE_TRACK, active);
		this.startTile = startTile;
		this.endTile = endTile;
		this.duration = duration;
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
		this.opacity = opacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.maxVfxOnly = maxVfxOnly;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Pair<Long, TilePosition> startTile = Pair.with(0L, TilePosition.THIS_TILE);
		private Pair<Long, TilePosition> endTile = Pair.with(0L, TilePosition.THIS_TILE);
		private Double duration = 1.0;
		private Pair<Double, Double> positionOffset = Pair.with(0.0, 0.0);
		private Double rotationOffset = null;
		private Pair<Double, Double> scale = null;
		private Double opacity = null;
		private Double angleOffset = 0.0;
		private Ease ease = Ease.LINEAR;
		private Toggle maxVfxOnly = Toggle.DISABLED;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(MoveTrack src) {
			return self()
					.startTile(src.startTile)
					.endTile(src.endTile)
					.duration(src.duration)
					.positionOffset(src.positionOffset)
					.rotationOffset(src.rotationOffset)
					.scale(src.scale)
					.opacity(src.opacity)
					.angleOffset(src.angleOffset)
					.ease(src.ease)
					.maxVfxOnly(src.maxVfxOnly)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public MoveTrack build() {
			return new MoveTrack(active, startTile, endTile, duration,
					positionOffset, rotationOffset, scale, opacity, angleOffset, ease, maxVfxOnly, eventTag);
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
			return EventType.MOVE_TRACK;
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
		 * @throws IllegalArgumentException when size of positionOffset is not 2
		 */
		public Builder positionOffset(Pair<Double, Double> positionOffset) {
			this.positionOffset = positionOffset;
			return self();
		}

		/**
		 * setter of rotationOffset
		 *
		 * @param rotationOffset rotationOffset of MoveTrack Event
		 * @return self
		 */
		public Builder rotationOffset(Double rotationOffset) {
			this.rotationOffset = rotationOffset;
			return self();
		}

		/**
		 * setter of scale
		 *
		 * @param scale scale of MoveTrack Event
		 * @return self
		 * @throws IllegalArgumentException when size of scale is not 2
		 */
		public Builder scale(Pair<Double, Double> scale) {
			this.scale = scale;
			return self();
		}

		/**
		 * setter of opacity
		 *
		 * @param opacity opacity of MoveTrack Event
		 * @return self
		 */
		public Builder opacity(Double opacity) {
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

		public Builder maxVfxOnly(Toggle maxVfxOnly) {
			Objects.requireNonNull(maxVfxOnly);
			this.maxVfxOnly = maxVfxOnly;
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
