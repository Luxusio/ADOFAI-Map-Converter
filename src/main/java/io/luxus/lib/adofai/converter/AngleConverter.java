package io.luxus.lib.adofai.converter;

import com.google.common.math.DoubleMath;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.Constants.EPSILON;

public class AngleConverter {

    @Getter
    @RequiredArgsConstructor
    public static class Result2 {
        private final double currStaticAngle;
        private final double currTravelAngle;
    }

    public static AngleConverter.Result2 convert2(double prevStaticAngle, Double currAngle, Double nextAngle, boolean currReversed) {
        return convert2(prevStaticAngle, currAngle, nextAngle, currReversed, currAngle != ANGLE_MID_TILE);
    }
    public static AngleConverter.Result2 convert2(double prevStaticAngle, Double currAngle, Double nextAngle, boolean currReversed, boolean currNotNone) {

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

        return new Result2(currStaticAngle, currTravelAngle);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Result {
        private final double nextStaticAngle;
        private final double currTravelAngle;
    }

    public static AngleConverter.Result convert(double currStaticAngle, Double nextAngle, boolean currReversed, boolean currNotNone) {

        double nextStaticAngle;
        double currTravelAngle;

        if (nextAngle == ANGLE_MID_TILE) {
            nextStaticAngle = currStaticAngle;
            currTravelAngle = 0.0;

            if (nextStaticAngle >= 360) {
                nextStaticAngle -= 360;
            }
        }
        else {
            nextStaticAngle = nextAngle;

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

        return new AngleConverter.Result(nextStaticAngle, currTravelAngle);
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
