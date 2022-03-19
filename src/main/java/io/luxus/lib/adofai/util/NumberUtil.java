package io.luxus.lib.adofai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.luxus.lib.adofai.Constants.EPSILON;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberUtil {

    // from google-guava
    public static boolean fuzzyEquals(double a, double b) {
        return Math.copySign(a - b, 1.0) <= EPSILON
                // copySign(x, 1.0) is a branch-free version of abs(x), but with different NaN semantics
                //|| (a == b) // needed to ensure that infinities equal themselves
                //|| (Double.isNaN(a) && Double.isNaN(b))
                ;
    }


    /**
     * generalize angle exclude 360
     *
     * @param angle not generalized angle
     * @return 0 <= angle < 360
     */
    public static double generalizeAngle(double angle) {
        angle = angle - ((int) (angle / 360)) * 360;
        return angle < 0 ? angle + 360 : angle;
    }

    /**
     * returns ranged random number
     *
     * @param min minimum return value
     * @param range range of return number
     * @return returns random value min <= value < min + range to positive range.
     */
    public static double random(double min, double range) {
        return ((Math.random() - 0.5) * range) + min + 0.5 * range;
    }

    /**
     * returns ranged random number
     *
     * @param min minimum possible return value
     * @param max maximum limit of return value
     * @return random value min <= value < max
     */
    public static double randomMinMax(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Max should be bigger than min! (" + min + ", " + max + ")");
        }
        return random(min, max - min);
    }

}
