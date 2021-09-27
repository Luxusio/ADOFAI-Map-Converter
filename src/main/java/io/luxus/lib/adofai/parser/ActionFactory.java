package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.action.*;
import io.luxus.lib.adofai.action.type.*;

import java.util.*;
import java.util.function.Function;

import static io.luxus.lib.adofai.util.StringJsonUtil.*;

public class ActionFactory {

    public static final Map<String, EventType> eventTypeMap = getMap(EventType.values(), EventType::getJsonName);
    public static final Map<String, BGDisplayModeType> bgDisplayModeTypeMap = getMap(BGDisplayModeType.values(), BGDisplayModeType::getJsonName);
    public static final Map<String, CameraRelativeTo> cameraRelativeToMap = getMap(CameraRelativeTo.values(), CameraRelativeTo::getJsonName);
    public static final Map<String, DecorationRelativeTo> decorationRelativeToMap = getMap(DecorationRelativeTo.values(), DecorationRelativeTo::getJsonName);
    public static final Map<String, Ease> easeMap = getMap(Ease.values(), Ease::getJsonName);
    public static final Map<String, Filter> filterMap = getMap(Filter.values(), Filter::getJsonName);
    public static final Map<String, Font> fontMap = getMap(Font.values(), Font::getJsonName);
    public static final Map<String, HitSound> hitSoundMap = getMap(HitSound.values(), HitSound::getJsonName);
    public static final Map<String, Plane> planeMap = getMap(Plane.values(), Plane::getJsonName);
    public static final Map<String, SpeedType> speedTypeMap = getMap(SpeedType.values(), SpeedType::getJsonName);
    public static final Map<String, TilePosition> tilePositionMap = getMap(TilePosition.values(), TilePosition::getJsonName);
    public static final Map<String, Toggle> toggleMap = getMap(Toggle.values(), Toggle::getJsonName);
    public static final Map<String, TrackAnimation> trackAnimationMap = getMap(TrackAnimation.values(), TrackAnimation::getJsonName);
    public static final Map<String, TrackColorPulse> trackColorPulseMap = getMap(TrackColorPulse.values(), TrackColorPulse::getJsonName);
    public static final Map<String, TrackColorType> trackColorTypeMap = getMap(TrackColorType.values(), TrackColorType::getJsonName);
    public static final Map<String, TrackDisappearAnimation> trackDisappearAnimationMap = getMap(TrackDisappearAnimation.values(), TrackDisappearAnimation::getJsonName);
    public static final Map<String, TrackStyle> trackStyleMap = getMap(TrackStyle.values(), TrackStyle::getJsonName);

    private static <T> Map<String, T> getMap(T[] values, Function<T, String> stringFunction) {
        Map<String, T> map = new HashMap<>();
        for (T value : values) map.put(stringFunction.apply(value), value);
        return map;
    }

    public static Action read(JsonNode node) {
        Map<String, JsonNode> map = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> it = node.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> field = it.next();
            map.put(field.getKey(), field.getValue());
        }

        Action action = null;
        try {
            action = read(map);
        } catch (Throwable t) {
            //t.printStackTrace();
        }

        if (action == null) {
            System.err.println("Failed to read action " + node);
            action = new UnknownAction(node);
        }

        return action;
    }

    private static Action read(Map<String, JsonNode> map) {

        long floor = map.remove("floor").asLong();
        EventType eventType = eventTypeMap.getOrDefault(map.remove("eventType").asText(), EventType.UNKNOWN);

        Action action = null;
        switch (eventType) {
            case SET_SPEED: {
                action = new SetSpeed(
                        readVar(map, "speedType", JsonNode::asText, speedTypeMap::get),
                        readVar(map, "beatsPerMinute", JsonNode::asDouble),
                        readVar(map, "bpmMultiplier", JsonNode::asDouble));
                break;
            }
            case TWIRL: {
                action = new Twirl();
                break;
            }
            case CHECK_POINT: {
                action = new Checkpoint();
                break;
            }
            case EDITOR_COMMENT: {
                action = new EditorComment(
                        readVar(map, "comment", JsonNode::asText));
                break;
            }
            case CUSTOM_BACKGROUND: {
                action = new CustomBackground(
                        readVar(map, "color", JsonNode::asText),
                        readVar(map, "bgImage", JsonNode::asText),
                        readVar(map, "imageColor", JsonNode::asText),
                        readVar(map, "parallax", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "bgDisplayMode", JsonNode::asText, bgDisplayModeTypeMap::get),
                        readVar(map, "lockRot", JsonNode::asText, toggleMap::get),
                        readVar(map, "loopBG", JsonNode::asText, toggleMap::get),
                        readVar(map, "unscaledSize", JsonNode::asLong),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText)
                );
                break;
            }
            case COLOR_TRACK: {
                action = new ColorTrack(
                        readVar(map, "trackColorType", JsonNode::asText, trackColorTypeMap::get),
                        readVar(map, "trackColor", JsonNode::asText),
                        readVar(map, "secondaryTrackColor", JsonNode::asText),
                        readVar(map, "trackColorAnimDuration", JsonNode::asDouble),
                        readVar(map, "trackColorPulse", JsonNode::asText, trackColorPulseMap::get),
                        readVar(map, "trackPulseLength", JsonNode::asLong),
                        readVar(map, "trackStyle", JsonNode::asText, trackStyleMap::get));
                break;
            }
            case ANIMATE_TRACK: {
                action = new AnimateTrack(
                        readVar(map, "trackAnimation", JsonNode::asText, trackAnimationMap::get),
                        readVar(map, "beatsAhead", JsonNode::asDouble),
                        readVar(map, "trackDisappearAnimation", JsonNode::asText, trackDisappearAnimationMap::get),
                        readVar(map, "beatsBehind", JsonNode::asDouble));
                break;
            }
            case ADD_DECORATION: {
                action = new AddDecoration(
                        readVar(map, "decorationImage", JsonNode::asText),
                        readVar(map, "position", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "relativeTo", JsonNode::asText, decorationRelativeToMap::get),
                        readVar(map, "pivotOffset", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "rotation", JsonNode::asDouble),
                        readVar(map, "scale", JsonNode::asLong),
                        readVar(map, "color", JsonNode::asText),
                        readVar(map, "opacity", JsonNode::asLong),
                        readVar(map, "depth", JsonNode::asLong),
                        readVar(map, "parallax", JsonNode::asLong),
                        readVar(map, "tag", JsonNode::asText));
                break;
            }
            case FLASH: {
                action = new Flash(
                        readVar(map, "duration", JsonNode::asDouble),
                        readVar(map, "plane", JsonNode::asText, planeMap::get),
                        readVar(map, "startColor", JsonNode::asText),
                        readVar(map, "startOpacity", JsonNode::asLong),
                        readVar(map, "endColor", JsonNode::asText),
                        readVar(map, "endOpacity", JsonNode::asLong),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case MOVE_CAMERA: {
                action = new MoveCamera(
                        readVar(map, "duration", JsonNode::asDouble),
                        readVar(map, "relativeTo", JsonNode::asText, cameraRelativeToMap::get),
                        readVar(map, "position", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "rotation", JsonNode::asDouble),
                        readVar(map, "zoom", JsonNode::asLong),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "ease", JsonNode::asText, easeMap::get),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case SET_HITSOUND: {
                HitSound hitSound = hitSoundMap.get(readVar(map, "hitsound", JsonNode::asText));
                if (hitSound == null) break;
                action = new SetHitsound(
                        hitSound,
                        readVar(map, "hitsoundVolume", JsonNode::asLong));
                break;
            }
            case RECOLOR_TRACK: {
                List<Object> startTile = readVar(map, "startTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())));
                List<Object> endTile = readVar(map, "endTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())));

                action = new RecolorTrack(
                        startTile != null ? (Long) startTile.get(0) : null,
                        startTile != null ? (TilePosition) startTile.get(1) : null,
                        endTile != null ? (Long) endTile.get(0) : null,
                        endTile != null ? (TilePosition) endTile.get(1) : null,
                        readVar(map, "trackColorType", JsonNode::asText, trackColorTypeMap::get),
                        readVar(map, "trackColor", JsonNode::asText),
                        readVar(map, "secondaryTrackColor", JsonNode::asText),
                        readVar(map, "trackColorAnimDuration", JsonNode::asDouble),
                        readVar(map, "trackColorPulse", JsonNode::asText, trackColorPulseMap::get),
                        readVar(map, "trackPulseLength", JsonNode::asLong),
                        readVar(map, "trackStyle", JsonNode::asText, trackStyleMap::get),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case MOVE_TRACK: {
                List<Object> startTile = readVar(map, "startTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())));
                List<Object> endTile = readVar(map, "endTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())));

                action = new MoveTrack(
                        startTile != null ? (Long) startTile.get(0) : null,
                        startTile != null ? (TilePosition) startTile.get(1) : null,
                        endTile != null ? (Long) endTile.get(0) : null,
                        endTile != null ? (TilePosition) endTile.get(1) : null,
                        readVar(map, "duration", JsonNode::asDouble),
                        readVar(map, "positionOffset", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "rotationOffset", JsonNode::asDouble),
                        readVar(map, "scale", JsonNode::asLong),
                        readVar(map, "opacity", JsonNode::asLong),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "ease", JsonNode::asText, easeMap::get),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case SET_FILTER: {
                action = new SetFilter(
                        readVar(map, "filter", JsonNode::asText, filterMap::get),
                        readVar(map, "enabled", JsonNode::asText, toggleMap::get),
                        readVar(map, "intensity", JsonNode::asLong),
                        readVar(map, "disableOthers", JsonNode::asText, toggleMap::get),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case HALL_OF_MIRRORS: {
                action = new HallOfMirrors(
                        readVar(map, "enabled", JsonNode::asText, toggleMap::get),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case SHAKE_SCREEN: {
                action = new ShakeScreen(
                        readVar(map, "duration", JsonNode::asDouble),
                        readVar(map, "strength", JsonNode::asLong),
                        readVar(map, "intensity", JsonNode::asLong),
                        readVar(map, "fadeOut", JsonNode::asText, toggleMap::get),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case SET_PLANET_ROTATION: {
                action = new SetPlanetRotation(
                        readVar(map, "ease", JsonNode::asText, easeMap::get),
                        readVar(map, "easeParts", JsonNode::asLong));
                break;
            }
            case MOVE_DECORATIONS: {
                action = new MoveDecorations(
                        readVar(map, "duration", JsonNode::asDouble),
                        readVar(map, "tag", JsonNode::asText),
                        readVar(map, "positionOffset", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "rotationOffset", JsonNode::asDouble),
                        readVar(map, "scale", JsonNode::asLong),
                        readVar(map, "color", JsonNode::asText),
                        readVar(map, "opacity", JsonNode::asLong),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "ease", JsonNode::asText, easeMap::get),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case POSITION_TRACK: {
                action = new PositionTrack(
                        readVar(map, "positionOffset", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "editorOnly", JsonNode::asText, toggleMap::get));
                break;
            }
            case REPEAT_EVENTS: {
                action = new RepeatEvents(
                        readVar(map, "repetitions", JsonNode::asLong),
                        readVar(map, "interval", JsonNode::asDouble),
                        readVar(map, "tag", JsonNode::asText));
                break;
            }
            case BLOOM: {
                Toggle enabled = toggleMap.get(readVar(map, "enabled", JsonNode::asText));
                if (enabled == null) break;
                action = new Bloom(
                        enabled,
                        readVar(map, "threshold", JsonNode::asLong),
                        readVar(map, "intensity", JsonNode::asLong),
                        readVar(map, "color", JsonNode::asText),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case SET_CONDITIONAL_EVENTS: {
                action = new SetConditionalEvents(
                        readVar(map, "perfectTag", JsonNode::asText),
                        readVar(map, "hitTag", JsonNode::asText),
                        readVar(map, "barelyTag", JsonNode::asText),
                        readVar(map, "missTag", JsonNode::asText),
                        readVar(map, "lossTag", JsonNode::asText));
                break;
            }
            case SCREEN_TILE: {
                action = new ScreenTile(
                        readVar(map, "tile", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case SCREEN_SCROLL: {
                action = new ScreenScroll(
                        readVar(map, "scroll", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case ADD_TEXT: {
                action = new AddText(
                        readVar(map, "decText", JsonNode::asText),
                        readVar(map, "font", JsonNode::asText, fontMap::get),
                        readVar(map, "position", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "relativeTo", JsonNode::asText, decorationRelativeToMap::get),
                        readVar(map, "pivotOffset", jsonNode -> Arrays.asList(
                                jsonNode.get(0).asDouble(),
                                jsonNode.get(1).asDouble())),
                        readVar(map, "rotation", JsonNode::asDouble),
                        readVar(map, "scale", JsonNode::asLong),
                        readVar(map, "color", JsonNode::asText),
                        readVar(map, "opacity", JsonNode::asLong),
                        readVar(map, "depth", JsonNode::asLong),
                        readVar(map, "parallax", JsonNode::asLong),
                        readVar(map, "tag", JsonNode::asText));
                break;
            }
            case SET_TEXT: {
                action = new SetText(
                        readVar(map, "decText", JsonNode::asText),
                        readVar(map, "tag", JsonNode::asText),
                        readVar(map, "angleOffset", JsonNode::asDouble),
                        readVar(map, "eventTag", JsonNode::asText));
                break;
            }
            case CHANGE_TRACK: {
                action = new ChangeTrack(
                        readVar(map, "trackColorType", JsonNode::asText, trackColorTypeMap::get),
                        readVar(map, "trackColor", JsonNode::asText),
                        readVar(map, "secondaryTrackColor", JsonNode::asText),
                        readVar(map, "trackColorAnimDuration", JsonNode::asDouble),
                        readVar(map, "trackColorPulse", JsonNode::asText, trackColorPulseMap::get),
                        readVar(map, "trackPulseLength", JsonNode::asLong),
                        readVar(map, "trackStyle", JsonNode::asText, trackStyleMap::get),
                        readVar(map, "trackAnimation", JsonNode::asText, trackAnimationMap::get),
                        readVar(map, "beatsAhead", JsonNode::asDouble),
                        readVar(map, "trackDisappearAnimation", JsonNode::asText, trackDisappearAnimationMap::get),
                        readVar(map, "beatsBehind", JsonNode::asDouble));
                break;
            }
            case UNKNOWN: {
                break;
            }
        }

        if (action == null || !map.isEmpty()) {
            action = null;
        }

        return action;
    }

    private static <T> T readVar(Map<String, JsonNode> map, String name, Function<JsonNode, String> mapper, Function<String, T> mapper2) {
        return readVar(map, name, node -> {
            T result = mapper2.apply(mapper.apply(node));
            if (result == null) throw new IllegalStateException("Cannot find value(" + mapper.apply(node) + ")");
            return result;
        });
    }

    private static <T> T readVar(Map<String, JsonNode> map, String name, Function<JsonNode, T> mapper) {
        JsonNode node = map.remove(name);

        if (node == null) return null;
        return mapper.apply(node);
    }

    public static void write(StringBuilder sb, long floor, Action action) {

        startWrite(sb, floor, action.getEventType() != EventType.UNKNOWN ?
                action.getEventType().getJsonName() :
                ((UnknownAction) action).getRawData().get("eventType").asText());

        switch (action.getEventType()) {
            case SET_SPEED: {
                SetSpeed e = (SetSpeed) action;
                writeVar(sb, "speedType", e.getSpeedType().getJsonName());
                writeVar(sb, "beatsPerMinute", e.getBeatsPerMinute());
                writeVar(sb, "bpmMultiplier", e.getBpmMultiplier());
                break;
            }
            case TWIRL:
            case CHECK_POINT: {
                break;
            }
            case EDITOR_COMMENT: {
                EditorComment e = (EditorComment) action;
                writeVar(sb, "comment", e.getComment());
                break;
            }
            case CUSTOM_BACKGROUND: {
                CustomBackground e = (CustomBackground) action;
                writeVar(sb, "color", e.getColor());
                writeVar(sb, "bgImage", e.getBgImage());
                writeVar(sb, "imageColor", e.getImageColor());
                writeVar(sb, "parallax", e.getParallax());
                writeVar(sb, "bgDisplayMode", e.getBgDisplayMode().getJsonName());
                writeVar(sb, "lockRot", e.getLockRot().getJsonName());
                writeVar(sb, "loopBG", e.getLoopBG().getJsonName());
                writeVar(sb, "unscaledSize", e.getUnscaledSize());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case COLOR_TRACK: {
                ColorTrack e = (ColorTrack) action;
                writeVar(sb, "trackColorType", e.getTrackColorType().getJsonName());
                writeVar(sb, "trackColor", e.getTrackColor());
                writeVar(sb, "secondaryTrackColor", e.getSecondaryTrackColor());
                writeVar(sb, "trackColorAnimDuration", e.getTrackColorAnimDuration());
                writeVar(sb, "trackColorPulse", e.getTrackColorPulse().getJsonName());
                writeVar(sb, "trackPulseLength", e.getTrackPulseLength());
                writeVar(sb, "trackStyle", e.getTrackStyle().getJsonName());
                break;
            }
            case ANIMATE_TRACK: {
                AnimateTrack e = (AnimateTrack) action;
                writeVar(sb, "trackAnimation", e.getTrackAnimation().getJsonName());
                writeVar(sb, "beatsAhead", e.getBeatsAhead());
                writeVar(sb, "trackDisappearAnimation", e.getTrackDisappearAnimation().getJsonName());
                writeVar(sb, "beatsBehind", e.getBeatsBehind());
                break;
            }
            case ADD_DECORATION: {
                AddDecoration e = (AddDecoration) action;
                writeVar(sb, "decorationImage", e.getDecorationImage());
                writeVar(sb, "position", e.getPosition());
                writeVar(sb, "relativeTo", e.getDecorationRelativeTo().getJsonName());
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
            case FLASH: {
                Flash e = (Flash) action;
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "plane", e.getPlane().getJsonName());
                writeVar(sb, "startColor", e.getStartColor());
                writeVar(sb, "startOpacity", e.getStartOpacity());
                writeVar(sb, "endColor", e.getEndColor());
                writeVar(sb, "endOpacity", e.getEndOpacity());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case MOVE_CAMERA: {
                MoveCamera e = (MoveCamera) action;
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "relativeTo", e.getRelativeTo().getJsonName());
                writeVar(sb, "position", e.getPosition());
                writeVar(sb, "rotation", e.getRotation());
                writeVar(sb, "zoom", e.getZoom());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "ease", e.getEase().getJsonName());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SET_HITSOUND: {
                SetHitsound e = (SetHitsound) action;
                writeVar(sb, "hitsound", e.getHitsound().getJsonName());
                writeVar(sb, "hitsoundVolume", e.getHitsoundVolume());
                break;
            }
            case RECOLOR_TRACK: {
                RecolorTrack e = (RecolorTrack) action;
                writeVar(sb, "startTile", Arrays.asList(e.getStartTileNum(), e.getStartTilePosition().getJsonName()));
                writeVar(sb, "endTile", Arrays.asList(e.getEndTileNum(), e.getEndTilePosition().getJsonName()));
                writeVar(sb, "trackColorType", e.getTrackColorType().getJsonName());
                writeVar(sb, "trackColor", e.getTrackColor());
                writeVar(sb, "secondaryTrackColor", e.getSecondaryTrackColor());
                writeVar(sb, "trackColorAnimDuration", e.getTrackColorAnimDuration());
                writeVar(sb, "trackColorPulse", e.getTrackColorPulse().getJsonName());
                writeVar(sb, "trackPulseLength", e.getTrackPulseLength());
                writeVar(sb, "trackStyle", e.getTrackStyle().getJsonName());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case MOVE_TRACK: {
                MoveTrack e = (MoveTrack) action;
                writeVar(sb, "startTile", Arrays.asList(e.getStartTileNum(), e.getStartTilePosition().getJsonName()));
                writeVar(sb, "endTile", Arrays.asList(e.getEndTileNum(), e.getEndTilePosition().getJsonName()));
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "positionOffset", e.getPositionOffset());
                writeVar(sb, "rotationOffset", e.getRotationOffset());
                writeVar(sb, "scale", e.getScale());
                writeVar(sb, "opacity", e.getOpacity());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "ease", e.getEase().getJsonName());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SET_FILTER: {
                SetFilter e = (SetFilter) action;
                writeVar(sb, "filter", e.getFilter().getJsonName());
                writeVar(sb, "enabled", e.getEnabled().getJsonName());
                writeVar(sb, "intensity", e.getIntensity());
                writeVar(sb, "disableOthers", e.getDisableOthers().getJsonName());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case HALL_OF_MIRRORS: {
                HallOfMirrors e = (HallOfMirrors) action;
                writeVar(sb, "enabled", e.getEnabled().getJsonName());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SHAKE_SCREEN: {
                ShakeScreen e = (ShakeScreen) action;
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "strength", e.getStrength());
                writeVar(sb, "intensity", e.getIntensity());
                writeVar(sb, "fadeOut", e.getFadeOut().getJsonName());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SET_PLANET_ROTATION: {
                SetPlanetRotation e = (SetPlanetRotation) action;
                writeVar(sb, "ease", e.getEase().getJsonName());
                writeVar(sb, "easeParts", e.getEaseParts());
                break;
            }
            case MOVE_DECORATIONS: {
                MoveDecorations e = (MoveDecorations) action;
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "tag", e.getTag());
                writeVar(sb, "positionOffset", e.getPositionOffset());
                writeVar(sb, "rotationOffset", e.getRotationOffset());
                writeVar(sb, "scale", e.getScale());
                writeVar(sb, "color", e.getColor());
                writeVar(sb, "opacity", e.getOpacity());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "ease", e.getEase().getJsonName());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case POSITION_TRACK: {
                PositionTrack e = (PositionTrack) action;
                writeVar(sb, "positionOffset", e.getPositionOffset());
                writeVar(sb, "editorOnly", e.getEditorOnly().getJsonName());
                break;
            }
            case REPEAT_EVENTS: {
                RepeatEvents e = (RepeatEvents) action;
                writeVar(sb, "repetitions", e.getRepetitions());
                writeVar(sb, "interval", e.getInterval());
                writeVar(sb, "tag", e.getTag());
                break;
            }
            case BLOOM: {
                Bloom e = (Bloom) action;
                writeVar(sb, "enabled", e.getEnabled().getJsonName());
                writeVar(sb, "threshold", e.getThreshold());
                writeVar(sb, "intensity", e.getIntensity());
                writeVar(sb, "color", e.getColor());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SET_CONDITIONAL_EVENTS: {
                SetConditionalEvents e = (SetConditionalEvents) action;
                writeVar(sb, "perfectTag", e.getPerfectTag());
                writeVar(sb, "hitTag", e.getHitTag());
                writeVar(sb, "barelyTag", e.getBarelyTag());
                writeVar(sb, "missTag", e.getMissTag());
                writeVar(sb, "lossTag", e.getLossTag());
                break;
            }
            case SCREEN_TILE: {
                ScreenTile e = (ScreenTile) action;
                writeVar(sb, "tile", e.getTile());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SCREEN_SCROLL: {
                ScreenScroll e = (ScreenScroll) action;
                writeVar(sb, "scroll", e.getScroll());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case ADD_TEXT: {
                AddText e = (AddText) action;
                writeVar(sb, "decText", e.getDecText());
                writeVar(sb, "font", e.getFont().getJsonName());
                writeVar(sb, "position", e.getPosition());
                writeVar(sb, "relativeTo", e.getRelativeTo().getJsonName());
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
            case SET_TEXT: {
                SetText e = (SetText) action;
                writeVar(sb, "decText", e.getDecText());
                writeVar(sb, "tag", e.getTag());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case CHANGE_TRACK: {
                ChangeTrack e = (ChangeTrack) action;
                writeVar(sb, "trackColorType", e.getTrackColorType().getJsonName());
                writeVar(sb, "trackColor", e.getTrackColor());
                writeVar(sb, "secondaryTrackColor", e.getSecondaryTrackColor());
                writeVar(sb, "trackColorAnimDuration", e.getTrackColorAnimDuration());
                writeVar(sb, "trackColorPulse", e.getTrackColorPulse().getJsonName());
                writeVar(sb, "trackPulseLength", e.getTrackPulseLength());
                writeVar(sb, "trackStyle", e.getTrackStyle().getJsonName());
                writeVar(sb, "trackAnimation", e.getTrackAnimation().getJsonName());
                writeVar(sb, "beatsAhead", e.getBeatsAhead());
                writeVar(sb, "trackDisappearAnimation", e.getTrackDisappearAnimation().getJsonName());
                writeVar(sb, "beatsBehind", e.getBeatsBehind());
                break;
            }
            case UNKNOWN: {
                UnknownAction e = (UnknownAction) action;
                e.getRawData().fields().forEachRemaining(field -> {
                    String key = field.getKey();
                    if ("floor".equals(key) || "eventType".equals(key)) return;
                    writeVar(sb, key, field.getValue());
                });
            }
        }

        endWriteObj(sb);
    }

    private static void startWrite(StringBuilder sb, long floor, String eventType) {
        startWriteObj(sb, "floor", floor);
        writeVar(sb, "eventType", eventType);
    }

}