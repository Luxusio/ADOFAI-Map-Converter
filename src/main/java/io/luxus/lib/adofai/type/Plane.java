package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Plane implements JsonParsable {
    BACKGROUND("Background"),
    FOREGROUND("Foreground"),
    ;

    private final String jsonName;

}
