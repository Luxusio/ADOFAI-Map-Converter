package io.luxus.lib.adofai.util;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.type.JsonParsable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringJsonUtil {

    public static String fixJsonString(String jsonStr) {
        StringBuilder sb = new StringBuilder(jsonStr.length());

        Stack<Boolean> isListStack = new Stack<>();
        boolean hasPrevObject = false;
        boolean keyMode = true;

        char[] chars = jsonStr.toCharArray();
        for (int idx = 0; idx < chars.length; idx++) {
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
                        escape = !escape;
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

//    public static <R> Function<JsonNode, List<R>> nodeToXYListFunc(Function<? super JsonNode, ? extends R> mapper) {
//        return node -> {
//            List<R> result = nodeToList(node, mapper);
//            if (result.size() == 0) {
//                R value = mapper.apply(node);
//                if (value == null) return null;
//                result.add(value);
//                result.add(value);
//            }
//            else if (result.size() == 1) {
//                result.add(result.get(0));
//            }
//            else if (result.size() > 2) {
//                throw new IllegalStateException("property is more than 2");
//            }
//            return result;
//        };
//    }

    public static <R> Function<JsonNode, Pair<R, R>> nodeToXYPair(Function<? super JsonNode, ? extends R> mapper) {
        return node -> {
            List<R> result = nodeToList(node, mapper);
            if (result.size() == 0) {
                R value = mapper.apply(node);
                if (value == null) return null;
                return Pair.with(value, value);
            }
            else if (result.size() == 1) {
                R value = result.get(0);
                return Pair.with(value, value);
            }
            else if (result.size() > 2) {
                throw new IllegalStateException("property is more than 2");
            }
            return Pair.with(result.get(0), result.get(1));
        };
    }

    public static <T1, T2> Function<JsonNode, Pair<T1, T2>> nodeToPair(Function<? super JsonNode, ? extends T1> mapper1, Function<? super JsonNode, ? extends T2> mapper2) {
        return node -> {
            List<JsonNode> list = new ArrayList<>();

            Iterator<JsonNode> it = node.elements();
            while (it.hasNext()) {
                list.add(it.next());
            }

            if (list.size() != 2) {
                throw new IllegalStateException("property is not 2 (size=" + list.size() + ")");
            }

            return Pair.with(
                    mapper1.apply(list.get(0)),
                    mapper2.apply(list.get(1))
            );
        };
    }

    public static <T1, T2> Function<JsonNode, T2> chain(Function<JsonNode, T1> mapper1, Function<T1, T2> mapper2) {
        return node -> Optional.ofNullable(node)
                .map(mapper1)
                .map(mapper2)
                .orElse(null);
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

    public static void writeVar(StringBuilder sb, String name, JsonParsable value) {
        if (value == null) return;
        writeVar(sb, name, value.getJsonName());
    }

    public static void endWriteObj(StringBuilder sb) {
        sb.append(" }");
    }

    public static void writeVar(StringBuilder sb, Object value) {
        if (value instanceof String) {
            sb.append('"').append(((String) value)
                    .replace("\\", "\\\\")
                    .replace("\n", "\\n")
                    .replace("\"", "\\\"")).append('"');
        }
        else if (value instanceof Boolean) {
            sb.append(value);
        }
        else if (value instanceof Number) {
            sb.append(toCompactString((Number) value));
        }
        else if (value instanceof Pair<?, ?>) {
            writeIt(sb, ((Pair<?, ?>) value).iterator());
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
        else if (value instanceof JsonParsable) {
            writeVar(sb, ((JsonParsable) value).getJsonName());
        }
        else {
            throw new IllegalArgumentException("unsupported type (class=" + value.getClass() + ", value=" + value + ")");
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

    public static String toCompactString(Number number) {
        String formatNumber = String.format("%.6f", number.doubleValue());

        int subStringTo = formatNumber.length();

        // find not 0 index
        for (; subStringTo >= 1; subStringTo--) {
            char c = formatNumber.charAt(subStringTo - 1);
            if (c != '0') {
                break;
            }
        }

        // remove . if there's no decimal point
        if (formatNumber.charAt(subStringTo - 1) == '.') {
            subStringTo--;
        }

        return formatNumber.substring(0, subStringTo);
    }

    public static boolean isRGBCode(String color) {
        return true;
    }

    public static boolean isARGBCode(String color) {
        return true;
    }

}
