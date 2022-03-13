package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Twirl extends Action {
	
	private Twirl(Boolean active) {
		super(EventType.TWIRL, active);
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(Twirl src) {
			return self();
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public Twirl build() {
			return new Twirl(active);
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
			return EventType.TWIRL;
		}

	}

}
