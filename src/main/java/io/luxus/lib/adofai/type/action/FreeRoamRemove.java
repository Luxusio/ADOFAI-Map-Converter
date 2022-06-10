package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.Objects;


@Getter
@ToString
public class FreeRoamRemove extends Action {

	private final Pair<Double, Double> size;
	private final Pair<Double, Double> position;

	private FreeRoamRemove(Boolean active, Pair<Double, Double> size, Pair<Double, Double> position) {
		super(EventType.FREE_ROAM_REMOVE, active);
		this.size = size;
		this.position = position;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Pair<Double, Double> size = Pair.with(0.0, 0.0);
		private Pair<Double, Double> position = Pair.with(0.0, 0.0);


		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(FreeRoamRemove src) {
			return self()
					.position(src.position);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public FreeRoamRemove build() {
			return new FreeRoamRemove(active, size, position);
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
			return EventType.FREE_ROAM_REMOVE;
		}

		public Builder size(Pair<Double, Double> size) {
			Objects.requireNonNull(size);
			this.size = size;
			return self();
		}

		public Builder position(Pair<Double, Double> position) {
			Objects.requireNonNull(position);
			this.position = position;
			return self();
		}

	}

}
