package io.luxus.lib.adofai;

import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.type.TileAngle;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomLevel {

    private final LevelSetting levelSetting;
    private final List<Tile> tiles;

    @Getter
    @ToString
    public static class Builder {

        private LevelSetting.Builder levelSettingBuilder = new LevelSetting.Builder();
        private List<Tile.Builder> tileBuilders = new ArrayList<>();

        public Builder self() {
            return this;
        }

        public Builder levelSetting(LevelSetting levelSetting) {
            Objects.requireNonNull(levelSetting);
            return levelSettingBuilder(new LevelSetting.Builder().from(levelSetting));
        }
        public Builder levelSettingBuilder(LevelSetting.Builder levelSettingBuilder) {
            Objects.requireNonNull(levelSettingBuilder);
            this.levelSettingBuilder = levelSettingBuilder;
            return self();
        }

        public CustomLevel build() {
            LevelSetting levelSetting = levelSettingBuilder.build();
            return new CustomLevel(levelSetting, getTiles(levelSetting));
        }

        private List<Tile> getTiles(LevelSetting levelSetting) {
            List<Tile> results = new ArrayList<>();

            if (tileBuilders.isEmpty())
                return results;

            Iterator<Tile.Builder> bIt = tileBuilders.iterator();

            // build first tile
            Tile firstTile = bIt.next()
                    .buildFirst(levelSetting, tileBuilders.size() == 1 ? TileAngle.ZERO : tileBuilders.get(1).getAngle());

            results.add(firstTile);

            // build rest tiles
            if (!bIt.hasNext())
                return results;

            TileMeta prevTileMeta = firstTile.getTileMeta();
            Tile.Builder currTileBuilder = bIt.next();
            while (bIt.hasNext()) {
                Tile.Builder nextTileBuilder = bIt.next();
                Tile newCurrTile = currTileBuilder
                        .build(prevTileMeta, nextTileBuilder.getAngle());
                results.add(newCurrTile);

                prevTileMeta = newCurrTile.getTileMeta();
                currTileBuilder = nextTileBuilder;
            }

            Tile lastTile = currTileBuilder.buildLast(prevTileMeta);
            results.add(lastTile);

            return results;
        }

        public Builder tiles(List<Tile> tiles) {
//            if (fromFloor < 0 || fromFloor >= tiles.size()) {
//                throw new IndexOutOfBoundsException("out of range! (size=" + tiles.size() + ", given idx=" + fromFloor + ")");
//            }

            return this.tileBuilders(tiles.stream()
                    .map(tile -> new Tile.Builder().from(tile))
                    .collect(Collectors.toList()));
        }

        public Builder tileBuilders(List<Tile.Builder> tileBuilders) {
            this.tileBuilders = tileBuilders;
            return self();
        }

        public Builder tileFromAngles(List<TileAngle> angles) {
            this.tileBuilders = angles.stream()
                    .map(angle -> new Tile.Builder().angle(angle))
                    .collect(Collectors.toList());
            return self();
        }

        public Builder tileFromBuilders(List<Tile.Builder> builders) {
            this.tileBuilders = builders;
            return self();
        }

        public Builder addTileBuilder(Tile.Builder tileBuilder) {
            this.tileBuilders.add(tileBuilder);
            return self();
        }

        public Builder from(CustomLevel customLevel) {
            return self()
                    .levelSetting(customLevel.levelSetting)
                    .tiles(customLevel.tiles);
        }

    }

}
