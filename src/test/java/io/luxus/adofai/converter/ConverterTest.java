package io.luxus.adofai.converter;

import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ConverterTest {

    @Test
    void testLinearConvert() throws Exception {
        // given
        String path = "./src/test/resources/test/f777 - Hydra.adofai";
        String outPath = path.substring(0, path.length() - 7);

        // when
        //CustomLevelParser.write(CustomLevelParser.readPath(path), outPath + " Original.adofai");
        CustomLevelParser.write(OuterMapConverter.convert(path), outPath + " Outer.adofai");
        CustomLevelParser.write(ShapedMapConverter.linearConvert(path, false), outPath + " Linear.adofai");
        //CustomLevelParser.write(ShapedMapConverter.convert(path, Arrays.asList(0.0, 135.0), false), outPath + " Shape.adofai");
        CustomLevelParser.write(ShapedMapConverter.convert(path, Arrays.asList(0.0, 90.0), false), outPath + " Shape.adofai");
        CustomLevelParser.write(ShapedMapConverter.convert(path, CustomLevelParser.readPath("./src/test/resources/test/bmb.adofai"), false), outPath + " bmb Shape.adofai");
        CustomLevelParser.write(TwirlConverter.convert(path, 1.0, false), outPath + " All Twirl.adofai");
        CustomLevelParser.write(TwirlConverter.convert(path, 0.0, false), outPath + " No Twirl.adofai");
        CustomLevelParser.write(TwirlConverter.convert(path, 0.1, false), outPath + " Twirl rate 0.1.adofai");
        CustomLevelParser.write(MapEffectConverter.removeEffectConvert(path, true, true, true, true), outPath + " Non-Effect.adofai");
        CustomLevelParser.write(MapEffectConverter.transparentConvert(path, 30), outPath + " Transpose.adofai");
        CustomLevelParser.write(PseudoMapConverter.convert(path, 15, 2, true), outPath + " 2 Pseudo.adofai");
        CustomLevelParser.write(AllMidspinMapConverter.convert(path, 2), outPath + " all 2 midspin.adofai");

        // then

    }

}
