package io.luxus.lib.adofai.util;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class NumberUtilTest {

    @ParameterizedTest
    @CsvSource({
            "0,0",
            "150,150",
            "359.99,359.99",
            "360,0",
            "480,120",
            "720,0",
            "-1,359",
            "-360,0",
            "-360.45,359.55",
    })
    void generalizeAngleTestNormalRange(double input, double expect) {
        // given
        // input

        // when
        double output = NumberUtil.generalizeAngle(input);

        // then
        assertThat(output).isEqualTo(expect);
    }

    @RepeatedTest(100)
    void randomTest() {
        // given
        double min = -0.5;
        double range = 0.5;

        // when
        double value = NumberUtil.random(min, range);

        // then
        assertThat(value).isGreaterThanOrEqualTo(min)
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
        assertThat(value).isGreaterThanOrEqualTo(min)
                .isLessThan(max);
    }

}