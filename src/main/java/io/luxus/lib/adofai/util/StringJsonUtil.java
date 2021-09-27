package io.luxus.lib.adofai.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.math.DoubleMath;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.luxus.lib.adofai.Constants.EPSILON;

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
            sb.append('"').append(((String) value).replace("\n", "\\n")).append('"');
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
            if(DoubleMath.fuzzyEquals(doubleNumber, longNumber, EPSILON)) {
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
