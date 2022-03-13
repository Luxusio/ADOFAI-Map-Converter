package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
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
	private final DecorationRelativeTo relativeTo;
	private final List<Double> pivotOffset;
	private final Double rotation;
	private final List<Double> scale;
	private final List<Long> tile;
	private final String color;
	private final Double opacity;
	private final Long depth;
	private final List<Double> parallax;
	private final String tag;
	private final Toggle imageSmoothing;
	private final String components;

	private AddDecoration(Boolean active, String decorationImage, List<Double> position, DecorationRelativeTo relativeTo,
						 List<Double> pivotOffset, Double rotation, List<Double> scale, List<Long> tile, String color,
						  Double opacity, Long depth, List<Double> parallax, String tag, Toggle imageSmoothing, String components) {
		super(EventType.ADD_DECORATION, active);
		this.decorationImage = decorationImage;
		this.position = position;
		this.relativeTo = relativeTo;
		this.pivotOffset = pivotOffset;
		this.rotation = rotation;
		this.scale = scale;
		this.tile = tile;
		this.color = color;
		this.opacity = opacity;
		this.depth = depth;
		this.parallax = parallax;
		this.tag = tag;
		this.imageSmoothing = imageSmoothing;
		this.components = components;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private String decorationImage = "";
		private List<Double> position = Arrays.asList(0.0, 0.0);
		private DecorationRelativeTo relativeTo = DecorationRelativeTo.TILE;
		private List<Double> pivotOffset = Arrays.asList(0.0, 0.0);
		private Double rotation = 0.0;
		private List<Double> scale = Arrays.asList(100.0, 100.0);
		private List<Long> tile = Arrays.asList(1L, 1L);
		private String color = "ffffff";
		private Double opacity = 100.0;
		private Long depth = 0L;
		private List<Double> parallax = Arrays.asList(0.0, 0.0);
		private String tag = "";
		private Toggle imageSmoothing = Toggle.ENABLED;
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
					.relativeTo(src.relativeTo)
					.pivotOffset(src.pivotOffset)
					.rotation(src.rotation)
					.scale(src.scale)
					.tile(src.tile)
					.color(src.color)
					.opacity(src.opacity)
					.depth(src.depth)
					.parallax(src.parallax)
					.tag(src.tag)
					.imageSmoothing(src.imageSmoothing)
					.components(src.components);
		}

		/**
		 * build AddDecoration action with builder
		 *
		 * @return Built AddDecoration action
		 */
		@Override
		public AddDecoration build() {
			return new AddDecoration(active, decorationImage, position, relativeTo, pivotOffset, rotation,
					scale, tile, color, opacity, depth, parallax, tag, imageSmoothing, components);
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
			return EventType.ADD_DECORATION;
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
		 * setter of relativeTo
		 *
		 * @throws NullPointerException when position is null
		 * @param relativeTo relativeTo of addDecoration event
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
		public Builder opacity(Double opacity) {
			Objects.requireNonNull(opacity);
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
		public Builder parallax(List<Double> parallax) {
			Objects.requireNonNull(parallax);
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
			Objects.requireNonNull(tag);
			this.tag = tag;
			return self();
		}

		/**
		 * setter of imageSmoothing
		 *
		 * @throws NullPointerException when imageSmoothing is null
		 * @param imageSmoothing imageSmoothing of addDecoration event
		 * @return self
		 */
		public Builder imageSmoothing(Toggle imageSmoothing) {
			Objects.requireNonNull(imageSmoothing);
			this.imageSmoothing = imageSmoothing;
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
			Objects.requireNonNull(components);
			this.components = components;
			return self();
		}
	}

}
