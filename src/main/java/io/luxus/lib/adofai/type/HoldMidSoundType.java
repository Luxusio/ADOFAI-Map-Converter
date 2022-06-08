package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HoldMidSoundType implements JsonParsable {
    ONCE("Once"),
    REPEAT("Repeat"),
    ;

    private final String jsonName;

}
