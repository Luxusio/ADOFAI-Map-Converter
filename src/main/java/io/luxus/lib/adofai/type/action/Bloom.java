package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import io.luxus.lib.adofai.type.TrackAnimation;
import io.luxus.lib.adofai.type.TrackDisappearAnimation;
import lombok.Getter;
import lombok.Setter;
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
					.trackAnimation(src.trackAnimation)
					.beatsAhead(src.beatsAhead)
					.trackDisappearAnimation(src.trackDisappearAnimation)
					.beatsBehind(src.beatsBehind);
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
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of Bloom Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public AnimateTrack.Builder trackAnimation(TrackAnimation trackAnimation) {
			Objects.requireNonNull(trackAnimation);
			this.trackAnimation = trackAnimation;
			return self();
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of Bloom Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public Builder setEnabled(Toggle enabled) {
			this.enabled = enabled;
			return this;
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of Bloom Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public Builder setThreshold(Long threshold) {
			this.threshold = threshold;
			return this;
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of Bloom Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public Builder setIntensity(Long intensity) {
			this.intensity = intensity;
			return this;
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of Bloom Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public Builder setColor(String color) {
			this.color = color;
			return this;
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of Bloom Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public Builder setAngleOffset(Double angleOffset) {
			this.angleOffset = angleOffset;
			return this;
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param trackAnimation trackAnimation of Bloom Event
		 * @return self
		 * @throws NullPointerException when trackAnimation is null
		 */
		public Builder setEventTag(String eventTag) {
			this.eventTag = eventTag;
			return this;
		}

	}
	
}
