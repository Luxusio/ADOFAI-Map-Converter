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
    public static final Map<String, GameSound> gameSoundMap = getMap(GameSound.values(), GameSound::getJsonName);
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

        map.remove("floor").asLong();
        EventType eventType = eventTypeMap.getOrDefault(map.remove("eventType").asText(), EventType.UNKNOWN);

        Action action = null;
        switch (eventType) {
            case SET_SPEED: {
                action = new SetSpeed(
                        readProperty(map, "speedType", JsonNode::asText, getOrThrowFunc(speedTypeMap)),
                        readProperty(map, "beatsPerMinute", JsonNode::asDouble),
                        readProperty(map, "bpmMultiplier", JsonNode::asDouble));
                break;
            }
            case TWIRL: {
                action = new Twirl();
                break;
            }
            case BOOKMARK: {
                action = new Bookmark();
                break;
            }
            case CHECK_POINT: {
                action = new Checkpoint();
                break;
            }
            case EDITOR_COMMENT: {
                action = new EditorComment(
                        readProperty(map, "comment", JsonNode::asText));
                break;
            }
            case CUSTOM_BACKGROUND: {
                action = new CustomBackground(
                        readProperty(map, "color", JsonNode::asText),
                        readProperty(map, "bgImage", JsonNode::asText),
                        readProperty(map, "imageColor", JsonNode::asText),
                        readProperty(map, "parallax", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "bgDisplayMode", JsonNode::asText, getOrThrowFunc(bgDisplayModeTypeMap)),
                        readProperty(map, "lockRot", JsonNode::asText, getOrThrowFunc(toggleMap)),
                        readProperty(map, "loopBG", JsonNode::asText, getOrThrowFunc(toggleMap)),
                        readProperty(map, "unscaledSize", JsonNode::asLong),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText)
                );
                break;
            }
            case COLOR_TRACK: {
                action = new ColorTrack(
                        readProperty(map, "trackColorType", JsonNode::asText, getOrThrowFunc(trackColorTypeMap)),
                        readProperty(map, "trackColor", JsonNode::asText),
                        readProperty(map, "secondaryTrackColor", JsonNode::asText),
                        readProperty(map, "trackColorAnimDuration", JsonNode::asDouble),
                        readProperty(map, "trackColorPulse", JsonNode::asText, getOrThrowFunc(trackColorPulseMap)),
                        readProperty(map, "trackPulseLength", JsonNode::asLong),
                        readProperty(map, "trackStyle", JsonNode::asText, getOrThrowFunc(trackStyleMap)),
                        readProperty(map, "trackTexture", JsonNode::asText),
                        readProperty(map, "trackTextureScale", JsonNode::asDouble));
                break;
            }
            case ANIMATE_TRACK: {
                action = new AnimateTrack(
                        readProperty(map, "trackAnimation", JsonNode::asText, getOrThrowFunc(trackAnimationMap)),
                        readProperty(map, "beatsAhead", JsonNode::asDouble),
                        readProperty(map, "trackDisappearAnimation", JsonNode::asText, getOrThrowFunc(trackDisappearAnimationMap)),
                        readProperty(map, "beatsBehind", JsonNode::asDouble));
                break;
            }
            case ADD_DECORATION: {
                action = new AddDecoration(
                        readProperty(map, "decorationImage", JsonNode::asText),
                        readProperty(map, "position", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "relativeTo", JsonNode::asText, getOrThrowFunc(decorationRelativeToMap)),
                        readProperty(map, "pivotOffset", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "rotation", JsonNode::asDouble),
                        readProperty(map, "scale", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "tile", nodeToXYListFunc(JsonNode::asLong)),
                        readProperty(map, "color", JsonNode::asText),
                        readProperty(map, "opacity", JsonNode::asLong),
                        readProperty(map, "depth", JsonNode::asLong),
                        readProperty(map, "parallax", JsonNode::asLong),
                        readProperty(map, "tag", JsonNode::asText),
                        readProperty(map, "components", JsonNode::asText));
                break;
            }
            case FLASH: {
                action = new Flash(
                        readProperty(map, "duration", JsonNode::asDouble),
                        readProperty(map, "plane", JsonNode::asText, getOrThrowFunc(planeMap)),
                        readProperty(map, "startColor", JsonNode::asText),
                        readProperty(map, "startOpacity", JsonNode::asLong),
                        readProperty(map, "endColor", JsonNode::asText),
                        readProperty(map, "endOpacity", JsonNode::asLong),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "ease", JsonNode::asText, getOrThrowFunc(easeMap)),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case MOVE_CAMERA: {
                action = new MoveCamera(
                        readProperty(map, "duration", JsonNode::asDouble),
                        readProperty(map, "relativeTo", JsonNode::asText, getOrThrowFunc(cameraRelativeToMap)),
                        readProperty(map, "position", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "rotation", JsonNode::asDouble),
                        readProperty(map, "zoom", JsonNode::asLong),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "ease", JsonNode::asText, getOrThrowFunc(easeMap)),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case SET_HITSOUND: {
                action = new SetHitsound(
                        readProperty(map, "gameSound", JsonNode::asText, getOrThrowFunc(gameSoundMap)),
                        readProperty(map, "hitsound", JsonNode::asText, getOrThrowFunc(hitSoundMap)),
                        readProperty(map, "hitsoundVolume", JsonNode::asLong));
                break;
            }
            case PLAY_SOUND: {
                action = new PlaySound(
                        readProperty(map, "hitsound", JsonNode::asText, getOrThrowFunc(hitSoundMap)),
                        readProperty(map, "hitsoundVolume", JsonNode::asLong),
                        readProperty(map, "angleOffset", JsonNode::asDouble));
                break;
            }
            case RECOLOR_TRACK: {
                List<Object> startTile = readProperty(map, "startTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())));
                List<Object> endTile = readProperty(map, "endTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())));

                action = new RecolorTrack(
                        startTile != null ? (Long) startTile.get(0) : null,
                        startTile != null ? (TilePosition) startTile.get(1) : null,
                        endTile != null ? (Long) endTile.get(0) : null,
                        endTile != null ? (TilePosition) endTile.get(1) : null,
                        readProperty(map, "trackColorType", JsonNode::asText, getOrThrowFunc(trackColorTypeMap)),
                        readProperty(map, "trackColor", JsonNode::asText),
                        readProperty(map, "secondaryTrackColor", JsonNode::asText),
                        readProperty(map, "trackColorAnimDuration", JsonNode::asDouble),
                        readProperty(map, "trackColorPulse", JsonNode::asText, getOrThrowFunc(trackColorPulseMap)),
                        readProperty(map, "trackPulseLength", JsonNode::asLong),
                        readProperty(map, "trackStyle", JsonNode::asText, getOrThrowFunc(trackStyleMap)),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case MOVE_TRACK: {
                List<Object> startTile = readProperty(map, "startTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())));
                List<Object> endTile = readProperty(map, "endTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())));

                action = new MoveTrack(
                        startTile != null ? (Long) startTile.get(0) : null,
                        startTile != null ? (TilePosition) startTile.get(1) : null,
                        endTile != null ? (Long) endTile.get(0) : null,
                        endTile != null ? (TilePosition) endTile.get(1) : null,
                        readProperty(map, "duration", JsonNode::asDouble),
                        readProperty(map, "positionOffset", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "rotationOffset", JsonNode::asDouble),
                        readProperty(map, "scale", nodeToXYListFunc(JsonNode::asLong)),
                        readProperty(map, "opacity", JsonNode::asLong),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "ease", JsonNode::asText, getOrThrowFunc(easeMap)),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case SET_FILTER: {
                action = new SetFilter(
                        readProperty(map, "filter", JsonNode::asText, getOrThrowFunc(filterMap)),
                        readProperty(map, "enabled", JsonNode::asText, getOrThrowFunc(toggleMap)),
                        readProperty(map, "intensity", JsonNode::asLong),
                        readProperty(map, "disableOthers", JsonNode::asText, getOrThrowFunc(toggleMap)),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case HALL_OF_MIRRORS: {
                action = new HallOfMirrors(
                        readProperty(map, "enabled", JsonNode::asText, getOrThrowFunc(toggleMap)),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case SHAKE_SCREEN: {
                action = new ShakeScreen(
                        readProperty(map, "duration", JsonNode::asDouble),
                        readProperty(map, "strength", JsonNode::asLong),
                        readProperty(map, "intensity", JsonNode::asLong),
                        readProperty(map, "fadeOut", JsonNode::asText, getOrThrowFunc(toggleMap)),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case SET_PLANET_ROTATION: {
                action = new SetPlanetRotation(
                        readProperty(map, "ease", JsonNode::asText, getOrThrowFunc(easeMap)),
                        readProperty(map, "easeParts", JsonNode::asLong));
                break;
            }
            case MOVE_DECORATIONS: {
                action = new MoveDecorations(
                        readProperty(map, "duration", JsonNode::asDouble),
                        readProperty(map, "tag", JsonNode::asText),
                        readProperty(map, "positionOffset", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "rotationOffset", JsonNode::asDouble),
                        readProperty(map, "scale", nodeToXYListFunc(JsonNode::asLong)),
                        readProperty(map, "color", JsonNode::asText),
                        readProperty(map, "opacity", JsonNode::asLong),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "ease", JsonNode::asText, getOrThrowFunc(easeMap)),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case POSITION_TRACK: {
                action = new PositionTrack(
                        readProperty(map, "positionOffset", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "editorOnly", JsonNode::asText, getOrThrowFunc(toggleMap)));
                break;
            }
            case REPEAT_EVENTS: {
                action = new RepeatEvents(
                        readProperty(map, "repetitions", JsonNode::asLong),
                        readProperty(map, "interval", JsonNode::asDouble),
                        readProperty(map, "tag", JsonNode::asText));
                break;
            }
            case BLOOM: {
                action = new Bloom(
                        readProperty(map, "enabled", JsonNode::asText, getOrThrowFunc(toggleMap)),
                        readProperty(map, "threshold", JsonNode::asLong),
                        readProperty(map, "intensity", JsonNode::asLong),
                        readProperty(map, "color", JsonNode::asText),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case SET_CONDITIONAL_EVENTS: {
                action = new SetConditionalEvents(
                        readProperty(map, "perfectTag", JsonNode::asText),
                        readProperty(map, "hitTag", JsonNode::asText),
                        readProperty(map, "barelyTag", JsonNode::asText),
                        readProperty(map, "missTag", JsonNode::asText),
                        readProperty(map, "lossTag", JsonNode::asText));
                break;
            }
            case SCREEN_TILE: {
                action = new ScreenTile(
                        readProperty(map, "tile", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case SCREEN_SCROLL: {
                action = new ScreenScroll(
                        readProperty(map, "scroll", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case ADD_TEXT: {
                action = new AddText(
                        readProperty(map, "decText", JsonNode::asText),
                        readProperty(map, "font", JsonNode::asText, getOrThrowFunc(fontMap)),
                        readProperty(map, "position", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "relativeTo", JsonNode::asText, getOrThrowFunc(decorationRelativeToMap)),
                        readProperty(map, "pivotOffset", nodeToXYListFunc(JsonNode::asDouble)),
                        readProperty(map, "rotation", JsonNode::asDouble),
                        readProperty(map, "scale", nodeToXYListFunc(JsonNode::asLong)),
                        readProperty(map, "color", JsonNode::asText),
                        readProperty(map, "opacity", JsonNode::asLong),
                        readProperty(map, "depth", JsonNode::asLong),
                        readProperty(map, "parallax", JsonNode::asLong),
                        readProperty(map, "tag", JsonNode::asText));
                break;
            }
            case SET_TEXT: {
                action = new SetText(
                        readProperty(map, "decText", JsonNode::asText),
                        readProperty(map, "tag", JsonNode::asText),
                        readProperty(map, "angleOffset", JsonNode::asDouble),
                        readProperty(map, "eventTag", JsonNode::asText));
                break;
            }
            case CHANGE_TRACK: {
                action = new ChangeTrack(
                        readProperty(map, "trackColorType", JsonNode::asText, getOrThrowFunc(trackColorTypeMap)),
                        readProperty(map, "trackColor", JsonNode::asText),
                        readProperty(map, "secondaryTrackColor", JsonNode::asText),
                        readProperty(map, "trackColorAnimDuration", JsonNode::asDouble),
                        readProperty(map, "trackColorPulse", JsonNode::asText, getOrThrowFunc(trackColorPulseMap)),
                        readProperty(map, "trackPulseLength", JsonNode::asLong),
                        readProperty(map, "trackStyle", JsonNode::asText, getOrThrowFunc(trackStyleMap)),
                        readProperty(map, "trackAnimation", JsonNode::asText, getOrThrowFunc(trackAnimationMap)),
                        readProperty(map, "beatsAhead", JsonNode::asDouble),
                        readProperty(map, "trackDisappearAnimation", JsonNode::asText, getOrThrowFunc(trackDisappearAnimationMap)),
                        readProperty(map, "beatsBehind", JsonNode::asDouble));
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

    public static void write(StringBuilder sb, long floor, Action action) {

        startWrite(sb, floor, action.getEventType() != EventType.UNKNOWN ?
                action.getEventType().getJsonName() :
                ((UnknownAction) action).getRawData().get("eventType").asText());

        switch (action.getEventType()) {
            case SET_SPEED: {
                SetSpeed e = (SetSpeed) action;
                writeVar(sb, "speedType", e.getSpeedType(), SpeedType::getJsonName);
                writeVar(sb, "beatsPerMinute", e.getBeatsPerMinute());
                writeVar(sb, "bpmMultiplier", e.getBpmMultiplier());
                break;
            }
            case TWIRL:
            case BOOKMARK:
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
                writeVar(sb, "bgDisplayMode", e.getBgDisplayMode(), BGDisplayModeType::getJsonName);
                writeVar(sb, "lockRot", e.getLockRot(), Toggle::getJsonName);
                writeVar(sb, "loopBG", e.getLoopBG(), Toggle::getJsonName);
                writeVar(sb, "unscaledSize", e.getUnscaledSize());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case COLOR_TRACK: {
                ColorTrack e = (ColorTrack) action;
                writeVar(sb, "trackColorType", e.getTrackColorType(), TrackColorType::getJsonName);
                writeVar(sb, "trackColor", e.getTrackColor());
                writeVar(sb, "secondaryTrackColor", e.getSecondaryTrackColor());
                writeVar(sb, "trackColorAnimDuration", e.getTrackColorAnimDuration());
                writeVar(sb, "trackColorPulse", e.getTrackColorPulse(), TrackColorPulse::getJsonName);
                writeVar(sb, "trackPulseLength", e.getTrackPulseLength());
                writeVar(sb, "trackStyle", e.getTrackStyle(), TrackStyle::getJsonName);
                writeVar(sb, "trackTexture", e.getTrackTexture());
                writeVar(sb, "trackTextureScale", e.getTrackTextureScale());
                break;
            }
            case ANIMATE_TRACK: {
                AnimateTrack e = (AnimateTrack) action;
                writeVar(sb, "trackAnimation", e.getTrackAnimation(), TrackAnimation::getJsonName);
                writeVar(sb, "beatsAhead", e.getBeatsAhead());
                writeVar(sb, "trackDisappearAnimation", e.getTrackDisappearAnimation(), TrackDisappearAnimation::getJsonName);
                writeVar(sb, "beatsBehind", e.getBeatsBehind());
                break;
            }
            case ADD_DECORATION: {
                AddDecoration e = (AddDecoration) action;
                writeVar(sb, "decorationImage", e.getDecorationImage());
                writeVar(sb, "position", e.getPosition());
                writeVar(sb, "relativeTo", e.getDecorationRelativeTo(), DecorationRelativeTo::getJsonName);
                writeVar(sb, "pivotOffset", e.getPivotOffset());
                writeVar(sb, "rotation", e.getRotation());
                writeVar(sb, "scale", e.getScale());
                writeVar(sb, "tile", e.getTile());
                writeVar(sb, "color", e.getColor());
                writeVar(sb, "opacity", e.getOpacity());
                writeVar(sb, "depth", e.getDepth());
                writeVar(sb, "parallax", e.getParallax());
                writeVar(sb, "tag", e.getTag());
                writeVar(sb, "components", e.getComponents());
                break;
            }
            case FLASH: {
                Flash e = (Flash) action;
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "plane", e.getPlane(), Plane::getJsonName);
                writeVar(sb, "startColor", e.getStartColor());
                writeVar(sb, "startOpacity", e.getStartOpacity());
                writeVar(sb, "endColor", e.getEndColor());
                writeVar(sb, "endOpacity", e.getEndOpacity());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "ease", e.getEase(), Ease::getJsonName);
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case MOVE_CAMERA: {
                MoveCamera e = (MoveCamera) action;
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "relativeTo", e.getRelativeTo(), CameraRelativeTo::getJsonName);
                writeVar(sb, "position", e.getPosition());
                writeVar(sb, "rotation", e.getRotation());
                writeVar(sb, "zoom", e.getZoom());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "ease", e.getEase(), Ease::getJsonName);
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SET_HITSOUND: {
                SetHitsound e = (SetHitsound) action;
                writeVar(sb, "gameSound", e.getGameSound(), GameSound::getJsonName);
                writeVar(sb, "hitsound", e.getHitsound(), HitSound::getJsonName);
                writeVar(sb, "hitsoundVolume", e.getHitsoundVolume());
                break;
            }
            case PLAY_SOUND: {
                PlaySound e = (PlaySound) action;
                writeVar(sb, "hitsound", e.getHitsound(), HitSound::getJsonName);
                writeVar(sb, "hitsoundVolume", e.getHitsoundVolume());
                writeVar(sb, "angleOffset", e.getAngleOffset());
                break;
            }
            case RECOLOR_TRACK: {
                RecolorTrack e = (RecolorTrack) action;
                writeVar(sb, "startTile", Arrays.asList(e.getStartTileNum(), e.getStartTilePosition().getJsonName()));
                writeVar(sb, "endTile", Arrays.asList(e.getEndTileNum(), e.getEndTilePosition().getJsonName()));
                writeVar(sb, "trackColorType", e.getTrackColorType(), TrackColorType::getJsonName);
                writeVar(sb, "trackColor", e.getTrackColor());
                writeVar(sb, "secondaryTrackColor", e.getSecondaryTrackColor());
                writeVar(sb, "trackColorAnimDuration", e.getTrackColorAnimDuration());
                writeVar(sb, "trackColorPulse", e.getTrackColorPulse(), TrackColorPulse::getJsonName);
                writeVar(sb, "trackPulseLength", e.getTrackPulseLength());
                writeVar(sb, "trackStyle", e.getTrackStyle(), TrackStyle::getJsonName);
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
                writeVar(sb, "ease", e.getEase(), Ease::getJsonName);
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SET_FILTER: {
                SetFilter e = (SetFilter) action;
                writeVar(sb, "filter", e.getFilter(), Filter::getJsonName);
                writeVar(sb, "enabled", e.getEnabled(), Toggle::getJsonName);
                writeVar(sb, "intensity", e.getIntensity());
                writeVar(sb, "disableOthers", e.getDisableOthers(), Toggle::getJsonName);
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case HALL_OF_MIRRORS: {
                HallOfMirrors e = (HallOfMirrors) action;
                writeVar(sb, "enabled", e.getEnabled(), Toggle::getJsonName);
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SHAKE_SCREEN: {
                ShakeScreen e = (ShakeScreen) action;
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "strength", e.getStrength());
                writeVar(sb, "intensity", e.getIntensity());
                writeVar(sb, "fadeOut", e.getFadeOut(), Toggle::getJsonName);
                writeVar(sb, "angleOffset", e.getAngleOffset());
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case SET_PLANET_ROTATION: {
                SetPlanetRotation e = (SetPlanetRotation) action;
                writeVar(sb, "ease", e.getEase(), Ease::getJsonName);
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
                writeVar(sb, "ease", e.getEase(), Ease::getJsonName);
                writeVar(sb, "eventTag", e.getEventTag());
                break;
            }
            case POSITION_TRACK: {
                PositionTrack e = (PositionTrack) action;
                writeVar(sb, "positionOffset", e.getPositionOffset());
                writeVar(sb, "editorOnly", e.getEditorOnly(), Toggle::getJsonName);
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
                writeVar(sb, "enabled", e.getEnabled(), Toggle::getJsonName);
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
                writeVar(sb, "font", e.getFont(), Font::getJsonName);
                writeVar(sb, "position", e.getPosition());
                writeVar(sb, "relativeTo", e.getRelativeTo(), DecorationRelativeTo::getJsonName);
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
                writeVar(sb, "trackColorType", e.getTrackColorType(), TrackColorType::getJsonName);
                writeVar(sb, "trackColor", e.getTrackColor());
                writeVar(sb, "secondaryTrackColor", e.getSecondaryTrackColor());
                writeVar(sb, "trackColorAnimDuration", e.getTrackColorAnimDuration());
                writeVar(sb, "trackColorPulse", e.getTrackColorPulse(), TrackColorPulse::getJsonName);
                writeVar(sb, "trackPulseLength", e.getTrackPulseLength());
                writeVar(sb, "trackStyle", e.getTrackStyle(), TrackStyle::getJsonName);
                writeVar(sb, "trackAnimation", e.getTrackAnimation(), TrackAnimation::getJsonName);
                writeVar(sb, "beatsAhead", e.getBeatsAhead());
                writeVar(sb, "trackDisappearAnimation", e.getTrackDisappearAnimation(), TrackDisappearAnimation::getJsonName);
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