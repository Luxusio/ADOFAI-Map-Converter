package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import io.luxus.lib.adofai.util.ListUtil;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@ToString
public class CustomBackground extends Action {
	
	private final String color;
	private final String bgImage;
	private final String imageColor;
	private final List<Double> parallax;
	private final BGDisplayModeType bgDisplayMode;
	private final Toggle lockRot;
	private final Toggle loopBG;
	private final Long unscaledSize;
	private final Double angleOffset;
	private final String eventTag;

	private CustomBackground(String color, String bgImage, String imageColor, List<Double> parallax, BGDisplayModeType bgDisplayMode, Toggle lockRot, Toggle loopBG, Long unscaledSize, Double angleOffset, String eventTag) {
		super(EventType.CUSTOM_BACKGROUND);
		this.color = color;
		this.bgImage = bgImage;
		this.imageColor = imageColor;
		this.parallax = parallax;
		this.bgDisplayMode = bgDisplayMode;
		this.lockRot = lockRot;
		this.loopBG = loopBG;
		this.unscaledSize = unscaledSize;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private String color = "000000";
		private String bgImage = "";
		private String imageColor = "ffffff";
		private List<Double> parallax = Arrays.asList(100.0, 100.0);
		private BGDisplayModeType bgDisplayMode = BGDisplayModeType.FIT_TO_SCREEN;
		private Toggle lockRot = Toggle.DISABLED;
		private Toggle loopBG = Toggle.DISABLED;
		private Long unscaledSize = 100L;
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(CustomBackground src) {
			return self()
					.color(src.color)
					.bgImage(src.bgImage)
					.imageColor(src.imageColor)
					.parallax(src.parallax)
					.bgDisplayMode(src.bgDisplayMode)
					.lockRot(src.lockRot)
					.loopBG(src.loopBG)
					.unscaledSize(src.unscaledSize)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public CustomBackground build() {
			return new CustomBackground(color, bgImage, imageColor, parallax, bgDisplayMode, lockRot, loopBG,
					unscaledSize, angleOffset, eventTag);
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
			return EventType.CUSTOM_BACKGROUND;
		}

		/**
		 * setter of color
		 *
		 * @param color color of CustomBackground Event
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
		 * setter of bgImage
		 *
		 * @param bgImage bgImage of CustomBackground Event
		 * @return self
		 * @throws NullPointerException when bgImage is null
		 */
		public Builder bgImage(String bgImage) {
			Objects.requireNonNull(bgImage);
			this.bgImage = bgImage;
			return self();
		}

		/**
		 * setter of imageColor
		 *
		 * @param imageColor imageColor of CustomBackground Event
		 * @return self
		 * @throws NullPointerException when imageColor is null
		 * @throws IllegalArgumentException when imageColor is not rgb code
		 */
		public Builder imageColor(String imageColor) {
			Objects.requireNonNull(imageColor);
			if (!StringJsonUtil.isRGBCode(imageColor)) {
				throw new IllegalArgumentException("imageColor is not rgb code");
			}
			this.imageColor = imageColor;
			return self();
		}

		/**
		 * setter of parallax
		 *
		 * @param parallax parallax of CustomBackground Event
		 * @return self
		 * @throws NullPointerException when parallax is null
		 * @throws IllegalArgumentException when size of parallax is not 2
		 */
		public Builder parallax(List<Double> parallax) {
			Objects.requireNonNull(parallax);
			if (parallax.size() != 2) {
				throw new IllegalArgumentException("size of parallax must be 2");
			}
			this.parallax = ListUtil.createNewUnmodifiableList(parallax);
			return self();
		}

		/**
		 * setter of bgDisplayMode
		 *
		 * @param bgDisplayMode bgDisplayMode of CustomBackground Event
		 * @return self
		 * @throws NullPointerException when bgDisplayMode is null
		 */
		public Builder bgDisplayMode(BGDisplayModeType bgDisplayMode) {
			Objects.requireNonNull(bgDisplayMode);
			this.bgDisplayMode = bgDisplayMode;
			return self();
		}

		/**
		 * setter of lockRot
		 *
		 * @param lockRot lockRot of CustomBackground Event
		 * @return self
		 * @throws NullPointerException when lockRot is null
		 */
		public Builder lockRot(Toggle lockRot) {
			Objects.requireNonNull(lockRot);
			this.lockRot = lockRot;
			return self();
		}

		/**
		 * setter of loopBG
		 *
		 * @param loopBG loopBG of CustomBackground Event
		 * @return self
		 * @throws NullPointerException when loopBG is null
		 */
		public Builder loopBG(Toggle loopBG) {
			Objects.requireNonNull(loopBG);
			this.loopBG = loopBG;
			return self();
		}

		/**
		 * setter of unscaledSize
		 *
		 * @param unscaledSize unscaledSize of CustomBackground Event
		 * @return self
		 * @throws NullPointerException when unscaledSize is null
		 */
		public Builder unscaledSize(Long unscaledSize) {
			Objects.requireNonNull(unscaledSize);
			this.unscaledSize = unscaledSize;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of CustomBackground Event
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
		 * @param eventTag eventTag of CustomBackground Event
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
