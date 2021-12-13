package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameSound implements JsonParsable {
    HITSOUND("Hitsound"),
    MIDSPIN("Midspin"),
    ;

    private final String jsonName;

}
