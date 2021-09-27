package io.luxus.lib.adofai.action.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HitSound {
    KICK("Kick"),
    HAT("Hat"),
    SHAKER("Shaker"),
    SIZZLE("Sizzle"),
    CHUCK("Chuck"),
    SHAKER_LOUD("ShakerLoud"),
    NONE("None"),
    HAMMER("Hammer"),
    KICK_CHROMA("KickChroma"),
    SNARE_ACOUSTIC_2("SnareAcoustic2"),
    SIDESTICK("Sidestick"),
    STICK("Stick"),
    REVERB_CLACK("ReverbClack"),
    SQUARESHOT("Squareshot"),
    ;

    private final String jsonName;

}
