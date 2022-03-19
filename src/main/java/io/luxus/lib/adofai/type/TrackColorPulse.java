package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrackColorPulse implements JsonParsable {
    NONE("None"),
    FORWARD("Forward"),
    BACKWARD("Backward"),
    ;

    private final String jsonName;

}
