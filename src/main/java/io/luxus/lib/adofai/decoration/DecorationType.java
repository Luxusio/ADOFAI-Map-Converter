package io.luxus.lib.adofai.decoration;

import io.luxus.lib.adofai.type.JsonParsable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DecorationType implements JsonParsable {
    UNKNOWN(null),
    ADD_DECORATION("AddDecoration"),
    ADD_TEXT("AddText"),
    ;

    private final String jsonName;

}
