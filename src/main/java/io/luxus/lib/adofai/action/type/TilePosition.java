package io.luxus.lib.adofai.action.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TilePosition {
    THIS_TILE("ThisTile"),
    START("Start"),
    END("End"),
    ;

    private final String jsonName;

}
