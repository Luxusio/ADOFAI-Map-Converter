package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class Flash extends Action {
	
	private final Double duration;
	private final Plane plane;
	private final String startColor;
	private final Long startOpacity;
	private final String endColor;
	private final Long endOpacity;
	private final Double angleOffset;
	private final Ease ease;
	private final String eventTag;

	private Flash(Double duration, Plane plane, String startColor, Long startOpacity, String endColor, Long endOpacity, Double angleOffset, Ease ease, String eventTag) {
		super(EventType.FLASH);
		this.duration = duration;
		this.plane = plane;
		this.startColor = startColor;
		this.startOpacity = startOpacity;
		this.endColor = endColor;
		this.endOpacity = endOpacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Double duration = 1.0;
		private Plane plane = Plane.BACKGROUND;
		private String startColor = "ffffff";
		private Long startOpacity = 100L;
		private String endColor = "ffffff";
		private Long endOpacity = 0L;
		private Double angleOffset = 0.0;
		private Ease ease = Ease.LINEAR;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(Flash src) {
			return self()
					.duration(src.duration)
					.plane(src.plane)
					.startColor(src.startColor)
					.startOpacity(src.startOpacity)
					.endColor(src.endColor)
					.endOpacity(src.endOpacity)
					.angleOffset(src.angleOffset)
					.ease(src.ease)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public Flash build() {
			return new Flash(duration, plane, startColor, startOpacity, endColor, endOpacity, angleOffset, ease, eventTag);
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
		 * setter of duration
		 *
		 * @param duration duration of Flash Event
		 * @return self
		 * @throws NullPointerException when duration is null
		 */
		public Builder duration(Double duration) {
			Objects.requireNonNull(duration);
			this.duration = duration;
			return self();
		}

		/**
		 * setter of plane
		 *
		 * @param plane plane of Flash Event
		 * @return self
		 * @throws NullPointerException when plane is null
		 */
		public Builder plane(Plane plane) {
			Objects.requireNonNull(plane);
			this.plane = plane;
			return self();
		}

		/**
		 * setter of startColor
		 *
		 * @param startColor startColor of Flash Event
		 * @return self
		 * @throws NullPointerException when startColor is null
		 */
		public Builder startColor(String startColor) {
			Objects.requireNonNull(startColor);
			this.startColor = startColor;
			return self();
		}

		/**
		 * setter of startOpacity
		 *
		 * @param startOpacity startOpacity of Flash Event
		 * @return self
		 * @throws NullPointerException when startOpacity is null
		 */
		public Builder startOpacity(Long startOpacity) {
			Objects.requireNonNull(startOpacity);
			this.startOpacity = startOpacity;
			return self();
		}

		/**
		 * setter of endColor
		 *
		 * @param endColor endColor of Flash Event
		 * @return self
		 * @throws NullPointerException when endColor is null
		 */
		public Builder endColor(String endColor) {
			Objects.requireNonNull(endColor);
			this.endColor = endColor;
			return self();
		}

		/**
		 * setter of endOpacity
		 *
		 * @param endOpacity endOpacity of Flash Event
		 * @return self
		 * @throws NullPointerException when endOpacity is null
		 */
		public Builder endOpacity(Long endOpacity) {
			Objects.requireNonNull(endOpacity);
			this.endOpacity = endOpacity;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of Flash Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return self();
		}

		/**
		 * setter of ease
		 *
		 * @param ease ease of Flash Event
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
		 * @param eventTag eventTag of Flash Event
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
