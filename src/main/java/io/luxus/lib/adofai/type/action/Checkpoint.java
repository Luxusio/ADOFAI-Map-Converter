package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;


@Getter
@ToString
public class Checkpoint extends Action {

	private final Long tileOffset;

	private Checkpoint(Boolean active, Long tileOffset) {
		super(EventType.CHECK_POINT, active);
		this.tileOffset = tileOffset;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Long tileOffset = 0L;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(Checkpoint src) {
			return self()
					.tileOffset(src.tileOffset);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public Checkpoint build() {
			return new Checkpoint(active, tileOffset);
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
			return EventType.CHECK_POINT;
		}

		public Builder tileOffset(Long tileOffset) {
			Objects.requireNonNull(tileOffset);
			this.tileOffset = tileOffset;
			return self();
		}

	}

}
