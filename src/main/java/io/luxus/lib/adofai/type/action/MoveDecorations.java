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
	private final List<Double> positionOffset;
	private final Double rotationOffset;
	private final List<Long> scale;
	private final String color;
	private final Long opacity;
	private final Double angleOffset;
	private final Ease ease;
	private final String eventTag;

	private MoveDecorations(Double duration, String tag, List<Double> positionOffset, Double rotationOffset,
						   List<Long> scale, String color, Long opacity, Double angleOffset, Ease ease, String eventTag) {
		super(EventType.MOVE_DECORATIONS);
		this.duration = duration;
		this.tag = tag;
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
		this.color = color;
		this.opacity = opacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}


	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Double duration = 1.0;
		private String tag = "";
		private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
		private Double rotationOffset = 0.0;
		private List<Long> scale = Arrays.asList(100L, 100L);
		private String color = "ffffff";
		private Long opacity = 100L;
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
					.positionOffset(src.positionOffset)
					.rotationOffset(src.rotationOffset)
					.scale(src.scale)
					.color(src.color)
					.opacity(src.opacity)
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
			return new MoveDecorations(duration, tag, positionOffset, rotationOffset, scale, color, opacity, angleOffset, ease, eventTag);
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
		 * setter of positionOffset
		 *
		 * @param positionOffset positionOffset of MoveDecoration Event
		 * @return self
		 * @throws NullPointerException when positionOffset is null
		 * @throws IllegalArgumentException when size of positionOffset is not 2
		 */
		public Builder positionOffset(List<Double> positionOffset) {
			Objects.requireNonNull(positionOffset);
			if (positionOffset.size() != 2) {
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
		 * @throws NullPointerException when rotationOffset is null
		 */
		public Builder rotationOffset(Double rotationOffset) {
			Objects.requireNonNull(rotationOffset);
			this.rotationOffset = rotationOffset;
			return self();
		}


		/**
		 * setter of scale
		 *
		 * @param scale scale of MoveDecoration Event
		 * @return self
		 * @throws NullPointerException when scale is null
		 * @throws IllegalArgumentException when size of scale is not 2
		 */
		public Builder scale(List<Long> scale) {
			Objects.requireNonNull(scale);
			if (scale.size() != 2) {
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
		 * @throws NullPointerException when color is null
		 * @throws IllegalArgumentException when color with invalid format
		 */
		public Builder color(String color) {
			Objects.requireNonNull(color);
			if (!StringJsonUtil.isRGBCode(color)) {
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
		 * @throws NullPointerException when opacity is null
		 */
		public Builder opacity(Long opacity) {
			Objects.requireNonNull(eventTag);
			this.opacity = opacity;
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
			Objects.requireNonNull(eventTag);
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
			Objects.requireNonNull(eventTag);
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
