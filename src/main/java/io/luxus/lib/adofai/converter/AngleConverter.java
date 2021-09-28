package io.luxus.lib.adofai.converter;

import com.google.common.math.DoubleMath;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.Constants.EPSILON;

public class AngleConverter {

    @Getter
    @RequiredArgsConstructor
    public static class Result {
        private final double currStaticAngle;
        private final double currTravelAngle;
    }

    public static Result convert(double prevStaticAngle, Double currAngle, Double nextAngle, boolean currReversed) {
        return convert(prevStaticAngle, currAngle, nextAngle, currReversed, currAngle != ANGLE_MID_TILE);
    }

    public static Result convert(double prevStaticAngle, Double currAngle, Double nextAngle, boolean currReversed, boolean currNotNone) {

        double currStaticAngle = currAngle == ANGLE_MID_TILE ? prevStaticAngle : currAngle;
        double currTravelAngle;

        if (nextAngle == ANGLE_MID_TILE) {
            currTravelAngle = 0.0;
        }
        else {
            currTravelAngle = currStaticAngle - nextAngle;
            if (currReversed) {
                currTravelAngle = -currTravelAngle;
            }

            if (currNotNone) {
                currTravelAngle += 180;
            }

            if (currTravelAngle <= 0) {
                currTravelAngle += 360;
            }
            else if (currTravelAngle > 360) {
                currTravelAngle -= 360;
            }
        }

        return new Result(currStaticAngle, currTravelAngle);
    }

    public static Double getNextAngle(double currStaticAngle, double currTravelAngle, boolean reversed, boolean currNotNone) {

        if (DoubleMath.fuzzyEquals(currTravelAngle, 0.0, EPSILON)) {
            System.out.println(currStaticAngle + ", " + currTravelAngle + ", " + reversed + " : " + null);
            return ANGLE_MID_TILE;
        }

        double nextAngle;
        if (reversed) {
            nextAngle = currStaticAngle + currTravelAngle;
        } else {
            nextAngle = currStaticAngle - currTravelAngle;
        }

        if (currNotNone) {
            nextAngle += 180;
        }

        if (nextAngle < 0) {
            nextAngle += 360;
        } else if(nextAngle >= 360) {
            nextAngle -= 360;
        }

        return nextAngle;
    }

    public static double getNextStaticAngle(double staticAngle, double relativeAngle, boolean reversed) {
        if (reversed) {
            staticAngle = staticAngle + relativeAngle - 180;
        } else {
            staticAngle = staticAngle - relativeAngle + 180;
        }

        if (staticAngle < 0) {
            staticAngle += 360;
        } else if (staticAngle >= 360) {
            staticAngle -= 360;
        }

        return staticAngle;
    }

}
