package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrackDisappearAnimation implements JsonParsable {
    NONE("None"),
    SCATTER("Scatter"),
    SCATTER_FAR("Scatter_Far"),
    RETRACT("Retract"),
    SHRINK("Shrink"),
    SHRINK_SPIN("Shrink_Spin"),
    FADE("Fade"),
    ;

    private final String jsonName;

}
