package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TilePosition implements JsonParsable {
    THIS_TILE("ThisTile"),
    START("Start"),
    END("End"),
    ;

    private final String jsonName;

}
