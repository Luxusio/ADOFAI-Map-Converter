package io.luxus.adofai.converter;

import io.luxus.adofai.converter.converters.ShapedMapConverter;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MapShapedMapConverterTest {

    @Test
    void testConvert() throws IOException {
        // given
        String path = "./src/test/resources/test/sb.adofai";
        String shapePath = "./src/test/resources/test/bmb.adofai";

        // when
        CustomLevel result  = new ShapedMapConverter(new I18n()).convert(CustomLevelParser.readPath(path), new ShapedMapConverter.Parameters("bmb", CustomLevelParser.readPath(shapePath), null));


        // then
        CustomLevel shapeLevel = CustomLevelParser.readPath(shapePath);

        List<Tile> resultTiles = result.getTiles();
        List<Tile> shapeTiles = shapeLevel.getTiles();

        for (int i = 1; i < shapeTiles.size() && i < resultTiles.size(); i++) {
            Assertions.assertThat(resultTiles.get(i).getActions(EventType.TWIRL).size())
                    .isEqualTo(shapeTiles.get(i).getActions(EventType.TWIRL).size());
        }


    }


}
