package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;


@Getter
@ToString
public class MultiPlanet extends Action {

	private final Long planets;

	private MultiPlanet(Boolean active, Long planets) {
		super(EventType.MULTI_PLANET, active);
		this.planets = planets;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Long planets = 2L;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(MultiPlanet src) {
			return self()
					.planets(src.planets);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public MultiPlanet build() {
			return new MultiPlanet(active, planets);
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
			return EventType.MULTI_PLANET;
		}

		public Builder planets(Long planets) {
			Objects.requireNonNull(planets);
			if (planets < 2) {
				throw new IllegalArgumentException("planets should be equal or bigger than 2");
			}
			this.planets = planets;
			return self();
		}

	}

}
