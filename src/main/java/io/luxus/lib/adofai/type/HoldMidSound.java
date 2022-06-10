package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HoldMidSound implements JsonParsable {
    NONE("None"),
    FUSE("Fuse"),
    SING_SING("SingSing"),
    ;

    private final String jsonName;

}
