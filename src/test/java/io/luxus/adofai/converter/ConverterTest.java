package io.luxus.adofai.converter;

import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ConverterTest {

    @Test
    void testLinearConvert() throws Exception {
        // given
        String path = "./src/test/resources/test/sb.adofai";
        String outPath = path.substring(0, path.length() - 7);

        // when
        CustomLevelParser.write(CustomLevelParser.readPath(path), outPath + " Original.adofai");
        CustomLevelParser.write(OuterMapConverter.convert(path), outPath + " Outer.adofai");
        CustomLevelParser.write(LinearMapConverter.convert(path, false), outPath + " Linear.adofai");
        CustomLevelParser.write(ShapedMapConverter.convert(path, Arrays.asList(1.0, 179.0), false), outPath + " Shape.adofai");
        CustomLevelParser.write(MapShapedMapConverter.convert(path, CustomLevelParser.readPath("./src/test/resources/test/bmb.adofai"), false), outPath + " Shape.adofai");
        CustomLevelParser.write(TwirlConverter.convert(path, true, false), outPath + " All Twirl.adofai");
        CustomLevelParser.write(TwirlConverter.convert(path, false, false), outPath + " No Twirl.adofai");
        CustomLevelParser.write(NonEffectConverter.convert(path, true, true, true, true), outPath + " Non-Effect.adofai");
        CustomLevelParser.write(TransposeMapConverter.convert(path, 0), outPath + " Transpose.adofai");

        // then

    }

}
