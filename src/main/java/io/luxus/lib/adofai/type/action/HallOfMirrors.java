package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class HallOfMirrors extends Action {

	private final Toggle enabled;
	private final Double angleOffset;
	private final String eventTag;

	private HallOfMirrors(Boolean active, Toggle enabled, Double angleOffset, String eventTag) {
		super(EventType.HALL_OF_MIRRORS, active);
		this.enabled = enabled;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Toggle enabled = Toggle.ENABLED;
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(HallOfMirrors src) {
			return self()
					.enabled(src.enabled)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public HallOfMirrors build() {
			return new HallOfMirrors(active, enabled, angleOffset, eventTag);
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
			return EventType.HALL_OF_MIRRORS;
		}


		/**
		 * setter of enabled
		 *
		 * @param enabled enabled of HallOfMirrors Event
		 * @return self
		 * @throws NullPointerException when enabled is null
		 */
		public Builder enabled(Toggle enabled) {
			Objects.requireNonNull(enabled);
			this.enabled = enabled;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of HallOfMirrors Event
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
		 * @param eventTag eventTag of HallOfMirrors Event
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
