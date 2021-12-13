package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BGDisplayModeType {
    FIT_TO_SCREEN("FitToScreen"),
    UNSCALED("Unscaled"),
    TILED("Tiled"),
    ;

    private final String jsonName;

}
