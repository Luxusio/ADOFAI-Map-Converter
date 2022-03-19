package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HitSound implements JsonParsable {
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
    POWER_DOWN("PowerDown"),
    POWER_UP("PowerUp"),
    KICK_HOUSE("KickHouse"),
    KICK_RUPTURE("KickRupture"),
    HAT_HOUSE("HatHouse"),
    SNARE_HOUSE("SnareHouse"),
    SNARE_VAPOR("SnareVapor"),
    CLAP_HIT("ClapHit"),
    CLAP_HIT_ECHO("ClapHitEcho"),
    REVERB_CLAP("ReverbClap"),
    ;

    private final String jsonName;

}
