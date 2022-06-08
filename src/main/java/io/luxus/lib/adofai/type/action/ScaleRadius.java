package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;


@Getter
@ToString
public class ScaleRadius extends Action {

	private final Long scale;

	private ScaleRadius(Boolean active, Long scale) {
		super(EventType.SCALE_RADIUS, active);
		this.scale = scale;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Long scale = 100L;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(ScaleRadius src) {
			return self()
					.scale(src.scale);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public ScaleRadius build() {
			return new ScaleRadius(active, scale);
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
			return EventType.SCALE_RADIUS;
		}

		public Builder scale(Long scale) {
			Objects.requireNonNull(scale);
			this.scale = scale;
			return self();
		}

	}

}
