package io.luxus.lib.adofai.action.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum DecorationRelativeTo {
    TILE("Tile"),
    GLOBAL("Global"),
    RED_PLANET("RedPlanet"),
    BLUE_PLANET("BluePlanet"),
    ;

    DecorationRelativeTo(String jsonName) {
        this.jsonName = jsonName;
    }

    private final String jsonName;

}
