package io.luxus.lib.adofai.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberUtilTest {

    @Test
    void generalizeAngleTestNormalRange() {
        // given
        double in1 = 0;
        double in2 = 150;
        double in3 = 359.99;

        // when
        double out1 = NumberUtil.generalizeAngle(in1);
        double out2 = NumberUtil.generalizeAngle(in2);
        double out3 = NumberUtil.generalizeAngle(in3);

        // then
        Assertions.assertThat(out1).isEqualTo(in1);
        Assertions.assertThat(out2).isEqualTo(in2);
        Assertions.assertThat(out3).isEqualTo(in3);
    }

    @Test
    void generalizeAngleTestOtherRange() {
        // given
        double in1 = -1;
        double in2 = 360.0;
        double in3 = 720.0;
        double in4 = -360.0;

        // when
        double out1 = NumberUtil.generalizeAngle(in1);
        double out2 = NumberUtil.generalizeAngle(in2);
        double out3 = NumberUtil.generalizeAngle(in3);
        double out4 = NumberUtil.generalizeAngle(in4);

        // then
        Assertions.assertThat(out1).isEqualTo(in1 + 360.0);
        Assertions.assertThat(out2).isEqualTo(in2 - 360.0);
        Assertions.assertThat(out3).isEqualTo(in3 - 720.0);
        Assertions.assertThat(out4).isEqualTo(in4 + 360.0);
    }

    @RepeatedTest(100)
    void randomTest() {
        // given
        double min = -0.5;
        double range = 0.5;

        // when
        double value = NumberUtil.random(min, range);

        // then
        Assertions.assertThat(value).isGreaterThanOrEqualTo(min)
                .isLessThan(min + range);
    }

    @RepeatedTest(100)
    void randomMinMaxTest() {
        // given
        double min = 0.000001;
        double max = 1.0;

        // when
        double value = NumberUtil.randomMinMax(min, max);

        // then
        Assertions.assertThat(value).isGreaterThanOrEqualTo(min)
                .isLessThan(max);
    }

}