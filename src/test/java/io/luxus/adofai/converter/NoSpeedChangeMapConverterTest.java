package io.luxus.adofai.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.util.StringJsonUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class NoSpeedChangeMapConverterTest {

    @Test
    void testConvert() throws Exception {
        // given
        String path = "./src/test/resources/convertTest/level.adofai";
        String jsonStr = StringJsonUtil.fixJsonString(CustomLevelParser.readString(new File(path)));
        JsonNode node = new ObjectMapper().readTree(jsonStr);
        CustomLevel original = CustomLevelParser.read(node);

        double targetBpm = 31.25;

        // when
        CustomLevel result = NoSpeedChangeMapConverter.convert(path, targetBpm);
        CustomLevelParser.write(result, path.substring(0, path.length() - 7) + " " + targetBpm + "bpm.adofai");

        // then
        assertNotNull(original);
        assertNotNull(result);

    }

}