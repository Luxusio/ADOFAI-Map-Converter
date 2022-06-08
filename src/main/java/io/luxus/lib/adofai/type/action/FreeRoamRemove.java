package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.luxus.lib.adofai.util.ListUtil.toUnmodifiableXYList;


@Getter
@ToString
public class FreeRoamRemove extends Action {

	private final List<Double> size;
	private final List<Double> position;

	private FreeRoamRemove(Boolean active, List<Double> size, List<Double> position) {
		super(EventType.FREE_ROAM_REMOVE, active);
		this.size = size;
		this.position = position;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private List<Double> size = Arrays.asList(0.0, 0.0);
		private List<Double> position = Arrays.asList(0.0, 0.0);


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

		public Builder size(List<Double> size) {
			Objects.requireNonNull(size);
			if (size.size() != 2) {
				throw new IllegalArgumentException("size of size should be 2");
			}
			this.size = toUnmodifiableXYList(size);
			return self();
		}

		public Builder position(List<Double> position) {
			Objects.requireNonNull(position);
			if (position.size() != 2) {
				throw new IllegalArgumentException("size of position should be 2");
			}
			this.position = toUnmodifiableXYList(position);
			return self();
		}


	}

}
