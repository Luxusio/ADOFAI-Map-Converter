package io.luxus.lib.adofai.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;
import java.util.function.Function;

public class StringJsonUtil {

    public static String fixJsonString(String jsonStr) {

        StringBuilder sb = new StringBuilder(jsonStr.length());

        boolean isString = false;
        boolean escape = false;
        boolean comma = false;

        for (char c : jsonStr.toCharArray()) {
            if (c == '\\') {
                escape = true;
                sb.append(c);
            }
            else {
                if (isString) {
                    if (c == '"') {
                        if (escape) {
                            sb.append(c);
                        }
                        else { // string end
                            isString = false;
                            sb.append(c);
                        }
                    }
                    else {
                        // string content. just add
                        sb.append(c);
                    }
                }
                else {
                    if (c == '"') {
                        isString = true;
                        if (comma) {
                            comma = false;
                            sb.append(',');
                        }
                        sb.append(c);
                    }
                    else if (c == '}' || c == ']') {
                        sb.append(c);
                        comma = false;
                    }
                    else if (c == ',') {
                        comma = true;
                    }
                    else if (c != ' ' && c != '\t' && c != '\n') {
                        if (comma) {
                            comma = false;
                            sb.append(',');
                        }
                        sb.append(c);
                    }
                }
                escape = false;
            }
        }

        return sb.toString();
    }

    public static <T> Function<String, T> getOrThrowFunc(Map<String, T> map) {
        return name -> {
            T result = map.get(name);
            if (result == null) throw new IllegalStateException("Property not found!(" + name + ")");
            return result;
        };
    }

    public static <T1, T2> T2 readProperty(Map<String, JsonNode> map, String name, Function<JsonNode, T1> mapper, Function<T1, T2> mapper2) {
        return readPropertyO(map, name, jsonNode -> jsonNode.map(mapper).map(mapper2)).orElse(null);
    }

    public static <T> T readProperty(Map<String, JsonNode> map, String name, Function<JsonNode, T> mapper) {
        return readPropertyO(map, name, jsonNode -> jsonNode.map(mapper)).orElse(null);
    }

    public static <T> Optional<T> readPropertyO(Map<String, JsonNode> map, String name, Function<Optional<JsonNode>, Optional<T>> mapper) {
        JsonNode node = map.remove(name);
        Optional<JsonNode> optional = Optional.ofNullable(node);
        try {
            return mapper.apply(optional);
        } catch (Throwable t) {
            map.put(name, node);
            throw t;
        }
    }

    public static <R> Function<JsonNode, List<R>> nodeToXYListFunc(Function<? super JsonNode, ? extends R> mapper) {
        return node -> {
            List<R> result = nodeToList(node, mapper);
            if (result.size() == 0) return null;
            else if (result.size() == 1) {
                result.add(result.get(0));
            }
            else if (result.size() > 2) throw new IllegalStateException("property is more than 2");
            return result;
        };
    }

    public static <R> List<R> nodeToList(JsonNode node, Function<? super JsonNode, ? extends R> mapper) {
        List<R> list = new ArrayList<>();
        Iterator<JsonNode> it = node.elements();
        while (it.hasNext()) {
            list.add(mapper.apply(it.next()));
        }
        return list;
    }

    public static void startWriteObj(StringBuilder sb, String name, Object value) {
        sb.append("\t\t{ \"").append(name).append("\": ");
        writeVar(sb, value);
    }

    public static void writeVar(StringBuilder sb, String name, Object value) {
        if (value == null) return;
        sb.append(", \"").append(name).append("\": ");
        writeVar(sb, value);
    }

    public static void endWriteObj(StringBuilder sb) {
        sb.append(" }");
    }

    public static void writeVar(StringBuilder sb, Object value) {
        if (value instanceof String) {
            sb.append('"').append(((String) value)
                    .replace("\n", "\\n")
                    .replace("\"", "\\\"")).append('"');
        }
        else if (value instanceof Boolean) {
            sb.append(value);
        }
        else if (value instanceof Number) {
            writeNumber(sb, (Number) value);
        }
        else if (value instanceof List<?>) {
            writeIt(sb, ((List<?>) value).iterator());
        }
        else if (value instanceof JsonNode) {
            JsonNode jsonNode = (JsonNode) value;
            switch (jsonNode.getNodeType()) {
                case ARRAY:
                    writeIt(sb, jsonNode.elements());
                    break;
                case BOOLEAN:
                    writeVar(sb, jsonNode.asBoolean());
                    break;
                case BINARY:
                case NUMBER:
                    writeVar(sb, jsonNode.asDouble());
                    break;
                case STRING:
                    writeVar(sb, jsonNode.asText());
                    break;
                case OBJECT:
                case POJO:
                    writeObj(sb, jsonNode);
                    break;
                case MISSING:
                case NULL:
                    break;
            }

        }
    }

    public static void writeIt(StringBuilder sb, Iterator<?> it) {
        sb.append('[');
        if (it.hasNext()) writeVar(sb, it.next());
        while (it.hasNext()) {
            sb.append(", ");
            writeVar(sb, it.next());
        }
        sb.append(']');
    }

    public static void writeObj(StringBuilder sb, JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        sb.append("{ ");
        if (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            sb.append('"').append(next.getKey()).append("\": ");
            writeVar(sb, next.getValue());
        }
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            writeVar(sb, next.getKey(), next.getValue());
        }
        sb.append(" }");
    }

    public static void writeNumber(StringBuilder sb, Number number) {
        if (number instanceof Long || number instanceof Integer || number instanceof Byte) {
            sb.append(number);
        }
        else if (number instanceof Double || number instanceof Float) {
            double doubleNumber = number instanceof Double ? (double) number :
                    (float) number;
            long longNumber = (long) doubleNumber;
            if(NumberUtil.fuzzyEquals(doubleNumber, longNumber)) {
                sb.append(longNumber);
            } else {
                sb.append(String.format("%.6f", doubleNumber));
            }
        }
        else {
            throw new IllegalStateException("Unknown number type : (" + number + ")" + number.getClass().getSimpleName());
        }
    }

}
