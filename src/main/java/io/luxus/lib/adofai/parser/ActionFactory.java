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
            t.printStackTrace();
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
                SpeedType speedType = speedTypeMap.get(map.remove("speedType").asText());
                if (speedType == null) break;
                action = new SetSpeed(
                        speedType,
                        map.remove("beatsPerMinute").asDouble(),
                        map.remove("bpmMultiplier").asDouble());
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
                        map.remove("comment").asText());
                break;
            }
            case CUSTOM_BACKGROUND: {
                JsonNode parallax = map.remove("parallax");
                BGDisplayModeType bgDisplayModeType = bgDisplayModeTypeMap.get(map.remove("bgDisplayMode").asText());
                Toggle lockRot = toggleMap.get(map.remove("lockRot").asText());
                Toggle loopBG = toggleMap.get(map.remove("loopBG").asText());
                if (parallax == null || bgDisplayModeType == null || lockRot == null || loopBG == null) break;
                action = new CustomBackground(
                        map.remove("color").asText(),
                        map.remove("bgImage").asText(),
                        map.remove("imageColor").asText(),
                        Arrays.asList(
                                parallax.get(0).asDouble(),
                                parallax.get(1).asDouble()),
                        bgDisplayModeType,
                        lockRot,
                        loopBG,
                        map.remove("unscaledSize").asLong(),
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText()
                );
                break;
            }
            case COLOR_TRACK: {
                TrackColorType trackColorType = trackColorTypeMap.get(map.remove("trackColorType").asText());
                TrackColorPulse trackColorPulse = trackColorPulseMap.get(map.remove("trackColorPulse").asText());
                TrackStyle trackStyle = trackStyleMap.get(map.remove("trackStyle").asText());
                if (trackColorType == null || trackColorPulse == null || trackStyle == null) break;
                action = new ColorTrack(
                        trackColorType,
                        map.remove("trackColor").asText(),
                        map.remove("secondaryTrackColor").asText(),
                        map.remove("trackColorAnimDuration").asDouble(),
                        trackColorPulse,
                        map.remove("trackPulseLength").asLong(),
                        trackStyle);
                break;
            }
            case ANIMATE_TRACK: {
                TrackAnimation trackAnimation = trackAnimationMap.get(map.remove("trackAnimation").asText());
                TrackDisappearAnimation trackDisappearAnimation = trackDisappearAnimationMap.get(map.remove("trackDisappearAnimation").asText());
                if (trackAnimation == null || trackDisappearAnimation == null) break;
                action = new AnimateTrack(
                        trackAnimation,
                        map.remove("beatsAhead").asDouble(),
                        trackDisappearAnimation,
                        map.remove("beatsBehind").asDouble());
                break;
            }
            case ADD_DECORATION: {
                JsonNode position = map.remove("position");
                DecorationRelativeTo relativeTo = decorationRelativeToMap.get(map.remove("relativeTo").asText());
                JsonNode pivotOffset = map.remove("pivotOffset");
                if (position == null || relativeTo == null || pivotOffset == null) break;
                action = new AddDecoration(
                        map.remove("decorationImage").asText(),
                        Arrays.asList(
                                position.get(0).asDouble(),
                                position.get(1).asDouble()),
                        relativeTo,
                        Arrays.asList(
                                pivotOffset.get(0).asDouble(),
                                pivotOffset.get(1).asDouble()),
                        map.remove("rotation").asDouble(),
                        map.remove("scale").asLong(),
                        map.remove("color").asText(),
                        map.remove("opacity").asLong(),
                        map.remove("depth").asLong(),
                        map.remove("parallax").asLong(),
                        map.remove("tag").asText());
                break;
            }
            case FLASH: {
                Plane plane = planeMap.get(map.remove("plane").asText());
                if (plane == null) break;
                action = new Flash(
                        map.remove("duration").asDouble(),
                        plane,
                        map.remove("startColor").asText(),
                        map.remove("startOpacity").asLong(),
                        map.remove("endColor").asText(),
                        map.remove("endOpacity").asLong(),
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case MOVE_CAMERA: {
                CameraRelativeTo relativeTo = cameraRelativeToMap.get(map.remove("relativeTo").asText());
                JsonNode position = map.remove("position");
                Ease ease = easeMap.get(map.remove("ease").asText());
                if (relativeTo == null || position == null || ease == null) break;
                action = new MoveCamera(
                        map.remove("duration").asDouble(),
                        relativeTo,
                        Arrays.asList(
                                position.get(0).asDouble(),
                                position.get(1).asDouble()),
                        map.remove("rotation").asDouble(),
                        map.remove("zoom").asLong(),
                        map.remove("angleOffset").asDouble(),
                        ease,
                        map.remove("eventTag").asText());
                break;
            }
            case SET_HITSOUND: {
                HitSound hitSound = hitSoundMap.get(map.remove("hitsound").asText());
                if (hitSound == null) break;
                action = new SetHitsound(
                        hitSound,
                        map.remove("hitsoundVolume").asLong());
                break;
            }
            case RECOLOR_TRACK: {
                JsonNode startTile = map.remove("startTile");
                TilePosition startTilePosition = tilePositionMap.get(startTile.get(1).asText());
                JsonNode endTile = map.remove("endTile");
                TilePosition endTilePosition = tilePositionMap.get(endTile.get(1).asText());
                TrackColorType trackColorType = trackColorTypeMap.get(map.remove("trackColorType").asText());
                TrackColorPulse trackColorPulse = trackColorPulseMap.get(map.remove("trackColorPulse").asText());
                TrackStyle trackStyle = trackStyleMap.get(map.remove("trackStyle").asText());
                if (startTilePosition == null || endTilePosition == null || trackColorType == null || trackColorPulse == null || trackStyle == null)
                    break;
                action = new RecolorTrack(
                        startTile.get(0).asLong(),
                        startTilePosition,
                        endTile.get(0).asLong(),
                        endTilePosition,
                        trackColorType,
                        map.remove("trackColor").asText(),
                        map.remove("secondaryTrackColor").asText(),
                        map.remove("trackColorAnimDuration").asDouble(),
                        trackColorPulse,
                        map.remove("trackPulseLength").asLong(),
                        trackStyle,
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case MOVE_TRACK: {
                JsonNode startTile = map.remove("startTile");
                TilePosition startTilePosition = tilePositionMap.get(startTile.get(1).asText());
                JsonNode endTile = map.remove("endTile");
                TilePosition endTilePosition = tilePositionMap.get(endTile.get(1).asText());
                JsonNode positionOffset = map.remove("positionOffset");
                Ease ease = easeMap.get(map.remove("ease").asText());
                if (startTilePosition == null || endTilePosition == null || positionOffset == null || ease == null)
                    break;
                action = new MoveTrack(
                        startTile.get(0).asLong(),
                        startTilePosition,
                        endTile.get(0).asLong(),
                        endTilePosition,
                        map.remove("duration").asDouble(),
                        Arrays.asList(
                                positionOffset.get(0).asDouble(),
                                positionOffset.get(1).asDouble()),
                        map.remove("rotationOffset").asDouble(),
                        map.remove("scale").asLong(),
                        map.remove("opacity").asLong(),
                        map.remove("angleOffset").asDouble(),
                        ease,
                        map.remove("eventTag").asText());
                break;
            }
            case SET_FILTER: {
                Filter filter = filterMap.get(map.remove("filter").asText());
                Toggle enabled = toggleMap.get(map.remove("enabled").asText());
                Toggle disableOthers = toggleMap.get(map.remove("disableOthers").asText());
                if (filter == null || enabled == null || disableOthers == null) break;
                action = new SetFilter(
                        filter,
                        enabled,
                        map.remove("intensity").asLong(),
                        disableOthers,
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case HALL_OF_MIRRORS: {
                Toggle enabled = toggleMap.get(map.remove("enabled").asText());
                if (enabled == null) break;
                action = new HallOfMirrors(
                        enabled,
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case SHAKE_SCREEN: {
                Toggle fadeOut = toggleMap.get(map.remove("fadeOut").asText());
                if (fadeOut == null) break;
                action = new ShakeScreen(
                        map.remove("duration").asDouble(),
                        map.remove("strength").asLong(),
                        map.remove("intensity").asLong(),
                        fadeOut,
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case SET_PLANET_ROTATION: {
                Ease ease = easeMap.get(map.remove("ease").asText());
                if (ease == null) break;
                action = new SetPlanetRotation(
                        ease,
                        map.remove("easeParts").asLong());
                break;
            }
            case MOVE_DECORATIONS: {
                JsonNode positionOffset = map.remove("positionOffset");
                Ease ease = easeMap.get(map.remove("ease").asText());
                if (positionOffset == null || ease == null) break;
                action = new MoveDecorations(
                        map.remove("duration").asDouble(),
                        map.remove("tag").asText(),
                        Arrays.asList(
                                positionOffset.get(0).asDouble(),
                                positionOffset.get(1).asDouble()),
                        map.remove("rotationOffset").asDouble(),
                        map.remove("scale").asLong(),
                        map.remove("color").asText(),
                        map.remove("opacity").asLong(),
                        map.remove("angleOffset").asDouble(),
                        ease,
                        map.remove("eventTag").asText());
                break;
            }
            case POSITION_TRACK: {
                JsonNode positionOffset = map.remove("positionOffset");
                Toggle editorOnly = toggleMap.get(map.remove("editorOnly").asText());
                if (positionOffset == null || editorOnly == null) break;
                action = new PositionTrack(
                        Arrays.asList(
                                positionOffset.get(0).asDouble(),
                                positionOffset.get(1).asDouble()),
                        editorOnly);
                break;
            }
            case REPEAT_EVENTS: {
                action = new RepeatEvents(
                        map.remove("repetitions").asLong(),
                        map.remove("interval").asDouble(),
                        map.remove("tag").asText());
                break;
            }
            case BLOOM: {
                Toggle enabled = toggleMap.get(map.remove("enabled").asText());
                if (enabled == null) break;
                action = new Bloom(
                        enabled,
                        map.remove("threshold").asLong(),
                        map.remove("intensity").asLong(),
                        map.remove("color").asText(),
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case SET_CONDITIONAL_EVENTS: {
                action = new SetConditionalEvents(
                        map.remove("perfectTag").asText(),
                        map.remove("hitTag").asText(),
                        map.remove("barelyTag").asText(),
                        map.remove("missTag").asText(),
                        map.remove("lossTag").asText());
                break;
            }
            case SCREEN_TILE: {
                JsonNode tile = map.remove("tile");
                if (tile == null) break;
                action = new ScreenTile(
                        Arrays.asList(
                                tile.get(0).asDouble(),
                                tile.get(1).asDouble()),
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case SCREEN_SCROLL: {
                JsonNode scroll = map.remove("scroll");
                if (scroll == null) break;
                action = new ScreenScroll(
                        Arrays.asList(
                                scroll.get(0).asDouble(),
                                scroll.get(1).asDouble()),
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case ADD_TEXT: {
                Font font = fontMap.get(map.remove("font").asText());
                JsonNode position = map.remove("position");
                DecorationRelativeTo relativeTo = decorationRelativeToMap.get(map.remove("relativeTo").asText());
                JsonNode pivotOffset = map.remove("pivotOffset");
                if (font == null || position == null || relativeTo == null || pivotOffset == null) break;
                action = new AddText(
                        map.remove("decText").asText(),
                        font,
                        Arrays.asList(
                                position.get(0).asDouble(),
                                position.get(1).asDouble()),
                        relativeTo,
                        Arrays.asList(
                                pivotOffset.get(0).asDouble(),
                                pivotOffset.get(1).asDouble()),
                        map.remove("rotation").asDouble(),
                        map.remove("scale").asLong(),
                        map.remove("color").asText(),
                        map.remove("opacity").asLong(),
                        map.remove("depth").asLong(),
                        map.remove("parallax").asLong(),
                        map.remove("tag").asText());
                break;
            }
            case SET_TEXT: {
                action = new SetText(
                        map.remove("decText").asText(),
                        map.remove("tag").asText(),
                        map.remove("angleOffset").asDouble(),
                        map.remove("eventTag").asText());
                break;
            }
            case CHANGE_TRACK: {
                TrackColorType trackColorType = trackColorTypeMap.get(map.remove("trackColorType").asText());
                TrackColorPulse trackColorPulse = trackColorPulseMap.get(map.remove("trackColorPulse").asText());
                TrackStyle trackStyle = trackStyleMap.get(map.remove("trackStyle").asText());
                TrackAnimation trackAnimation = trackAnimationMap.get(map.remove("trackAnimation").asText());
                TrackDisappearAnimation trackDisappearAnimation = trackDisappearAnimationMap.get(map.remove("trackDisappearAnimation").asText());
                if (trackColorType == null || trackColorPulse == null || trackStyle == null || trackAnimation == null || trackDisappearAnimation == null)
                    break;
                action = new ChangeTrack(
                        trackColorType,
                        map.remove("trackColor").asText(),
                        map.remove("secondaryTrackColor").asText(),
                        map.remove("trackColorAnimDuration").asDouble(),
                        trackColorPulse,
                        map.remove("trackPulseLength").asLong(),
                        trackStyle,
                        trackAnimation,
                        map.remove("beatsAhead").asDouble(),
                        trackDisappearAnimation,
                        map.remove("beatsBehind").asDouble());
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