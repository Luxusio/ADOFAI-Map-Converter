package io.luxus.lib.adofai.helper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.util.NumberUtil.generalizeAngleExclude360;
import static io.luxus.lib.adofai.util.NumberUtil.generalizeAngleInclude360;

public class AngleHelper {

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

            currTravelAngle = generalizeAngleInclude360(currTravelAngle);
        }

        return new Result(currStaticAngle, currTravelAngle);
    }

    public static double getNextStaticAngle(double staticAngle, double relativeAngle, boolean reversed) {
        if (reversed) {
            staticAngle = staticAngle + relativeAngle - 180;
        } else {
            staticAngle = staticAngle - relativeAngle + 180;
        }

        return generalizeAngleExclude360(staticAngle);
    }

}
