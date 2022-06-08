package io.luxus.lib.adofai.decoration;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.Font;
import io.luxus.lib.adofai.util.ListUtil;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class AddText extends Decoration {

	private final String decText;
	private final Font font;
	private final List<Double> position;
	private final DecorationRelativeTo relativeTo;
	private final List<Double> pivotOffset;
	private final Double rotation;
	private final List<Double> scale;
	private final String color;
	private final Double opacity;
	private final Long depth;
	private final List<Double> parallax;
	private final String tag;

	private AddText(Boolean active, Long floor, String decText, Font font, List<Double> position, DecorationRelativeTo relativeTo, List<Double> pivotOffset,
                    Double rotation, List<Double> scale, String color, Double opacity, Long depth, List<Double> parallax, String tag) {
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
		private List<Double> position = Arrays.asList(0.0, 0.0);
		private DecorationRelativeTo relativeTo = DecorationRelativeTo.GLOBAL;
		private List<Double> pivotOffset = Arrays.asList(0.0, 0.0);
		private Double rotation = 0.0;
		private List<Double> scale = Arrays.asList(100.0, 100.0);
		private String color = "ffffff";
		private Double opacity = 100.0;
		private Long depth = -1L;
		private List<Double> parallax = Arrays.asList(-1.0, -1.0);
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

		public Builder position(List<Double> position) {
			Objects.requireNonNull(position);
			if (position.size() != 2) {
				throw new IllegalArgumentException("size of position must be 2");
			}
			this.position = ListUtil.createNewUnmodifiableList(position);
			return self();
		}

		public Builder relativeTo(DecorationRelativeTo relativeTo) {
			Objects.requireNonNull(relativeTo);
			this.relativeTo = relativeTo;
			return self();
		}

		public Builder pivotOffset(List<Double> pivotOffset) {
			Objects.requireNonNull(pivotOffset);
			if (pivotOffset.size() != 2) {
				throw new IllegalArgumentException("size of pivotOffset must be 2");
			}
			this.pivotOffset = ListUtil.createNewUnmodifiableList(pivotOffset);
			return self();
		}

		public Builder rotation(Double rotation) {
			Objects.requireNonNull(decText);
			this.rotation = rotation;
			return self();
		}

		public Builder scale(List<Double> scale) {
			Objects.requireNonNull(scale);
			if (scale.size() != 2) {
				throw new IllegalArgumentException("size of scale must be 2");
			}
			this.scale = ListUtil.createNewUnmodifiableList(scale);
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

		public Builder parallax(List<Double> parallax) {
			Objects.requireNonNull(parallax);
			if (parallax.size() != 2) {
				throw new IllegalArgumentException("size of scale must be 2");
			}
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
