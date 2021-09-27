package io.luxus.lib.adofai.action.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpeedType {
    BPM("Bpm"),
    MULTIPLIER("Multiplier"),
    ;

    private final String jsonName;

}
