package io.luxus.lib.adofai.helper;

import io.luxus.lib.adofai.util.NumberUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.util.NumberUtil.generalizeAngle;

public class AngleHelper {

    @Getter
    @RequiredArgsConstructor
    public static class Result {
        private final double currStaticAngle;
        private final double currTravelAngle;
    }

    public static Result calculateAngleData(double prevStaticAngle, Double currAngle, Double nextAngle, boolean currReversed) {
        double currStaticAngle = isMidAngle(currAngle) ? prevStaticAngle : currAngle;
        double currTravelAngle;

        if (isMidAngle(nextAngle)) {
            currTravelAngle = 0.0;
            if (isMidAngle(currAngle)) {
                currStaticAngle += 180;
                currStaticAngle = generalizeAngle(currStaticAngle);
            }
        }
        else {
            currTravelAngle = currStaticAngle - nextAngle;
            if (currReversed) {
                currTravelAngle = -currTravelAngle;
            }

            if (!isMidAngle(currAngle)) {
                currTravelAngle += 180;
            }

            currTravelAngle = generalizeAngle(currTravelAngle);
            currTravelAngle = currTravelAngle == 0.0 ? 360.0 : currTravelAngle;
        }
        return new Result(currStaticAngle, currTravelAngle);
    }

    public static double getNextStaticAngle(double staticAngle, double relativeAngle, boolean reversed) {
        if (reversed) {
            staticAngle = staticAngle + relativeAngle - 180;
        } else {
            staticAngle = staticAngle - relativeAngle + 180;
        }

        return generalizeAngle(staticAngle);
    }

    public static boolean isMidAngle(Double angle) {
        return ANGLE_MID_TILE == null ? angle == null :
                NumberUtil.fuzzyEquals(ANGLE_MID_TILE, angle);
    }

}
