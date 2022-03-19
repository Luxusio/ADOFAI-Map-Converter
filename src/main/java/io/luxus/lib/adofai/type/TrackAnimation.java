package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrackAnimation implements JsonParsable {
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

    private final String jsonName;

}
