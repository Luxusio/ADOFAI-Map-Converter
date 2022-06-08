package io.luxus.lib.adofai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.luxus.lib.adofai.parser.ActionFactory;
import io.luxus.lib.adofai.parser.CustomLevelFactory;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.action.Action;
import io.luxus.lib.adofai.util.StringJsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapDataTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/level/all-r77.adofai",
            "src/test/resources/level/all-r83.adofai",
            "src/test/resources/level/all-r84.adofai",
            "src/test/resources/level/all-r90.adofai",
    })
    void testReadLevel(String path) throws Exception {
        // given
        // path

        // when
        CustomLevel customLevel = CustomLevelParser.readPath(path);

        // then
        assertThat(customLevel).isNotNull();
        assertThat(customLevel.getLevelSetting().getUnknownProperties()).isEmpty();

        customLevel.getTiles().stream()
                .map(Tile::getActionMap)
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .forEach(action -> assertThat(action)
                        .matches(a -> !a.getEventType().equals(EventType.UNKNOWN)));

    }

    @Test
    void testWriteLevel() throws Exception {
        // given
        String path = "src/test/resources/level/all-r90.adofai";
        JsonNode node = new ObjectMapper().readTree(StringJsonUtil.fixJsonString(CustomLevelParser.readString(new File(path))));

        // when
        CustomLevel customLevel = CustomLevelParser.read(node);
        assert customLevel != null;
        String result = CustomLevelFactory.write(customLevel);

        // then
        System.out.println("result = " + result);
        assertThat(new ObjectMapper().readTree(result)).isEqualTo(node);
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
        assertThat(new ObjectMapper().readTree(sb.toString())).isEqualTo(jsonNode);
    }


}
