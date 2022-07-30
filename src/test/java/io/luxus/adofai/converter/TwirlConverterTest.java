package io.luxus.adofai.converter;

import io.luxus.adofai.converter.converters.TwirlConverter;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TwirlConverterTest {

    @Test
    void testConvertTwirl() throws IOException {
        // given
        String path = "./src/test/resources/test/sb.adofai";

        // when
        CustomLevel resultAllTwirl = new TwirlConverter(new I18n()).convert(CustomLevelParser.readPath(path), new TwirlConverter.Parameters(1.0));
        CustomLevel resultNoTwirl = new TwirlConverter(new I18n()).convert(CustomLevelParser.readPath(path), new TwirlConverter.Parameters(0.0));

        // then
        CustomLevel original = CustomLevelParser.readPath(path);

        assertThat(resultAllTwirl.getTiles().size()).isEqualTo(original.getTiles().size());
        assertThat(resultNoTwirl.getTiles().size()).isEqualTo(original.getTiles().size());

        List<Tile> allTwirlTiles = resultAllTwirl.getTiles();
        List<Tile> noTwirlTiles = resultNoTwirl.getTiles();
        List<Tile> originalTiles = original.getTiles();

        for (int i = 0; i < allTwirlTiles.size(); i++) {
            Tile allTwirlTile = allTwirlTiles.get(i);
            Tile noTwirlTile = noTwirlTiles.get(i);
            Tile originalTile = originalTiles.get(i);

            assertThat(allTwirlTile.getTileMeta().getBpm()).isEqualTo(originalTile.getTileMeta().getBpm());
            assertThat(noTwirlTile.getTileMeta().getBpm()).isEqualTo(originalTile.getTileMeta().getBpm());
        }

    }

}
