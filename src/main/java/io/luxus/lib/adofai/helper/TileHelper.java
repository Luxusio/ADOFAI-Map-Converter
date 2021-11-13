package io.luxus.lib.adofai.helper;

import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;

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
                        destinationList.add(new Twirl());
                    }
                } else {
                    while (destinationList.size() > 1) {
                        destinationList.remove(0);
                    }
                }
            }
        });

    }

}
