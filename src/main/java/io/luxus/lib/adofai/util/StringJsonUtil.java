package io.luxus.lib.adofai.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
