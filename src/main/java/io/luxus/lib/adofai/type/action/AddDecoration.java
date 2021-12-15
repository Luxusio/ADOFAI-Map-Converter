package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.util.ListUtil;
import io.luxus.lib.adofai.util.StringJsonUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public final class AddDecoration extends Action {
	
	private final String decorationImage;
	private final List<Double> position;
	private final DecorationRelativeTo decorationRelativeTo;
	private final List<Double> pivotOffset;
	private final Double rotation;
	private final List<Double> scale;
	private final List<Long> tile;
	private final String color;
	private final Long opacity;
	private final Long depth;
	private final Long parallax;
	private final String tag;
	private final String components;

	private AddDecoration(String decorationImage, List<Double> position, DecorationRelativeTo decorationRelativeTo,
						 List<Double> pivotOffset, Double rotation, List<Double> scale, List<Long> tile, String color,
						 Long opacity, Long depth, Long parallax, String tag, String components) {
		super(EventType.ADD_DECORATION);
		this.decorationImage = decorationImage;
		this.position = position;
		this.decorationRelativeTo = decorationRelativeTo;
		this.pivotOffset = pivotOffset;
		this.rotation = rotation;
		this.scale = scale;
		this.tile = tile;
		this.color = color;
		this.opacity = opacity;
		this.depth = depth;
		this.parallax = parallax;
		this.tag = tag;
		this.components = components;
	}

	@Getter
	public static final class Builder extends Action.Builder<Builder> {

		private String decorationImage = "";
		private List<Double> position = Arrays.asList(0.0, 0.0);
		private DecorationRelativeTo decorationRelativeTo = DecorationRelativeTo.TILE;
		private List<Double> pivotOffset = Arrays.asList(0.0, 0.0);
		private Double rotation = 0.0;
		private List<Double> scale = Arrays.asList(100.0, 100.0);
		private List<Long> tile = Arrays.asList(1L, 1L);
		private String color = "ffffff";
		private Long opacity = 100L;
		private Long depth = 0L;
		private Long parallax = 0L;
		private String tag = "";
		private String components = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(AddDecoration src) {
			return self()
					.decorationImage(src.decorationImage)
					.position(src.position)
					.decorationRelativeTo(src.decorationRelativeTo)
					.pivotOffset(src.pivotOffset)
					.rotation(src.rotation)
					.scale(src.scale)
					.tile(src.tile)
					.color(src.color)
					.opacity(src.opacity)
					.depth(src.depth)
					.parallax(src.parallax)
					.tag(src.tag)
					.components(src.components);
		}

		/**
		 * build AddDecoration action with builder
		 *
		 * @return Built AddDecoration action
		 */
		@Override
		public AddDecoration build() {
			return new AddDecoration(decorationImage, position, decorationRelativeTo, pivotOffset, rotation,
					scale, tile, color, opacity, depth, parallax, tag, components);
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
		 * setter of decorationImage
		 *
		 * @throws NullPointerException when position is null
		 * @param decorationImage decoration image path of AddDecoration Event
		 * @return self
		 */
		public Builder decorationImage(String decorationImage) {
			Objects.requireNonNull(decorationImage);
			this.decorationImage = decorationImage;
			return self();
		}

		/**
		 * setter of position
		 *
		 * @throws NullPointerException when position is null
		 * @throws IllegalArgumentException when size of position is not 2
		 * @param position position of AddDecoration Event
		 * @return self
		 */
		public Builder position(List<Double> position) {
			Objects.requireNonNull(position);
			if (position.size() != 2) {
				throw new IllegalArgumentException("size of position must be 2");
			}
			this.position = ListUtil.createNewUnmodifiableList(position);
			return self();
		}

		/**
		 * setter of decorationRelativeTo
		 *
		 * @throws NullPointerException when position is null
		 * @param decorationRelativeTo decorationRelativeTo of addDecoration event
		 * @return self
		 */
		public Builder decorationRelativeTo(DecorationRelativeTo decorationRelativeTo) {
			Objects.requireNonNull(decorationRelativeTo);
			this.decorationRelativeTo = decorationRelativeTo;
			return self();
		}

		/**
		 * setter of pivotOffset
		 *
		 * @throws NullPointerException when pivotOffset is null
		 * @throws IllegalArgumentException when size of pivotOffset is not 2
		 * @param pivotOffset pivotOffset of addDecoration event
		 * @return self
		 */
		public Builder pivotOffset(List<Double> pivotOffset) {
			Objects.requireNonNull(pivotOffset);
			if (pivotOffset.size() != 2) {
				throw new IllegalArgumentException("size of pivotOffset must be 2");
			}
			this.pivotOffset = ListUtil.createNewUnmodifiableList(pivotOffset);
			return self();
		}

		/**
		 * setter of rotation
		 *
		 * @throws NullPointerException when rotation is null
		 * @param rotation rotation of addDecoration event
		 * @return self
		 */
		public Builder rotation(Double rotation) {
			Objects.requireNonNull(rotation);
			this.rotation = rotation;
			return self();
		}

		/**
		 * setter of scale
		 *
		 * @throws NullPointerException when scale is null
		 * @throws IllegalArgumentException when size of scale is not 2
		 * @param scale scale of addDecoration event
		 * @return self
		 */
		public Builder scale(List<Double> scale) {
			Objects.requireNonNull(scale);
			if (scale.size() != 2) {
				throw new IllegalArgumentException("size of scale must be 2");
			}
			this.scale = ListUtil.createNewUnmodifiableList(scale);
			return self();
		}

		/**
		 * setter of tile
		 *
		 * @throws NullPointerException when tile is null
		 * @throws IllegalArgumentException when size of tile is not 2
		 * @param tile tile of addDecoration event
		 * @return self
		 */
		public Builder tile(List<Long> tile) {
			Objects.requireNonNull(tile);
			if (tile.size() != 2) {
				throw new IllegalArgumentException("size of tile must be 2");
			}
			this.tile = ListUtil.createNewUnmodifiableList(tile);
			return self();
		}

		/**
		 * setter of color
		 *
		 * @throws NullPointerException when color is null
		 * @throws IllegalArgumentException when color with invalid format
		 * @param color color of addDecoration event
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
		 * @param opacity opacity of addDecoration event
		 * @return self
		 */
		public Builder opacity(Long opacity) {
			Objects.requireNonNull(depth);
			this.opacity = opacity;
			return self();
		}

		/**
		 * setter of depth
		 *
		 * @throws NullPointerException when depth is null
		 * @param depth depth of addDecoration event
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
		 * @param parallax parallax of addDecoration event
		 * @return self
		 */
		public Builder parallax(Long parallax) {
			Objects.requireNonNull(depth);
			this.parallax = parallax;
			return self();
		}

		/**
		 * setter of tag
		 *
		 * @throws NullPointerException when tag is null
		 * @param tag tag of addDecoration event
		 * @return self
		 */
		public Builder tag(String tag) {
			Objects.requireNonNull(depth);
			this.tag = tag;
			return self();
		}

		/**
		 * setter of components
		 *
		 * @throws NullPointerException when {components} is null
		 * @param components components of addDecoration event
		 * @return self
		 */
		public Builder components(String components) {
			Objects.requireNonNull(depth);
			this.components = components;
			return self();
		}
	}

}
