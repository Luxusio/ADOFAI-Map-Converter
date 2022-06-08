package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import io.luxus.lib.adofai.util.ListUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class PositionTrack extends Action {

	private final List<Double> positionOffset;
	private final Double rotation;
	private final Double scale;
	private final Double opacity;
	private final Toggle editorOnly;

	private PositionTrack(Boolean active, List<Double> positionOffset, Double rotation, Double scale, Double opacity, Toggle editorOnly) {
		super(EventType.POSITION_TRACK, active);
		this.positionOffset = positionOffset;
		this.rotation = rotation;
		this.scale = scale;
		this.opacity = opacity;
		this.editorOnly = editorOnly;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
		private Double rotation = 0.0;
		private Double scale = 100.0;
		private Double opacity = 100.0;
		private Toggle editorOnly = Toggle.DISABLED;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(PositionTrack src) {
			return self()
					.positionOffset(src.positionOffset)
					.rotation(src.rotation)
					.scale(src.scale)
					.opacity(src.opacity)
					.editorOnly(src.editorOnly);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public PositionTrack build() {
			return new PositionTrack(active, positionOffset, rotation, scale, opacity, editorOnly);
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
			return EventType.POSITION_TRACK;
		}

		/**
		 * setter of positionOffset
		 *
		 * @param positionOffset positionOffset of PositionTrack Event
		 * @return self
		 * @throws NullPointerException when positionOffset is null
		 * @throws IllegalArgumentException when size of positionOffset is not 2
		 */
		public Builder positionOffset(List<Double> positionOffset) {
			Objects.requireNonNull(positionOffset);
			if (positionOffset.size() != 2) {
				throw new IllegalArgumentException("size of positionOffset must be 2");
			}
			this.positionOffset = ListUtil.createNewUnmodifiableList(positionOffset);
			return self();
		}

		public Builder rotation(Double rotation) {
			Objects.requireNonNull(rotation);
			this.rotation = rotation;
			return this;
		}

		public Builder scale(Double scale) {
			Objects.requireNonNull(scale);
			this.scale = scale;
			return this;
		}

		public Builder opacity(Double opacity) {
			Objects.requireNonNull(opacity);
			this.opacity = opacity;
			return this;
		}

		/**
		 * setter of editorOnly
		 *
		 * @param editorOnly editorOnly of PositionTrack Event
		 * @return self
		 * @throws NullPointerException when editorOnly is null
		 */
		public Builder editorOnly(Toggle editorOnly) {
			Objects.requireNonNull(editorOnly);
			this.editorOnly = editorOnly;
			return self();
		}
	}


}
