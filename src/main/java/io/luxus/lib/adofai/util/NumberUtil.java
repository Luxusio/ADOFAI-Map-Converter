package io.luxus.lib.adofai.util;

import static io.luxus.lib.adofai.Constants.EPSILON;

public class NumberUtil {

    // from google-guava
    public static boolean fuzzyEquals(double a, double b) {
        return Math.copySign(a - b, 1.0) <= EPSILON
                // copySign(x, 1.0) is a branch-free version of abs(x), but with different NaN semantics
                //|| (a == b) // needed to ensure that infinities equal themselves
                //|| (Double.isNaN(a) && Double.isNaN(b))
                ;
    }

    public static double generalizeAngleExclude360(double angle) {
        return angle >= 360 ? angle - 360 :
                angle < 0 ? angle + 360 :
                        angle;
    }

    public static double generalizeAngleInclude360(double angle) {
        return angle > 360 ? angle - 360 :
                angle <= 0 ? angle + 360 :
                        angle;
    }

}
