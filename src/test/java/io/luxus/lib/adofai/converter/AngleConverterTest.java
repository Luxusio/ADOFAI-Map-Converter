package io.luxus.lib.adofai.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AngleConverterTest {

    @Test
    void testGetCurrStaticAngle() {
        // given
        double ps1 = 0.0, ps2 = 0.0, ps3 = 180.0;
        double ct1 = 180.0, ct2 = 45.0, ct3 = 45.0;
        boolean r1 = false, r2 = false, r3 = false;
        boolean n1 = true, n2 = true, n3 = true;

        // when
        double cs1 = AngleConverter.getNextAngle(ps1, ct1, r1, n1);
        double cs2 = AngleConverter.getNextAngle(ps2, ct2, r2, n2);
        double cs3 = AngleConverter.getNextAngle(ps3, ct3, r3, n3);

        // then
        assertEquals(0.0, cs1);
        assertEquals(135.0, cs2);
        assertEquals(315.0, cs3);

    }

}