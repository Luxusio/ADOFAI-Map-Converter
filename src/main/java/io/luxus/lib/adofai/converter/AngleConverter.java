package io.luxus.lib.adofai.converter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class AngleConverter {

    @Getter
    @RequiredArgsConstructor
    public static class Result {
        private final double staticAngle;
        private final double relativeAngle;
    }

    public static AngleConverter.Result convert(Double nowAngle, Double nextAngle, boolean reversed, boolean notNone) {

        double staticAngle;
        double relativeAngle;

        if(nextAngle == ANGLE_MID_TILE) {
            staticAngle = nowAngle;
            relativeAngle = 0.0;

            if(staticAngle > 360) {
                staticAngle -= 360;
            }
        }
        else {
            staticAngle = nextAngle;

            relativeAngle = nowAngle - nextAngle;
            if(reversed) {
                relativeAngle = -relativeAngle;
            }

            if(notNone) {
                relativeAngle += 180;
            }

            if(relativeAngle <= 0) {
                relativeAngle += 360;
            }
            else if(relativeAngle > 360) {
                relativeAngle -= 360;
            }
        }

        return new AngleConverter.Result(staticAngle, relativeAngle);
    }

    public static double getNextStaticAngle(double staticAngle, double relativeAngle, boolean reversed) {
        if(reversed) {
            staticAngle = staticAngle + relativeAngle - 180;
        } else {
            staticAngle = staticAngle - relativeAngle + 180;
        }

        if(staticAngle < 0) {
            staticAngle += 360;
        } else if(staticAngle >= 360) {
            staticAngle -= 360;
        }

        return staticAngle;
    }

}
