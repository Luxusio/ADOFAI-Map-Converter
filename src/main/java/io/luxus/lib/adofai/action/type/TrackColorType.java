package io.luxus.lib.adofai.action.type;

import lombok.Getter;

@Getter
public enum TrackColorType {
    SINGLE("Single"),
    STRIPES("Stripes"),
    GLOW("Glow"),
    BLINK("Blink"),
    SWITCH("Switch"),
    RAINBOW("Rainbow"),
    VOLUME("Volume"),
    ;

    TrackColorType(String jsonName) {
        this.jsonName = jsonName;
    }

    private final String jsonName;

}
