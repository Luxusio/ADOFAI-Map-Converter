package io.luxus.lib.adofai.action.type;

import lombok.Getter;

@Getter
public enum TrackColorPulse {
    NONE("None"),
    FORWARD("Forward"),
    BACKWARD("Backward"),
    ;

    TrackColorPulse(String jsonName) {
        this.jsonName = jsonName;
    }

    private final String jsonName;

}
