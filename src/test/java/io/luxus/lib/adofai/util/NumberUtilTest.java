package io.luxus.lib.adofai.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class NumberUtilTest {

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