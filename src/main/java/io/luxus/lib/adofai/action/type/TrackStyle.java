package io.luxus.lib.adofai.action.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum TrackStyle {
    STANDARD("Standard"),
    NEON("Neon"),
    NEON_LIGHT("NeonLight"),
    BASIC("Basic"),
    GEMS("Gems"),
    ;

    TrackStyle(String jsonName) {
        this.jsonName = jsonName;
    }

    private final String jsonName;

}
