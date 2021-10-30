package io.luxus.lib.adofai;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

@Getter
@ToString
public class CustomLevel {

    private LevelSetting levelSetting = new LevelSetting();
    private List<Tile> tiles = Arrays.asList(new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile());

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
            if (tiles.size() <= 1) {
                tiles.get(0).update(levelSetting, 0.0);
            }
            else {
                tiles.get(0).update(levelSetting, tiles.get(1).getAngle());
            }
            floor++;
        }

        Iterator<Tile> it = tiles.subList(floor - 1, tiles.size()).iterator();
        TileMeta prevTileMeta = it.next().getTileMeta();
        Tile currTile = it.next();
        while (it.hasNext()) {
            Tile nextTile = it.next();
            currTile.update(prevTileMeta, nextTile.getAngle());
            prevTileMeta = currTile.getTileMeta();
            currTile = nextTile;
        }

        if (currTile != null) {
            currTile.update(prevTileMeta, currTile.getAngle() == ANGLE_MID_TILE ?
                    prevTileMeta.getStaticAngle() : currTile.getAngle());
        }

    }

}
