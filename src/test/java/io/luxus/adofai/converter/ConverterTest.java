package io.luxus.adofai.converter;

import io.luxus.adofai.converter.converters.AllMidspinMapConverter;
import io.luxus.adofai.converter.converters.NoSpeedChangeMapConverter;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

public class ConverterTest {

    @Test
    void testLinearConvert() throws Exception {
        // given
        String path = "./src/test/resources/test/First_Town_Of_This_Journey.adofai";
        MapConverterDispatcher dispatcher = new MapConverterDispatcher();

        // when

        //CustomLevelParser.write(CustomLevelParser.readPath(path), outPath + " Original.adofai");
        //dispatcher.convertMapAndSave(path, ConverterType.OUTER);
        //dispatcher.convertMapAndSave(path, ConverterType.LINEAR);
        //dispatcher.convertMapAndSave(path, ConverterType.SHAPED, null, null, Arrays.asList(0.0, 135.0));
        //dispatcher.convertMapAndSave(path, ConverterType.SHAPED, null, null, Arrays.asList(0.0, 90.0));
        //dispatcher.convertMapAndSave(path, ConverterType.SHAPED, "bmb", CustomLevelParser.readPath("./src/test/resources/test/bmb.adofai"), Arrays.asList(0.0, 90.0));
        //dispatcher.convertMapAndSave(path, ConverterType.TWIRL_RATIO, 1.0);
        //dispatcher.convertMapAndSave(path, ConverterType.TWIRL_RATIO, 0.0);
        //dispatcher.convertMapAndSave(path, ConverterType.TWIRL_RATIO, 0.1);
        //dispatcher.convertMapAndSave(path, ConverterType.NO_EFFECT, new HashSet<>(Arrays.asList(EventType.MOVE_DECORATIONS, EventType.ADD_DECORATION)));
        //dispatcher.convertMapAndSave(path, ConverterType.TRANSPARENCY, 30);
        //dispatcher.convertMapAndSave(path, ConverterType.PSEUDO, 4, 15.0, false);
        dispatcher.convertMapAndSave(path, AllMidspinMapConverter.class, new AllMidspinMapConverter.Parameters(2));
        dispatcher.convertMapAndSave(path, NoSpeedChangeMapConverter.class, new NoSpeedChangeMapConverter.Parameters(113.3333333333));

        // then

    }

}
