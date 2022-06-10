package io.luxus.lib.adofai.decoration;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.Objects;

@Getter
@ToString
public class AddDecoration extends Decoration {

    private final String decorationImage;
    private final Pair<Double, Double> position;
    private final DecorationRelativeTo relativeTo;
    private final Pair<Double, Double> pivotOffset;
    private final Double rotation;
    private final Pair<Double, Double> scale;
    private final Pair<Long, Long> tile;
    private final String color;
    private final Double opacity;
    private final Long depth;
    private final Pair<Double, Double> parallax;
    private final String tag;
    private final Toggle imageSmoothing;
    private final Toggle failHitbox;
    private final String components;

    private AddDecoration(Boolean visible, Long floor, String decorationImage, Pair<Double, Double> position, DecorationRelativeTo relativeTo,
                          Pair<Double, Double> pivotOffset, Double rotation, Pair<Double, Double> scale, Pair<Long, Long> tile, String color,
                          Double opacity, Long depth, Pair<Double, Double> parallax, String tag, Toggle imageSmoothing,
                          Toggle failHitbox, String components) {
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
        private Pair<Double, Double> position = Pair.with(0.0, 0.0);
        private DecorationRelativeTo relativeTo = DecorationRelativeTo.GLOBAL;
        private Pair<Double, Double> pivotOffset = Pair.with(0.0, 0.0);
        private Double rotation = 0.0;
        private Pair<Double, Double> scale = Pair.with(100.0, 100.0);
        private Pair<Long, Long> tile = Pair.with(1L, 1L);
        private String color = "ffffff";
        private Double opacity = 100.0;
        private Long depth = 0L;
        private Pair<Double, Double> parallax = Pair.with(0.0, 0.0);
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

        public Builder position(Pair<Double, Double> position) {
            Objects.requireNonNull(position);
            this.position = position;
            return this;
        }

        public Builder relativeTo(DecorationRelativeTo relativeTo) {
            Objects.requireNonNull(relativeTo);
            this.relativeTo = relativeTo;
            return this;
        }

        public Builder pivotOffset(Pair<Double, Double> pivotOffset) {
            Objects.requireNonNull(pivotOffset);
            this.pivotOffset = pivotOffset;
            return this;
        }

        public Builder rotation(Double rotation) {
            Objects.requireNonNull(rotation);
            this.rotation = rotation;
            return this;
        }

        public Builder scale(Pair<Double, Double> scale) {
            Objects.requireNonNull(scale);
            this.scale = scale;
            return this;
        }

        public Builder tile(Pair<Long, Long> tile) {
            Objects.requireNonNull(tile);
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

        public Builder parallax(Pair<Double, Double> parallax) {
            Objects.requireNonNull(parallax);
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
