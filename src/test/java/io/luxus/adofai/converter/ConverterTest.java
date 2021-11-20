package io.luxus.adofai.converter;

import io.luxus.adofai.converter.converters.*;
import io.luxus.adofai.converter.converters.effect.MapEffectConverter;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

public class ConverterTest {

    @Test
    void testLinearConvert() throws Exception {
        // given
        String path = "./src/test/resources/test/f777 - Hydra.adofai";
        String outPath = path.substring(0, path.length() - 7);
        MapConverterDispatcher dispatcher = new MapConverterDispatcher();

        // when

        //CustomLevelParser.write(CustomLevelParser.readPath(path), outPath + " Original.adofai");
        dispatcher.convertMapAndSave(path, ConverterType.OUTER);
        dispatcher.convertMapAndSave(path, ConverterType.LINEAR);
        //dispatcher.convertMapAndSave(path, ConverterType.SHAPED, null, null, Arrays.asList(0.0, 135.0));
        dispatcher.convertMapAndSave(path, ConverterType.SHAPED, null, null, Arrays.asList(0.0, 90.0));
        dispatcher.convertMapAndSave(path, ConverterType.SHAPED, "bmb", CustomLevelParser.readPath("./src/test/resources/test/bmb.adofai"), Arrays.asList(0.0, 90.0));
        dispatcher.convertMapAndSave(path, ConverterType.TWIRL_RATIO, 1.0);
        dispatcher.convertMapAndSave(path, ConverterType.TWIRL_RATIO, 0.0);
        dispatcher.convertMapAndSave(path, ConverterType.TWIRL_RATIO, 0.1);
        dispatcher.convertMapAndSave(path, ConverterType.NO_EFFECT, new HashSet<>(Arrays.asList(EventType.MOVE_DECORATIONS, EventType.ADD_DECORATION)));
        dispatcher.convertMapAndSave(path, ConverterType.TRANSPARENCY, 30);
        dispatcher.convertMapAndSave(path, ConverterType.PSEUDO, 2);
        dispatcher.convertMapAndSave(path, ConverterType.MIDSPIN, 2);
        CustomLevelParser.write(MapEffectConverter.removeEffectConvert(path, true, true, true, true), outPath + " Non-Effect.adofai");
        CustomLevelParser.write(MapEffectConverter.transparentConvert(path, 30), outPath + " Transpose.adofai");
        CustomLevelParser.write(PseudoMapConverter.convert(path, 15, 2, true), outPath + " 2 Pseudo.adofai");
        CustomLevelParser.write(AllMidspinMapConverter.convert(path, 2), outPath + " all 2 midspin.adofai");

        // then

    }

}
