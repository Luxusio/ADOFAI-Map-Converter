package io.luxus.lib.adofai.helper;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
@ToString
public class JsonPropertyReader {

    private final Map<String, JsonNode> propertyMap;

    public <T1, T2> void read(String name, Consumer<T2> consumer, Function<JsonNode, T1> mapper1, Function<T1, T2> mapper2) {
        readO(name, o -> o.map(mapper1).map(mapper2)).ifPresent(consumer);
    }

    public <T> void read(String name, Consumer<T> consumer, Function<JsonNode, T> mapper) {
        readO(name, o -> o.map(mapper)).ifPresent(consumer);
    }

    public <T1, T2> T2 readAndGet(String name, Consumer<T2> consumer, Function<JsonNode, T1> mapper1, Function<T1, T2> mapper2) {
        return readO(name, o -> o.map(mapper1).map(mapper2)).orElse(null);
    }

    public <T> T readAndGet(String name, Function<JsonNode, T> mapper) {
        return readO(name, o -> o.map(mapper)).orElse(null);
    }

    public <T> Optional<T> readO(String name, Function<Optional<JsonNode>, Optional<T>> mapper) {
        JsonNode node = propertyMap.remove(name);
        Optional<JsonNode> optional = Optional.ofNullable(node);
        try {
            Optional<T> o = mapper.apply(optional);
            if (node != null && !o.isPresent()) {
                propertyMap.put(name, node);
            }
            return o;
        } catch (Throwable t) {
            propertyMap.put(name, node);
            throw t;
        }
    }

    public boolean isEmpty() {
        return propertyMap.isEmpty();
    }

}
