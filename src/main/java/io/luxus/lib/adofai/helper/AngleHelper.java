package io.luxus.lib.adofai.helper;

import io.luxus.lib.adofai.type.TileAngle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.luxus.lib.adofai.util.NumberUtil.generalizeAngle;

public class AngleHelper {

    @Getter
    @RequiredArgsConstructor
    public static class Result {
        private final double currStaticAngle;
        private final double currTravelAngle;
    }

    public static Result calculateAngleData(double prevStaticAngle, TileAngle currAngle, TileAngle nextAngle, boolean currReversed) {
        double currStaticAngle = currAngle.isMidspin() ? prevStaticAngle : currAngle.getAngle();
        double currTravelAngle;

        if (nextAngle.isMidspin()) {
            currTravelAngle = 0.0;
            if (currAngle.isMidspin()) {
                currStaticAngle += 180;
                currStaticAngle = generalizeAngle(currStaticAngle);
            }
        }
        else {
            currTravelAngle = currStaticAngle - nextAngle.getAngle();
            if (currReversed) {
                currTravelAngle = -currTravelAngle;
            }

            if (!currAngle.isMidspin()) {
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

}
