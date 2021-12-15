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
	private final Toggle editorOnly;

	private PositionTrack(List<Double> positionOffset, Toggle editorOnly) {
		super(EventType.POSITION_TRACK);
		this.positionOffset = positionOffset;
		this.editorOnly = editorOnly;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
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
					.editorOnly(src.editorOnly);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public PositionTrack build() {
			return new PositionTrack(positionOffset, editorOnly);
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
