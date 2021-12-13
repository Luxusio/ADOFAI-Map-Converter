package io.luxus.lib.adofai.type;

import lombok.Getter;

@Getter
public enum TrackDisappearAnimation {
    NONE("None"),
    SCATTER("Scatter"),
    SCATTER_FAR("Scatter_Far"),
    RETRACT("Retract"),
    SHRINK("Shrink"),
    SHRINK_SPIN("Shrink_Spin"),
    FADE("Fade"),
    ;

    TrackDisappearAnimation(String jsonName) {
        this.jsonName = jsonName;
    }

    private final String jsonName;

}
