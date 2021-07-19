package io.luxus.api.adofai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDataTest {

    @Test
    void testLoadSaveMap() {
        // given
        String path = "./src/test/resources/level/all-effects.adofai";
        String outPath = "./src/test/resources/level/all-effects-out.adofai";
        MapData mapData = new MapData();

        // when
        assertDoesNotThrow(()-> mapData.load(path));
        assertDoesNotThrow(()-> mapData.save(outPath));

        // then
        assertDoesNotThrow(()-> mapData.load(outPath));


    }

}
