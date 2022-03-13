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
public class ShakeScreen extends Action {

	private final Double duration;
	private final Double strength;
	private final Double intensity;
	private final Toggle fadeOut;
	private final Double angleOffset;
	private final String eventTag;

	private ShakeScreen(Boolean active, Double duration, Double strength, Double intensity, Toggle fadeOut, Double angleOffset, String eventTag) {
		super(EventType.SHAKE_SCREEN, active);
		this.duration = duration;
		this.strength = strength;
		this.intensity = intensity;
		this.fadeOut = fadeOut;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Double duration = 1.0;
		private Double strength = 100.0;
		private Double intensity = 100.0;
		private Toggle fadeOut = Toggle.ENABLED;
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(ShakeScreen src) {
			return self()
					.duration(src.duration)
					.strength(src.strength)
					.intensity(src.intensity)
					.fadeOut(src.fadeOut)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public ShakeScreen build() {
			return new ShakeScreen(active, duration, strength, intensity, fadeOut, angleOffset, eventTag);
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
			return EventType.SHAKE_SCREEN;
		}

		/**
		 * setter of duration
		 *
		 * @param duration duration of ShakeScreen Event
		 * @return self
		 * @throws NullPointerException when duration is null
		 */
		public Builder duration(Double duration) {
			Objects.requireNonNull(duration);
			this.duration = duration;
			return this;
		}
		/**
		 * setter of strength
		 *
		 * @param strength strength of ShakeScreen Event
		 * @return self
		 * @throws NullPointerException when strength is null
		 */
		public Builder strength(Double strength) {
			Objects.requireNonNull(strength);
			this.strength = strength;
			return this;
		}
		/**
		 * setter of intensity
		 *
		 * @param intensity intensity of ShakeScreen Event
		 * @return self
		 * @throws NullPointerException when intensity is null
		 */
		public Builder intensity(Double intensity) {
			Objects.requireNonNull(intensity);
			this.intensity = intensity;
			return this;
		}
		/**
		 * setter of fadeOut
		 *
		 * @param fadeOut fadeOut of ShakeScreen Event
		 * @return self
		 * @throws NullPointerException when fadeOut is null
		 */
		public Builder fadeOut(Toggle fadeOut) {
			Objects.requireNonNull(fadeOut);
			this.fadeOut = fadeOut;
			return this;
		}
		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of ShakeScreen Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return this;
		}
		/**
		 * setter of eventTag
		 *
		 * @param eventTag eventTag of ShakeScreen Event
		 * @return self
		 * @throws NullPointerException when eventTag is null
		 */
		public Builder eventTag(String eventTag) {
			Objects.requireNonNull(eventTag);
			this.eventTag = eventTag;
			return this;
		}

	}

}
