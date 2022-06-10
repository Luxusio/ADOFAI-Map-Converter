package io.luxus.lib.adofai.helper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class AngleHelperTest {



    @ParameterizedTest
    @CsvSource({
            "0,180,180,false,0",
            "0,90,180,false,90",
            "0,90,180,true,270",
            "0,45,180,false,135",
            "0,45,180,true,225",
            "0,30,120,false,90",
            "0,360,120,false,120",
    })
    void testGetNextStaticAngle(double staticAngle, double relativeAngle, double planetAngle, boolean reversed, double expected) {
        // given

        // when
        double output = AngleHelper.getNextStaticAngle(staticAngle, relativeAngle, planetAngle, reversed);

        // then
        assertThat(output).isEqualTo(expected);
    }

}