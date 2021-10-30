package io.luxus.lib.adofai.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringJsonUtilTest {

    @Test
    void fixJsonStringTest() {
        // given
        String wrongJsonStr = "{\"brokenObject\":{\"key\":\"valu\\\"e\", }," +
                "\"brokenList\":[" +
                "{\"obj\":\"foo\"}" +
                "{\"baab\":12312345,}]}";

        // when
        String result = StringJsonUtil.fixJsonString(wrongJsonStr);

        // then
        Assertions.assertThat(result).isEqualTo("{\"brokenObject\":{\"key\":\"valu\\\"e\"},\"brokenList\":[{\"obj\":\"foo\"},{\"baab\":12312345}]}");
    }

}