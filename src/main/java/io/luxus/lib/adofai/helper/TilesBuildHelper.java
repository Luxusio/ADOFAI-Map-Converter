package io.luxus.lib.adofai.helper;

import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

/**
 *
 *
 */
@Getter
public class TilesBuildHelper {

    private List<Tile> tiles = new ArrayList<>();
    private List<Tile.Builder> tileBuilders = new ArrayList<>();

    public TilesBuildHelper self() {
        return this;
    }

    public List<Tile> build(LevelSetting levelSetting) {
        List<Tile> results = new ArrayList<>(tiles);

        if (tileBuilders.isEmpty())
            return results;

        if (!results.isEmpty()) {
            Tile firstTile = tileBuilders.get(0)
                    .buildFirst(levelSetting, tileBuilders.size() == 1 ? 0.0 : tileBuilders.get(1).getAngle());

            results.add(firstTile);
        }


        Iterator<Tile.Builder> bIt = tileBuilders.iterator();
        TileMeta prevTileMeta = results.get(results.size() - 1).getTileMeta();

        if (!bIt.hasNext())
            return results;

        Tile.Builder currTileBuilder = bIt.next();
        while (bIt.hasNext()) {
            Tile.Builder nextTileBuilder = bIt.next();
            Tile newCurrTile = currTileBuilder
                    .build(prevTileMeta, nextTileBuilder.getAngle());
            results.add(newCurrTile);

            prevTileMeta = newCurrTile.getTileMeta();
            currTileBuilder = nextTileBuilder;
        }

        Tile newCurrTile = currTileBuilder
                .build(prevTileMeta, currTileBuilder.getAngle() == ANGLE_MID_TILE ?
                        prevTileMeta.getStaticAngle() : currTileBuilder.getAngle());
        results.add(newCurrTile);

        return results;
    }

    public TilesBuildHelper editFrom(List<Tile> tiles, int fromFloor) {
        if (fromFloor < 0 || fromFloor >= tiles.size()) {
            throw new IndexOutOfBoundsException("out of range! (size=" + tiles.size() + ", given idx=" + fromFloor + ")");
        }

        this.tiles = tiles.subList(0, fromFloor);
        this.tileBuilders = tiles.subList(fromFloor, tiles.size()).stream()
                .map(tile -> new Tile.Builder().from(tile))
                .collect(Collectors.toList());

        return self();
    }

    public TilesBuildHelper fromAngles(List<Double> angles) {
        this.tileBuilders = angles.stream()
                .map(angle -> new Tile.Builder().angle(angle))
                .collect(Collectors.toList());
        return self();
    }

    public TilesBuildHelper fromBuilders(List<Tile.Builder> builders) {
        this.tileBuilders = builders;
        return self();
    }

    public TilesBuildHelper addTileBuilder(Tile.Builder tileBuilder) {
        this.tileBuilders.add(tileBuilder);
        return self();
    }

}
