package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.Ease;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class Bloom extends Action {

	private final Toggle enabled;
	private final Double threshold;
	private final Double intensity;
	private final String color;
	private final Double duration;
	private final Ease ease;
	private final Double angleOffset;
	private final String eventTag;

	private Bloom(Boolean active, Toggle enabled, Double threshold, Double intensity, String color, Double duration, Ease ease, Double angleOffset, String eventTag) {
		super(EventType.BLOOM, active);
		this.enabled = enabled;
		this.threshold = threshold;
		this.intensity = intensity;
		this.color = color;
		this.duration = duration;
		this.ease = ease;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Toggle enabled = Toggle.ENABLED;
		private Double threshold = 50.0;
		private Double intensity = 100.0;
		private String color = "ffffff";
		private Double duration = 0.0;
		private Ease ease = Ease.LINEAR;
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(Bloom src) {
			return self()
					.enabled(src.enabled)
					.threshold(src.threshold)
					.intensity(src.intensity)
					.color(src.color)
					.duration(src.duration)
					.ease(src.ease)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public Bloom build() {
			return new Bloom(active, enabled, threshold, intensity, color, duration, ease, angleOffset, eventTag);
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
			return EventType.BLOOM;
		}

		/**
		 * setter of enabled
		 *
		 * @param enabled enabled of Bloom Event
		 * @return self
		 * @throws NullPointerException when enabled is null
		 */
		public Builder enabled(Toggle enabled) {
			Objects.requireNonNull(enabled);
			this.enabled = enabled;
			return self();
		}

		/**
		 * setter of threshold
		 *
		 * @param threshold threshold of Bloom Event
		 * @return self
		 * @throws NullPointerException when threshold is null
		 */
		public Builder threshold(Double threshold) {
			Objects.requireNonNull(threshold);
			this.threshold = threshold;
			return self();
		}

		/**
		 * setter of intensity
		 *
		 * @param intensity intensity of Bloom Event
		 * @return self
		 * @throws NullPointerException when intensity is null
		 */
		public Builder intensity(Double intensity) {
			Objects.requireNonNull(intensity);
			this.intensity = intensity;
			return self();
		}

		/**
		 * setter of color
		 *
		 * @param color color of Bloom Event
		 * @return self
		 * @throws NullPointerException when color is null
		 * @throws IllegalArgumentException when color is not rgb code
		 */
		public Builder color(String color) {
			Objects.requireNonNull(color);
			if (!StringJsonUtil.isRGBCode(color)) {
				throw new IllegalArgumentException("color is not rgb code");
			}
			this.color = color;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of Bloom Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return self();
		}

		/**
		 * setter of duration
		 *
		 * @param duration duration of Bloom Event
		 * @return self
		 * @throws NullPointerException when duration is null
		 */
		public Builder duration(Double duration) {
			Objects.requireNonNull(duration);
			this.duration = duration;
			return self();
		}

		/**
		 * setter of ease
		 *
		 * @param ease ease of Bloom Event
		 * @return self
		 * @throws NullPointerException when ease is null
		 */
		public Builder ease(Ease ease) {
			Objects.requireNonNull(ease);
			this.ease = ease;
			return self();
		}

		/**
		 * setter of eventTag
		 *
		 * @param eventTag eventTag of Bloom Event
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
