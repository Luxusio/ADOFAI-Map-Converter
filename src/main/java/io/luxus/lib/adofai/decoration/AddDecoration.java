package io.luxus.lib.adofai.decoration;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class AddDecoration extends Decoration {

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
    private final Toggle failHitbox;
    private final String components;

    private AddDecoration(Boolean visible, Long floor, String decorationImage, List<Double> position, DecorationRelativeTo relativeTo, List<Double> pivotOffset, Double rotation, List<Double> scale, List<Long> tile, String color, Double opacity, Long depth, List<Double> parallax, String tag, Toggle imageSmoothing, Toggle failHitbox, String components) {
        super(DecorationType.ADD_DECORATION, visible, floor);
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
        this.failHitbox = failHitbox;
        this.components = components;
    }


    @Getter
    @ToString
    public static final class Builder extends Decoration.Builder<Builder> {

        private String decorationImage = "";
        private List<Double> position = Arrays.asList(0.0, 0.0);
        private DecorationRelativeTo relativeTo = DecorationRelativeTo.GLOBAL;
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
        private Toggle failHitbox = Toggle.DISABLED;
        private String components = "";

        public Builder from(AddDecoration src) {
            return self()
                    .floor(src.getFloor())
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
                    .failHitbox(src.failHitbox)
                    .components(src.components)
            ;
        }


        @Override
        public AddDecoration build() {
            return new AddDecoration(visible, floor, decorationImage, position, relativeTo, pivotOffset, rotation, scale, tile, color, opacity,
                    depth, parallax, tag, imageSmoothing, failHitbox, components);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public DecorationType getEventType() {
            return DecorationType.ADD_DECORATION;
        }

        public Builder decorationImage(String decorationImage) {
            Objects.requireNonNull(decorationImage);
            this.decorationImage = decorationImage;
            return this;
        }

        public Builder position(List<Double> position) {
            Objects.requireNonNull(position);
            if (parallax.size() != 2) {
                throw new IllegalArgumentException("size of position should be 2");
            }
            this.position = position;
            return this;
        }

        public Builder relativeTo(DecorationRelativeTo relativeTo) {
            Objects.requireNonNull(relativeTo);
            this.relativeTo = relativeTo;
            return this;
        }

        public Builder pivotOffset(List<Double> pivotOffset) {
            Objects.requireNonNull(pivotOffset);
            if (parallax.size() != 2) {
                throw new IllegalArgumentException("size of pivotOffset should be 2");
            }
            this.pivotOffset = pivotOffset;
            return this;
        }

        public Builder rotation(Double rotation) {
            Objects.requireNonNull(rotation);
            this.rotation = rotation;
            return this;
        }

        public Builder scale(List<Double> scale) {
            Objects.requireNonNull(scale);
            if (parallax.size() != 2) {
                throw new IllegalArgumentException("size of scale should be 2");
            }
            this.scale = scale;
            return this;
        }

        public Builder tile(List<Long> tile) {
            Objects.requireNonNull(tile);
            if (parallax.size() != 2) {
                throw new IllegalArgumentException("size of tile should be 2");
            }
            this.tile = tile;
            return this;
        }

        public Builder color(String color) {
            Objects.requireNonNull(color);
            this.color = color;
            return this;
        }

        public Builder opacity(Double opacity) {
            Objects.requireNonNull(opacity);
            this.opacity = opacity;
            return this;
        }

        public Builder depth(Long depth) {
            Objects.requireNonNull(depth);
            this.depth = depth;
            return this;
        }

        public Builder parallax(List<Double> parallax) {
            Objects.requireNonNull(parallax);
            if (parallax.size() != 2) {
                throw new IllegalArgumentException("size of parallax should be 2");
            }
            this.parallax = parallax;
            return this;
        }

        public Builder tag(String tag) {
            Objects.requireNonNull(tag);
            this.tag = tag;
            return this;
        }

        public Builder imageSmoothing(Toggle imageSmoothing) {
            Objects.requireNonNull(imageSmoothing);
            this.imageSmoothing = imageSmoothing;
            return this;
        }

        public Builder failHitbox(Toggle failHitbox) {
            Objects.requireNonNull(failHitbox);
            this.failHitbox = failHitbox;
            return this;
        }

        public Builder components(String components) {
            Objects.requireNonNull(components);
            this.components = components;
            return this;
        }

    }

}
