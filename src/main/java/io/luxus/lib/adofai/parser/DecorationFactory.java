package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.decoration.*;
import io.luxus.lib.adofai.helper.JsonPropertyReader;
import io.luxus.lib.adofai.type.action.Action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static io.luxus.lib.adofai.parser.ActionFactory.*;
import static io.luxus.lib.adofai.util.StringJsonUtil.*;

public class DecorationFactory {

    public static final Map<String, DecorationType> decorationTypeMap = getMap(DecorationType.class);


    public static Decoration read(JsonNode node) {
        Map<String, JsonNode> map = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> it = node.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> field = it.next();
            map.put(field.getKey(), field.getValue());
        }

        Decoration action = null;
        try {
            action = read(map);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (action == null) {
            System.err.println("Failed to read action rest=" + map + ", " + node);
            action = new UnknownDecoration(node);
        }

        return action;
    }

    private static Decoration read(Map<String, JsonNode> map) {
        JsonPropertyReader reader = new JsonPropertyReader(map);

        DecorationType eventType = reader
                .read("eventType", o -> o
                        .map(JsonNode::asText)
                        .map(decorationTypeMap::get))
                .orElse(DecorationType.UNKNOWN);

        Decoration decoration = null;
        switch (eventType) {
            case ADD_DECORATION: {
                AddDecoration.Builder builder = new AddDecoration.Builder();
                reader.read("floor", builder::floor, JsonNode::asLong);
                reader.read("visible", builder::visible, JsonNode::asBoolean);
                reader.read("decorationImage", builder::decorationImage, JsonNode::asText);
                reader.read("position", builder::position, nodeToXYPair(JsonNode::asDouble));
                reader.read("relativeTo", builder::relativeTo, JsonNode::asText, decorationRelativeToMap::get);
                reader.read("pivotOffset", builder::pivotOffset, nodeToXYPair(JsonNode::asDouble));
                reader.read("rotation", builder::rotation, JsonNode::asDouble);
                reader.read("scale", builder::scale, nodeToXYPair(JsonNode::asDouble));
                reader.read("tile", builder::tile, nodeToXYPair(JsonNode::asLong));
                reader.read("color", builder::color, JsonNode::asText);
                reader.read("opacity", builder::opacity, JsonNode::asDouble);
                reader.read("depth", builder::depth, JsonNode::asLong);
                reader.read("parallax", builder::parallax, nodeToXYPair(JsonNode::asDouble));
                reader.read("tag", builder::tag, JsonNode::asText);
                reader.read("imageSmoothing", builder::imageSmoothing, JsonNode::asText, toggleMap::get);
                reader.read("failHitbox", builder::failHitbox, JsonNode::asText, toggleMap::get);
                reader.read("components", builder::components, JsonNode::asText);

                decoration = builder.build();
                break;
            }
            case ADD_TEXT: {
                AddText.Builder builder = new AddText.Builder();
                reader.read("floor", builder::floor, JsonNode::asLong);
                reader.read("visible", builder::visible, JsonNode::asBoolean);
                reader.read("decText", builder::decText, JsonNode::asText);
                reader.read("decText", builder::decText, JsonNode::asText);
                reader.read("font", builder::font, JsonNode::asText, fontMap::get);
                reader.read("position", builder::position, nodeToXYPair(JsonNode::asDouble));
                reader.read("relativeTo", builder::relativeTo, JsonNode::asText, decorationRelativeToMap::get);
                reader.read("pivotOffset", builder::pivotOffset, nodeToXYPair(JsonNode::asDouble));
                reader.read("rotation", builder::rotation, JsonNode::asDouble);
                reader.read("scale", builder::scale, nodeToXYPair(JsonNode::asDouble));
                reader.read("color", builder::color, JsonNode::asText);
                reader.read("opacity", builder::opacity, JsonNode::asDouble);
                reader.read("depth", builder::depth, JsonNode::asLong);
                reader.read("parallax", builder::parallax, nodeToXYPair(JsonNode::asDouble));
                reader.read("tag", builder::tag, JsonNode::asText);

                decoration = builder.build();
                break;
            }
            case UNKNOWN: {
                break;
            }
        }

        if (decoration == null || !map.isEmpty()) {
            decoration = null;
        }

        return decoration;
    }

    public static void write(StringBuilder sb, Decoration decoration) {

        startWrite(sb, decoration.getFloor(), decoration.getEventType() != DecorationType.UNKNOWN ?
                decoration.getEventType().getJsonName() :
                ((UnknownDecoration) decoration).getRawData().get("eventType").asText());

        writeVar(sb, "visible", decoration.getVisible());

        switch (decoration.getEventType()) {
            case ADD_DECORATION: {
                AddDecoration e = (AddDecoration) decoration;
                writeVar(sb, "decorationImage", e.getDecorationImage());
                writeVar(sb, "position", e.getPosition());
                writeVar(sb, "relativeTo", e.getRelativeTo());
                writeVar(sb, "pivotOffset", e.getPivotOffset());
                writeVar(sb, "rotation", e.getRotation());
                writeVar(sb, "scale", e.getScale());
                writeVar(sb, "tile", e.getTile());
                writeVar(sb, "color", e.getColor());
                writeVar(sb, "opacity", e.getOpacity());
                writeVar(sb, "depth", e.getDepth());
                writeVar(sb, "parallax", e.getParallax());
                writeVar(sb, "tag", e.getTag());
                writeVar(sb, "imageSmoothing", e.getImageSmoothing());
                writeVar(sb, "failHitbox", e.getFailHitbox());
                writeVar(sb, "components", e.getComponents());
                break;
            }
            case ADD_TEXT: {
                AddText e = (AddText) decoration;
                writeVar(sb, "decText", e.getDecText());
                writeVar(sb, "font", e.getFont());
                writeVar(sb, "position", e.getPosition());
                writeVar(sb, "relativeTo", e.getRelativeTo());
                writeVar(sb, "pivotOffset", e.getPivotOffset());
                writeVar(sb, "rotation", e.getRotation());
                writeVar(sb, "scale", e.getScale());
                writeVar(sb, "color", e.getColor());
                writeVar(sb, "opacity", e.getOpacity());
                writeVar(sb, "depth", e.getDepth());
                writeVar(sb, "parallax", e.getParallax());
                writeVar(sb, "tag", e.getTag());
                break;
            }
            case UNKNOWN: {
                UnknownDecoration e = (UnknownDecoration) decoration;
                e.getRawData().fields().forEachRemaining(field -> {
                    String key = field.getKey();
                    if ("floor".equals(key) || "eventType".equals(key)) return;
                    writeVar(sb, key, field.getValue());
                });
            }
        }

        endWriteObj(sb);
    }

    public static Decoration tryConvert(Long floor, Action action) {
        switch (action.getEventType()) {
            case ADD_DECORATION: {
                io.luxus.lib.adofai.type.action.AddDecoration a = (io.luxus.lib.adofai.type.action.AddDecoration) action;
                return new AddDecoration.Builder()
                        .floor(floor)
                        .decorationImage(a.getDecorationImage())
                        .position(a.getPosition())
                        .relativeTo(a.getRelativeTo())
                        .pivotOffset(a.getPivotOffset())
                        .rotation(a.getRotation())
                        .scale(a.getScale())
                        .tile(a.getTile())
                        .color(a.getColor())
                        .opacity(a.getOpacity())
                        .depth(a.getDepth())
                        .parallax(a.getParallax())
                        .tag(a.getTag())
                        .imageSmoothing(a.getImageSmoothing())
                        .components(a.getComponents())
                        .build();
            }
            case ADD_TEXT: {
                io.luxus.lib.adofai.type.action.AddText a = (io.luxus.lib.adofai.type.action.AddText) action;
                return new AddText.Builder()
                        .decText(a.getDecText())
                        .font(a.getFont())
                        .position(a.getPosition())
                        .relativeTo(a.getRelativeTo())
                        .pivotOffset(a.getPivotOffset())
                        .rotation(a.getRotation())
                        .scale(a.getScale())
                        .color(a.getColor())
                        .opacity(a.getOpacity())
                        .depth(a.getDepth())
                        .parallax(a.getParallax())
                        .tag(a.getTag())
                        .build();
            }
        }

        return null;
    }

    private static void startWrite(StringBuilder sb, Long floor, String eventType) {
        if (floor != null) {
            startWriteObj(sb, "floor", floor);
            writeVar(sb, "eventType", eventType);
        } else {
            startWriteObj(sb, "eventType", eventType);
        }
    }

}
