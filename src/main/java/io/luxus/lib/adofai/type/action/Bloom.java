package io.luxus.lib.adofai.type.action;

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
	private final Long threshold;
	private final Long intensity;
	private final String color;
	private final Double angleOffset;
	private final String eventTag;

	private Bloom(Toggle enabled, Long threshold, Long intensity, String color, Double angleOffset, String eventTag) {
		super(EventType.BLOOM);
		this.enabled = enabled;
		this.threshold = threshold;
		this.intensity = intensity;
		this.color = color;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Toggle enabled = Toggle.ENABLED;
		private Long threshold = 50L;
		private Long intensity = 100L;
		private String color = "ffffff";
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
			return new Bloom(enabled, threshold, intensity, color, angleOffset, eventTag);
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
		public Builder threshold(Long threshold) {
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
		public Builder intensity(Long intensity) {
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
			if (StringJsonUtil.isRGBCode(color)) {
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
