package io.luxus.lib.adofai.type;

import lombok.Getter;

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
