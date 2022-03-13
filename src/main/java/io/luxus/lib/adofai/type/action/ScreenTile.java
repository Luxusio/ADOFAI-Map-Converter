package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TrackAnimation;
import io.luxus.lib.adofai.type.TrackDisappearAnimation;
import io.luxus.lib.adofai.util.ListUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class ScreenTile extends Action {

	private final List<Double> tile;
	private final Double angleOffset;
	private final String eventTag;

	private ScreenTile(Boolean active, List<Double> tile, Double angleOffset, String eventTag) {
		super(EventType.SCREEN_TILE, active);
		this.tile = tile;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private List<Double> tile = Arrays.asList(1.0, 1.0);
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(ScreenTile src) {
			return self()
					.tile(src.tile)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public ScreenTile build() {
			return new ScreenTile(active, tile, angleOffset, eventTag);
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
			return EventType.SCREEN_TILE;
		}

		/**
		 * setter of tile
		 *
		 * @param tile tile of ScreenTile Event
		 * @return self
		 * @throws NullPointerException when tile is null
		 * @throws IllegalArgumentException when size of tile is not 2
		 */
		public Builder tile(List<Double> tile) {
			Objects.requireNonNull(tile);
			if (tile.size() != 2) {
				throw new IllegalArgumentException("size of tile must be 2");
			}
			this.tile = ListUtil.createNewUnmodifiableList(tile);
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of ScreenTile Event
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
		 * @param eventTag eventTag of ScreenTile Event
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
