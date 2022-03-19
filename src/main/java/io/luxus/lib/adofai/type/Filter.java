package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Filter implements JsonParsable {
    GRAYSCALE("Grayscale"),
    SEPIA("Sepia"),
    INVERT("Invert"),
    VHS("VHS"),
    EIGHTIES_TV("EightiesTV"),
    FIFTIES_TV("FiftiesTV"),
    ARCADE("Arcade"),
    LED("LED"),
    RAIN("Rain"),
    BLIZZARD("Blizzard"),
    PIXEL_SNOW("PixelSnow"),
    COMPRESSION("Compression"),
    GLITCH("Glitch"),
    PIXELATE("Pixelate"),
    WAVES("Waves"),
    STATIC("Static"),
    GRAIN("Grain"),
    MOTION_BLUR("MotionBlur"),
    FISHEYE("Fisheye"),
    ABERRATION("Aberration"),
    DRAWING("Drawing"),
    NEON("Neon"),
    HANDHELD("Handheld"),
    NIGHT_VISION("NightVision"),
    FUNK("Funk"),
    TUNNEL("Tunnel"),
    WEIRD_3D("Weird3D"),
    ;

    private final String jsonName;

}
