package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrackStyle implements JsonParsable {
    STANDARD("Standard"),
    NEON("Neon"),
    NEON_LIGHT("NeonLight"),
    BASIC("Basic"),
    GEMS("Gems"),
    ;

    private final String jsonName;

}
