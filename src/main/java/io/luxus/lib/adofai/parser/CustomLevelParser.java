package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.util.StringJsonUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CustomLevelParser {

    public static CustomLevel readPath(String path) throws IOException {
        return read(new File(path));
    }

    public static CustomLevel read(File file) throws IOException {
        String jsonString = StringJsonUtil.fixJsonString(readString(file));
        return read(jsonString);
    }

    public static CustomLevel read(String jsonStr) throws IOException {
        return read(new ObjectMapper().readTree(jsonStr));
    }

    public static CustomLevel read(JsonNode node) throws IOException {
        try {
            return CustomLevelFactory.read(node);
        } catch (Throwable throwable) {
            System.err.println("Failed to read level");
            throwable.printStackTrace();
            return null;
        }
    }

    public static void write(CustomLevel customLevel, String path) throws IOException {
        write(customLevel, new File(path));
    }

    public static void write(CustomLevel customLevel, File file) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            bufferedWriter.write(65279); // BOM write
            bufferedWriter.write(CustomLevelFactory.write(customLevel));
        }
    }

    private static String readString(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String buf;

        while ((buf = reader.readLine()) != null) {
            sb.append(buf);
        }

        reader.close();

        return sb.substring(sb.indexOf("{"));
    }



}
