package io.luxus.adofai.converter;

import io.luxus.adofai.converterv2.MapEffectConverter;
import io.luxus.adofai.converterv2.ShapedMapConverterV2;
import io.luxus.adofai.converterv2.OuterMapConverterV2;
import io.luxus.adofai.converterv2.TwirlConverterV2;
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
        //CustomLevelParser.write(CustomLevelParser.readPath(path), outPath + " Original.adofai");
        CustomLevelParser.write(OuterMapConverterV2.convert(path), outPath + " Outer.adofai");
        CustomLevelParser.write(ShapedMapConverterV2.linearConvert(path, false), outPath + " Linear.adofai");
        //CustomLevelParser.write(ShapedMapConverter.convert(path, Arrays.asList(0.0, 135.0), false), outPath + " Shape.adofai");
        CustomLevelParser.write(ShapedMapConverterV2.convert(path, Arrays.asList(0.0, 90.0), false), outPath + " Shape.adofai");
        CustomLevelParser.write(ShapedMapConverterV2.convert(path, CustomLevelParser.readPath("./src/test/resources/test/bmb.adofai"), false), outPath + " bmb Shape.adofai");
        CustomLevelParser.write(TwirlConverterV2.convert(path, 1.0, false), outPath + " All Twirl.adofai");
        CustomLevelParser.write(TwirlConverterV2.convert(path, 0.0, false), outPath + " No Twirl.adofai");
        CustomLevelParser.write(TwirlConverterV2.convert(path, 0.1, false), outPath + " Twirl rate 0.1.adofai");
        CustomLevelParser.write(MapEffectConverter.removeEffectConvert(path, true, true, true, true), outPath + " Non-Effect.adofai");
        CustomLevelParser.write(MapEffectConverter.transparentConvert(path, 30), outPath + " Transpose.adofai");

        // then

    }

}
