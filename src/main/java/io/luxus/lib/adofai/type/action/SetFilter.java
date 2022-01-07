package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class SetFilter extends Action {
	
	private final Filter filter;
	private final Toggle enabled;
	private final Long intensity;
	private final Toggle disableOthers;
	private final Double angleOffset;
	private final String eventTag;

	private SetFilter(Filter filter, Toggle enabled, Long intensity, Toggle disableOthers, Double angleOffset, String eventTag) {
		super(EventType.SET_FILTER);
		this.filter = filter;
		this.enabled = enabled;
		this.intensity = intensity;
		this.disableOthers = disableOthers;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Filter filter = Filter.GRAYSCALE;
		private Toggle enabled = Toggle.ENABLED;
		private Long intensity = 100L;
		private Toggle disableOthers = Toggle.DISABLED;
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(SetFilter src) {
			return self()
					.filter(src.filter)
					.enabled(src.enabled)
					.intensity(src.intensity)
					.disableOthers(src.disableOthers)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public SetFilter build() {
			return new SetFilter(filter, enabled, intensity, disableOthers, angleOffset, eventTag);
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
			return EventType.SET_FILTER;
		}

		/**
		 * setter of filter
		 *
		 * @param filter filter of SetFilter Event
		 * @return self
		 * @throws NullPointerException when filter is null
		 */
		public Builder filter(Filter filter) {
			Objects.requireNonNull(filter);
			this.filter = filter;
			return self();
		}

		/**
		 * setter of enabled
		 *
		 * @param enabled enabled of SetFilter Event
		 * @return self
		 * @throws NullPointerException when enabled is null
		 */
		public Builder enabled(Toggle enabled) {
			Objects.requireNonNull(enabled);
			this.enabled = enabled;
			return self();
		}

		/**
		 * setter of intensity
		 *
		 * @param intensity intensity of SetFilter Event
		 * @return self
		 * @throws NullPointerException when intensity is null
		 */
		public Builder intensity(Long intensity) {
			Objects.requireNonNull(intensity);
			this.intensity = intensity;
			return self();
		}

		/**
		 * setter of disableOthers
		 *
		 * @param disableOthers disableOthers of SetFilter Event
		 * @return self
		 * @throws NullPointerException when disableOthers is null
		 */
		public Builder disableOthers(Toggle disableOthers) {
			Objects.requireNonNull(disableOthers);
			this.disableOthers = disableOthers;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of SetFilter Event
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
		 * @param eventTag eventTag of SetFilter Event
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
