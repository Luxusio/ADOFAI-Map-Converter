package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpeedType implements JsonParsable {
    BPM("Bpm"),
    MULTIPLIER("Multiplier"),
    ;

    private final String jsonName;

}
