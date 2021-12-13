package io.luxus.lib.adofai.type;

import lombok.Getter;

@Getter
public enum TrackAnimation {
    NONE("None"),
    ASSEMBLE("Assemble"),
    ASSEMBLE_FAR("Assemble_Far"),
    EXTEND("Extend"),
    GROW("Grow"),
    GROW_SPIN("Grow_Spin"),
    FADE("Fade"),
    DROP("Drop"),
    RISE("Rise"),
    ;

    TrackAnimation(String jsonName) {
        this.jsonName = jsonName;
    }

    private final String jsonName;

}
