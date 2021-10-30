package io.luxus.lib.adofai.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;
import java.util.function.Function;

public class StringJsonUtil {

    public static String fixJsonString(String jsonStr) {
        StringBuilder sb = new StringBuilder(jsonStr.length());

        Stack<Boolean> isListStack = new Stack<>();
        boolean hasPrevObject = false;
        boolean keyMode = true;

        char[] chars = jsonStr.toCharArray();
        for (int idx = 0;idx < chars.length; idx++) {
            char c = chars[idx];

            if (c == '{' || c == '[') {
                if (hasPrevObject && keyMode) {
                    sb.append(',');
                }
                isListStack.push(c == '[');

                sb.append(c);
                hasPrevObject = false;
                keyMode = true;
            }
            else if (c == ':') {
                keyMode = false;
                sb.append(c);
            }
            else if (c == '}' || c == ']') {
                isListStack.pop();
                hasPrevObject = true;
                keyMode = true;
                sb.append(c);
            }
            else if (c == '"') {
                // write a string value

                if (hasPrevObject && (keyMode || isListStack.peek())) {
                    sb.append(',');
                }

                boolean escape = false;
                sb.append(c);

                for (idx++; idx < chars.length; idx++) {
                    char strC = chars[idx];
                    sb.append(strC);
                    if (strC == '\\') {
                        escape = true;
                    }
                    else if (!escape && strC == '"') {
                        break; // string end
                    }
                    else {
                        escape = false;
                    }
                }

                hasPrevObject = true;
                keyMode = !keyMode;
            }
            else if (c != ',' && c != ' ' && c != '\t' && c != '\n') {
                // write a value

                if (hasPrevObject && (keyMode || isListStack.peek())) {
                    sb.append(',');
                }

                sb.append(c);

                for (idx++; idx < chars.length; idx++) {
                    char valC = chars[idx];
                    if (valC == '{' || valC == '[' || valC == '}' || valC == ']' ||
                            valC == ',' || valC == ' ' || valC == '\t' || valC == '\n') {
                        idx--;
                        break;
                    }
                    sb.append(valC);
                }

                hasPrevObject = true;
                keyMode = !keyMode;
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
            Optional<T> o = mapper.apply(optional);
            if (node != null && !o.isPresent()) {
                map.put(name, node);
            }
            return o;
        } catch (Throwable t) {
            map.put(name, node);
            throw t;
        }
    }

    public static <R> Function<JsonNode, List<R>> nodeToXYListFunc(Function<? super JsonNode, ? extends R> mapper) {
        return node -> {
            List<R> result = nodeToList(node, mapper);
            if (result.size() == 0) {
                R value = mapper.apply(node);
                if (value == null) return null;
                result.add(value);
                result.add(value);
            }
            else if (result.size() == 1) {
                result.add(result.get(0));
            }
            else if (result.size() > 2) {
                throw new IllegalStateException("property is more than 2");
            }
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

    public static <T> void writeVar(StringBuilder sb, String name, T value, Function<T, ?> mapper) {
        Optional<?> o = Optional.ofNullable(value)
                .map(mapper);
        if (!o.isPresent()) return;

        sb.append(", \"").append(name).append("\": ");
        writeVar(sb, o.get());
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
