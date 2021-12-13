package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrackColorType implements JsonParsable {
    SINGLE("Single"),
    STRIPES("Stripes"),
    GLOW("Glow"),
    BLINK("Blink"),
    SWITCH("Switch"),
    RAINBOW("Rainbow"),
    VOLUME("Volume"),
    ;

    private final String jsonName;

}
