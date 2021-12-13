package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CameraRelativeTo {
    PLAYER("Player"),
    TILE("Tile"),
    GLOBAL("Global"),
    LAST_POSITION("LastPosition"),
    ;

    private final String jsonName;

}
