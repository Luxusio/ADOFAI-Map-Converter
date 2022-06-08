package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;


@Getter
@ToString
public class ScaleMargin extends Action {

	private final Long scale;

	private ScaleMargin(Boolean active, Long scale) {
		super(EventType.SCALE_MARGIN, active);
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
		public Builder from(ScaleMargin src) {
			return self()
					.scale(src.scale);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public ScaleMargin build() {
			return new ScaleMargin(active, scale);
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
			return EventType.SCALE_MARGIN;
		}

		public Builder scale(Long scale) {
			Objects.requireNonNull(scale);
			this.scale = scale;
			return self();
		}

	}

}
