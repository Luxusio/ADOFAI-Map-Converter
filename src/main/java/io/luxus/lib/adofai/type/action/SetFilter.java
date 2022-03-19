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
	private final Double duration;
	private final Ease ease;
	private final Toggle disableOthers;
	private final Double angleOffset;
	private final String eventTag;

	private SetFilter(Boolean active, Filter filter, Toggle enabled, Long intensity, Double duration, Ease ease, Toggle disableOthers, Double angleOffset, String eventTag) {
		super(EventType.SET_FILTER, active);
		this.filter = filter;
		this.enabled = enabled;
		this.intensity = intensity;
		this.duration = duration;
		this.ease = ease;
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
		private Double duration = 0.0;
		private Ease ease = Ease.LINEAR;
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
					.duration(src.duration)
					.ease(src.ease)
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
			return new SetFilter(active, filter, enabled, intensity, duration, ease, disableOthers, angleOffset, eventTag);
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
		 * setter of duration
		 *
		 * @param duration duration of SetFilter Event
		 * @return self
		 * @throws NullPointerException when duration is null
		 */
		public Builder duration(Double duration) {
			Objects.requireNonNull(duration);
			this.duration = duration;
			return self();
		}

		/**
		 * setter of intensity
		 *
		 * @param ease ease of SetFilter Event
		 * @return self
		 * @throws NullPointerException when ease is null
		 */
		public Builder ease(Ease ease) {
			Objects.requireNonNull(ease);
			this.ease = ease;
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
