package io.luxus.lib.adofai;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Getter
@ToString
public class CustomLevel {

    private LevelSetting levelSetting = new LevelSetting();
    private List<Tile> tiles = Arrays.asList(new Tile(), new Tile(0.0), new Tile(0.0), new Tile(0.0), new Tile(0.0), new Tile(0.0), new Tile(0.0), new Tile(0.0), new Tile(0.0), new Tile(0.0), new Tile(0.0));

    public CustomLevel() {
    }

    public CustomLevel(LevelSetting levelSetting, List<Tile> tiles) {
        this.levelSetting = levelSetting;
        this.tiles = tiles;
        updateTile(0);
    }

    public void updateTile(int floor) {
        if (floor < 0) {
            throw new IllegalArgumentException("floor must equal or greater than 0");
        }
        if (floor >= tiles.size()) {
            throw new IndexOutOfBoundsException("floor cannot be bigger than tiles size");
        }

        if (floor == 0) {
            tiles.get(0).update(levelSetting);
            floor++;
        }

        Iterator<Tile> it = tiles.subList(floor - 1, tiles.size()).iterator();
        TileMeta prevTileMeta = it.next().getTileMeta();
        while (it.hasNext()) {
            Tile currTile = it.next();
            currTile.update(prevTileMeta);
            prevTileMeta = currTile.getTileMeta();
        }

    }

}
