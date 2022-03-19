package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Toggle implements JsonParsable {
    ENABLED("Enabled"),
    DISABLED("Disabled"),
    ;

    private final String jsonName;

}
