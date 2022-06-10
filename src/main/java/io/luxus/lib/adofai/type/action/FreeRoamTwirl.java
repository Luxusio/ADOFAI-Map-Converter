package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.Objects;


@Getter
@ToString
public class FreeRoamTwirl extends Action {

	private final Pair<Double, Double> position;

	private FreeRoamTwirl(Boolean active, Pair<Double, Double> position) {
		super(EventType.FREE_ROAM_TWIRL, active);
		this.position = position;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Pair<Double, Double> position = Pair.with(0.0, 0.0);

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(FreeRoamTwirl src) {
			return self()
					.position(src.position);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public FreeRoamTwirl build() {
			return new FreeRoamTwirl(active, position);
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
			return EventType.FREE_ROAM_TWIRL;
		}

		public Builder position(Pair<Double, Double> position) {
			Objects.requireNonNull(position);
			this.position = position;
			return self();
		}

	}

}
