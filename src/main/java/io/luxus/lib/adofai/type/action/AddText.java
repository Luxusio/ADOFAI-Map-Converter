package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Font;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.Objects;

@Getter
@ToString
public class AddText extends Action {

	private final String decText;
	private final Font font;
	private final Pair<Double, Double> position;
	private final DecorationRelativeTo relativeTo;
	private final Pair<Double, Double> pivotOffset;
	private final Double rotation;
	private final Pair<Double, Double> scale;
	private final String color;
	private final Double opacity;
	private final Long depth;
	private final Pair<Double, Double> parallax;
	private final String tag;

	private AddText(Boolean active, String decText, Font font, Pair<Double, Double> position, DecorationRelativeTo relativeTo, Pair<Double, Double> pivotOffset,
				   Double rotation, Pair<Double, Double> scale, String color, Double opacity, Long depth, Pair<Double, Double> parallax, String tag) {
		super(EventType.ADD_TEXT, active);
		this.decText = decText;
		this.font = font;
		this.position = position;
		this.relativeTo = relativeTo;
		this.pivotOffset = pivotOffset;
		this.rotation = rotation;
		this.scale = scale;
		this.color = color;
		this.opacity = opacity;
		this.depth = depth;
		this.parallax = parallax;
		this.tag = tag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private String decText = "text";
		private Font font = Font.DEFAULT;
		private Pair<Double, Double> position = Pair.with(0.0, 0.0);
		private DecorationRelativeTo relativeTo = DecorationRelativeTo.TILE;
		private Pair<Double, Double> pivotOffset = Pair.with(0.0, 0.0);
		private Double rotation = 0.0;
		private Pair<Double, Double> scale = Pair.with(100.0, 100.0);
		private String color = "ffffff";
		private Double opacity = 100.0;
		private Long depth = -1L;
		private Pair<Double, Double> parallax = Pair.with(-1.0, -1.0);
		private String tag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(AddText src) {
			return self()
					.decText(src.decText)
					.font(src.font)
					.position(src.position)
					.relativeTo(src.relativeTo)
					.pivotOffset(src.pivotOffset)
					.rotation(src.rotation)
					.scale(src.scale)
					.color(src.color)
					.opacity(src.opacity)
					.depth(src.depth)
					.parallax(src.parallax)
					.tag(src.tag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public AddText build() {
			return new AddText(active, decText, font, position, relativeTo, pivotOffset, rotation,
					scale, color, opacity, depth, parallax, tag);
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
			return EventType.ADD_TEXT;
		}

		/**
		 * setter of decText
		 *
		 * @throws NullPointerException when decText is null
		 * @param decText decText of AddText Event
		 * @return self
		 */
		public Builder decText(String decText) {
			Objects.requireNonNull(decText);
			this.decText = decText;
			return self();
		}

		/**
		 * setter of font
		 *
		 * @throws NullPointerException when font is null
		 * @param font font of AddText Event
		 * @return self
		 */
		public Builder font(Font font) {
			Objects.requireNonNull(font);
			this.font = font;
			return self();
		}

		/**
		 * setter of position
		 *
		 * @throws NullPointerException when position is null
		 * @throws IllegalArgumentException when size of position is not 2
		 * @param position position of AddText Event
		 * @return self
		 */
		public Builder position(Pair<Double, Double> position) {
			Objects.requireNonNull(position);
			this.position = position;
			return self();
		}

		/**
		 * setter of relativeTo
		 *
		 * @throws NullPointerException when relativeTo is null
		 * @param relativeTo relativeTo of AddText Event
		 * @return self
		 */
		public Builder relativeTo(DecorationRelativeTo relativeTo) {
			Objects.requireNonNull(relativeTo);
			this.relativeTo = relativeTo;
			return self();
		}

		/**
		 * setter of pivotOffset
		 *
		 * @throws NullPointerException when pivotOffset is null
		 * @throws IllegalArgumentException when size of pivotOffset is not 2
		 * @param pivotOffset pivotOffset of AddText Event
		 * @return self
		 */
		public Builder pivotOffset(Pair<Double, Double> pivotOffset) {
			Objects.requireNonNull(pivotOffset);
			this.pivotOffset = pivotOffset;
			return self();
		}

		/**
		 * setter of rotation
		 *
		 * @throws NullPointerException when rotation is null
		 * @param rotation rotation of AddText Event
		 * @return self
		 */
		public Builder rotation(Double rotation) {
			Objects.requireNonNull(decText);
			this.rotation = rotation;
			return self();
		}

		/**
		 * setter of scale
		 *
		 * @throws NullPointerException when scale is null
		 * @throws IllegalArgumentException when size of scale is not 2
		 * @param scale scale of AddText Event
		 * @return self
		 */
		public Builder scale(Pair<Double, Double> scale) {
			Objects.requireNonNull(scale);
			this.scale = scale;
			return self();
		}

		/**
		 * setter of color
		 *
		 * @throws NullPointerException when color is null
		 * @throws IllegalArgumentException when color is not RGB color code
		 * @param color color of AddText Event
		 * @return self
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
		 * @throws NullPointerException when opacity is null
		 * @param opacity opacity of AddText Event
		 * @return self
		 */
		public Builder opacity(Double opacity) {
			Objects.requireNonNull(opacity);
			this.opacity = opacity;
			return self();
		}

		/**
		 * setter of depth
		 *
		 * @throws NullPointerException when depth is null
		 * @param depth depth of AddText Event
		 * @return self
		 */
		public Builder depth(Long depth) {
			Objects.requireNonNull(depth);
			this.depth = depth;
			return self();
		}

		/**
		 * setter of parallax
		 *
		 * @throws NullPointerException when parallax is null
		 * @param parallax parallax of AddText Event
		 * @return self
		 */
		public Builder parallax(Pair<Double, Double> parallax) {
			Objects.requireNonNull(parallax);
			this.parallax = parallax;
			return self();
		}

		/**
		 * setter of tag
		 *
		 * @throws NullPointerException when tag is null
		 * @param tag tag of AddText Event
		 * @return self
		 */
		public Builder tag(String tag) {
			Objects.requireNonNull(tag);
			this.tag = tag;
			return self();
		}
	}

}
