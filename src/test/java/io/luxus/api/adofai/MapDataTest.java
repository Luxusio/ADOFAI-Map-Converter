package io.luxus.api.adofai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.UnknownAction;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.parser.ActionFactory;
import io.luxus.lib.adofai.parser.CustomLevelFactory;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MapDataTest {

    @Test
    void testReadLevel() throws Exception {
        // given
        String path = "./src/test/resources/test/test.adofai";
        JsonNode node = new ObjectMapper().readTree(new File(path));

        // when
        CustomLevel customLevel = CustomLevelParser.read(node);

        // then
        assertNotNull(customLevel);
        assertTrue(customLevel.getLevelSetting().getUnknownProperties().isEmpty());

        customLevel.getTiles().stream()
                .map(Tile::getActionMap)
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .forEach(action -> assertNotEquals(EventType.UNKNOWN, action.getEventType(), "it was Unknown : " + action.getClass().getSimpleName()));


    }

    @Test
    void testWriteLevel() throws Exception {
        // given
        String path = "./src/test/resources/test/test.adofai";
        JsonNode node = new ObjectMapper().readTree(new File(path));

        // when
        CustomLevel customLevel = CustomLevelParser.read(node);
        assert customLevel != null;
        String result = CustomLevelFactory.write(customLevel);

        // then
        assertEquals(node, new ObjectMapper().readTree(result));

    }


    @Test
    void testWriteUnknownAction() throws Exception {
        // given
        String jsonStr = "\t\t{ \"floor\": 0, \"eventType\": \"ExampleAction\", \"intProp\": 1, \"doubleProp\": 1.1, \"strProp\": \"str\", \"listProp\": [0, \"This\"], \"objProp\": { \"i\": 2, \"d\": 2.2, \"str\": \"string\", \"list\": [2.2, \"test\"] } }";

        JsonNode jsonNode = new ObjectMapper().readTree(jsonStr);
        StringBuilder sb = new StringBuilder();

        // when
        Action action = ActionFactory.read(jsonNode);
        ActionFactory.write(sb, 0, action);

        // then
        assertEquals(jsonNode, new ObjectMapper().readTree(sb.toString()), "unknownEvent must equal");

    }


}
