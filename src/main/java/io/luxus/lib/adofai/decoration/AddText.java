package io.luxus.lib.adofai.decoration;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.Font;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.Objects;

@Getter
@ToString
public class AddText extends Decoration {

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

	private AddText(Boolean active, Long floor, String decText, Font font, Pair<Double, Double> position, DecorationRelativeTo relativeTo, Pair<Double, Double> pivotOffset,
                    Double rotation, Pair<Double, Double> scale, String color, Double opacity, Long depth, Pair<Double, Double> parallax, String tag) {
		super(DecorationType.ADD_TEXT, active, floor);
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
	public static final class Builder extends Decoration.Builder<Builder> {

		private String decText = "text";
		private Font font = Font.DEFAULT;
		private Pair<Double, Double> position = Pair.with(0.0, 0.0);
		private DecorationRelativeTo relativeTo = DecorationRelativeTo.GLOBAL;
		private Pair<Double, Double> pivotOffset = Pair.with(0.0, 0.0);
		private Double rotation = 0.0;
		private Pair<Double, Double> scale = Pair.with(100.0, 100.0);
		private String color = "ffffff";
		private Double opacity = 100.0;
		private Long depth = -1L;
		private Pair<Double, Double> parallax = Pair.with(-1.0, -1.0);
		private String tag = "";

		public Builder from(AddText src) {
			return self()
					.floor(src.getFloor())
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

		@Override
		public AddText build() {
			return new AddText(visible, floor, decText, font, position, relativeTo,
					pivotOffset, rotation, scale, color, opacity, depth, parallax, tag);
		}

		@Override
		public Builder self() {
			return this;
		}

		@Override
		public DecorationType getEventType() {
			return DecorationType.ADD_TEXT;
		}

		public Builder decText(String decText) {
			Objects.requireNonNull(decText);
			this.decText = decText;
			return self();
		}

		public Builder font(Font font) {
			Objects.requireNonNull(font);
			this.font = font;
			return self();
		}

		public Builder position(Pair<Double, Double> position) {
			Objects.requireNonNull(position);
			this.position = position;
			return self();
		}

		public Builder relativeTo(DecorationRelativeTo relativeTo) {
			Objects.requireNonNull(relativeTo);
			this.relativeTo = relativeTo;
			return self();
		}

		public Builder pivotOffset(Pair<Double, Double> pivotOffset) {
			Objects.requireNonNull(pivotOffset);
			this.pivotOffset = pivotOffset;
			return self();
		}

		public Builder rotation(Double rotation) {
			Objects.requireNonNull(decText);
			this.rotation = rotation;
			return self();
		}

		public Builder scale(Pair<Double, Double> scale) {
			Objects.requireNonNull(scale);
			this.scale = scale;
			return self();
		}

		public Builder color(String color) {
			Objects.requireNonNull(color);
			if (!StringJsonUtil.isRGBCode(color)) {
				throw new IllegalArgumentException("color is not RGB color code!");
			}
			this.color = color;
			return self();
		}

		public Builder opacity(Double opacity) {
			Objects.requireNonNull(opacity);
			this.opacity = opacity;
			return self();
		}

		public Builder depth(Long depth) {
			Objects.requireNonNull(depth);
			this.depth = depth;
			return self();
		}

		public Builder parallax(Pair<Double, Double> parallax) {
			Objects.requireNonNull(parallax);
			this.parallax = parallax;
			return self();
		}

		public Builder tag(String tag) {
			Objects.requireNonNull(tag);
			this.tag = tag;
			return self();
		}

	}

}
