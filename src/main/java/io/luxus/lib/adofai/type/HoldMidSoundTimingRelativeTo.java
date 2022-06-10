package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HoldMidSoundTimingRelativeTo implements JsonParsable {
    END("End"),
    START("Start"),
    ;

    private final String jsonName;

}
