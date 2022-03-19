package io.luxus.lib.adofai.helper;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.type.action.Action;
import io.luxus.lib.adofai.type.action.Twirl;
import io.luxus.lib.adofai.type.EventType;

import java.util.ArrayList;
import java.util.List;

public class TileHelper {

    /**
     * Combines 2 action map of tiles.
     * Add srcTile's all action to destTile
     *
     * @param destTile destination tile to add actions
     * @param srcTile source tile to extract actions
     */
    public static void combineTile(Tile destTile, Tile srcTile) {

        srcTile.getActionMap().forEach((eventType, value) -> {

            List<Action> destinationList = destTile.getActions(eventType);
            destinationList.addAll(value);

            if (eventType.isSingleOnly()) {
                if (eventType == EventType.TWIRL) {
                    boolean hasTwirl = (destinationList.size() % 2) == 1;
                    destinationList.clear();
                    if (hasTwirl) {
                        destinationList.add(new Twirl.Builder().build());
                    }
                } else {
                    while (destinationList.size() > 1) {
                        destinationList.remove(0);
                    }
                }
            }
        });

    }

    /**
     * get same timing tiles as list(normal tile + n midspin tile)
     *
     * @param tiles all tiles of level
     * @param fromIndex get tile index.
     * @return same tile list including midspin
     */
    public static List<Tile> getSameTimingTiles(List<Tile> tiles, int fromIndex) {
        List<Tile> sameTimingTiles = new ArrayList<>();

        Tile tile = tiles.get(fromIndex);
        sameTimingTiles.add(tile);

        for (int i = fromIndex + 1; i < tiles.size() && tile.getTileMeta().getTravelAngle() == 0.0; i++) {
            tile = tiles.get(i);
            sameTimingTiles.add(tile);
        }

        return sameTimingTiles;
    }

    public static double calculateZeroTileTravelMs(CustomLevel customLevel) {
        double zeroTileTravelAngle = TileMeta.calculateTotalTravelAngle(
                getSameTimingTiles(customLevel.getTiles(), 0));

        long straightAngleOffset = customLevel.getLevelSetting().getOffset();
        double additionalOffset = (60000.0 / (customLevel.getLevelSetting().getBpm() * (180 / (zeroTileTravelAngle - 180.0))));

        return straightAngleOffset + additionalOffset;
    }

}
