package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.HashMap;

public class OnlyBpmSetSpeedConverter {

    public static CustomLevel convert(String path) throws IOException {
        CustomLevel customLevel = CustomLevelParser.readPath(path);

        return MapSpeedConverterBase.convert(customLevel, false,
                applyEach -> {
                    Tile tile = applyEach.getTile();

                    Tile newTile = new Tile(tile.getAngle(), new HashMap<>(tile.getActionMap()));
                    applyEach.getNewTileList().add(newTile);
                    return new MapSpeedConverterBase.ApplyEachReturnValue(
                            newTile, tile.getTileMeta().getBpm(),
                            1.0, 1.0);
                });
    }

}
