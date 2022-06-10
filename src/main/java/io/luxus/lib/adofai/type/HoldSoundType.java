package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HoldSoundType implements JsonParsable {
    NONE("None"),
    FUSE("Fuse"),
    ;

    private final String jsonName;

}
