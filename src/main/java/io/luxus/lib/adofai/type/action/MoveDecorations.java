package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.Ease;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.util.ListUtil;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class MoveDecorations extends Action {

	private final Double duration;
	private final String tag;
	private final String decorationImage;
	private final List<Double> positionOffset;
	private final Double rotationOffset;
	private final List<Double> scale;
	private final String color;
	private final Double opacity;
	private final Long depth;
	private final List<Double> parallax;
	private final Double angleOffset;
	private final Ease ease;
	private final String eventTag;

	private MoveDecorations(Boolean active, Double duration, String tag, String decorationImage, List<Double> positionOffset, Double rotationOffset,
						   List<Double> scale, String color, Double opacity, Long depth, List<Double> parallax, Double angleOffset, Ease ease, String eventTag) {
		super(EventType.MOVE_DECORATIONS, active);
		this.duration = duration;
		this.tag = tag;
		this.decorationImage = decorationImage;
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
		this.color = color;
		this.opacity = opacity;
		this.depth = depth;
		this.parallax = parallax;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}


	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Double duration = 1.0;
		private String tag = "";
		private String decorationImage = null;
		private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
		private Double rotationOffset = null;
		private List<Double> scale = null;
		private String color = null;
		private Double opacity = null;
		private Long depth = null;
		private List<Double> parallax = null;
		private Double angleOffset = 0.0;
		private Ease ease = Ease.LINEAR;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(MoveDecorations src) {
			return self()
					.duration(src.duration)
					.tag(src.tag)
					.decorationImage(src.decorationImage)
					.positionOffset(src.positionOffset)
					.rotationOffset(src.rotationOffset)
					.scale(src.scale)
					.color(src.color)
					.opacity(src.opacity)
					.depth(src.depth)
					.parallax(src.parallax)
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
		public MoveDecorations build() {
			return new MoveDecorations(active, duration, tag, decorationImage, positionOffset, rotationOffset, scale, color, opacity, depth, parallax, angleOffset, ease, eventTag);
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
			return EventType.MOVE_DECORATIONS;
		}

		/**
		 * setter of duration
		 *
		 * @param duration duration of MoveDecoration Event
		 * @return self
		 * @throws NullPointerException when duration is null
		 */
		public Builder duration(Double duration) {
			Objects.requireNonNull(duration);
			this.duration = duration;
			return self();
		}


		/**
		 * setter of tag
		 *
		 * @param tag tag of MoveDecoration Event
		 * @return self
		 * @throws NullPointerException when tag is null
		 */
		public Builder tag(String tag) {
			Objects.requireNonNull(tag);
			this.tag = tag;
			return self();
		}


		/**
		 * setter of decorationImage
		 *
		 * @param decorationImage decorationImage of MoveDecoration Event
		 * @return self
		 */
		public Builder decorationImage(String decorationImage) {
			this.decorationImage = decorationImage;
			return self();
		}


		/**
		 * setter of positionOffset
		 *
		 * @param positionOffset positionOffset of MoveDecoration Event
		 * @return self
		 * @throws IllegalArgumentException when size of positionOffset is not 2
		 */
		public Builder positionOffset(List<Double> positionOffset) {
			if (positionOffset != null && positionOffset.size() != 2) {
				throw new IllegalArgumentException("size of positionOffset must be 2");
			}
			this.positionOffset = ListUtil.createNewUnmodifiableList(positionOffset);
			return self();
		}


		/**
		 * setter of rotationOffset
		 *
		 * @param rotationOffset rotationOffset of MoveDecoration Event
		 * @return self
		 */
		public Builder rotationOffset(Double rotationOffset) {
			this.rotationOffset = rotationOffset;
			return self();
		}


		/**
		 * setter of scale
		 *
		 * @param scale scale of MoveDecoration Event
		 * @return self
		 * @throws IllegalArgumentException when size of scale is not 2
		 */
		public Builder scale(List<Double> scale) {
			if (scale != null && scale.size() != 2) {
				throw new IllegalArgumentException("size of scale must be 2");
			}
			this.scale = ListUtil.createNewUnmodifiableList(scale);
			return self();
		}


		/**
		 * setter of color
		 *
		 * @param color color of MoveDecoration Event
		 * @return self
		 * @throws IllegalArgumentException when color with invalid format
		 */
		public Builder color(String color) {
			if (color != null && !StringJsonUtil.isRGBCode(color)) {
				throw new IllegalArgumentException("color is not RGB color code!");
			}
			this.color = color;
			return self();
		}


		/**
		 * setter of opacity
		 *
		 * @param opacity opacity of MoveDecoration Event
		 * @return self
		 */
		public Builder opacity(Double opacity) {
			this.opacity = opacity;
			return self();
		}


		/**
		 * setter of depth
		 *
		 * @param depth depth of MoveDecoration Event
		 * @return self
		 * @throws NullPointerException when depth is null
		 */
		public Builder depth(Long depth) {
			this.depth = depth;
			return self();
		}


		/**
		 * setter of parallax
		 *
		 * @param parallax parallax of MoveDecoration Event
		 * @return self
		 */
		public Builder parallax(List<Double> parallax) {
			if (parallax != null && parallax.size() != 2) {
				throw new IllegalArgumentException("size of parallax must be 2");
			}
			this.parallax = parallax;
			return self();
		}


		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of MoveDecoration Event
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
		 * @param ease ease of MoveDecoration Event
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
		 * @param eventTag eventTag of MoveDecoration Event
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
