package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Ease {
    LINEAR("Linear"),
    IN_SINE("InSine"),
    OUT_SINE("OutSine"),
    IN_OUT_SINE("InOutSine"),
    IN_QUAD("InQuad"),
    OUT_QUAD("OutQuad"),
    IN_OUT_QUAD("InOutQuad"),
    IN_CUBIC("InCubic"),
    OUT_CUBIC("OutCubic"),
    IN_OUT_CUBIC("InOutCubic"),
    IN_QUART("InQuart"),
    OUT_QUART("OutQuart"),
    IN_OUT_QUART("InOutQuart"),
    IN_QUINT("InQuint"),
    OUT_QUINT("OutQuint"),
    IN_OUT_QUINT("InOutQuint"),
    IN_EXPO("InExpo"),
    OUT_EXPO("OutExpo"),
    IN_OUT_EXPO("InOutExpo"),
    IN_CIRC("InCirc"),
    OUT_CIRC("OutCirc"),
    IN_OUT_CIRC("InOutCirc"),
    IN_ELASTIC("InElastic"),
    OUT_ELASTIC("OutElastic"),
    IN_OUT_ELASTIC("InOutElastic"),
    IN_BACK("InBack"),
    OUT_BACK("OutBack"),
    IN_OUT_BACK("InOutBack"),
    IN_BOUNCE("InBounce"),
    OUT_BOUNCE("OutBounce"),
    IN_OUT_BOUNCE("InOutBounce"),
    FLASH("Flash"),
    IN_FLASH("InFlash"),
    OUT_FLASH("OutFlash"),
    IN_OUT_FLASH("InOutFlash"),
    ;

    private final String jsonName;

}
