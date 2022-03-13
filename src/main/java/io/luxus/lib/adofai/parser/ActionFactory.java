package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.helper.JsonPropertyReader;
import io.luxus.lib.adofai.type.action.*;
import io.luxus.lib.adofai.type.*;

import java.util.*;
import java.util.stream.Stream;

import static io.luxus.lib.adofai.util.StringJsonUtil.*;
import static io.luxus.lib.adofai.util.StringJsonUtil.writeVar;

public class ActionFactory {

    public static final Map<String, EventType> eventTypeMap = getMap(EventType.class);
    public static final Map<String, BGDisplayModeType> bgDisplayModeTypeMap = getMap(BGDisplayModeType.class);
    public static final Map<String, CameraRelativeTo> cameraRelativeToMap = getMap(CameraRelativeTo.class);
    public static final Map<String, DecorationRelativeTo> decorationRelativeToMap = getMap(DecorationRelativeTo.class);
    public static final Map<String, Ease> easeMap = getMap(Ease.class);
    public static final Map<String, Filter> filterMap = getMap(Filter.class);
    public static final Map<String, Font> fontMap = getMap(Font.class);
    public static final Map<String, GameSound> gameSoundMap = getMap(GameSound.class);
    public static final Map<String, HitSound> hitSoundMap = getMap(HitSound.class);
    public static final Map<String, Plane> planeMap = getMap(Plane.class);
    public static final Map<String, SpeedType> speedTypeMap = getMap(SpeedType.class);
    public static final Map<String, TilePosition> tilePositionMap = getMap(TilePosition.class);
    public static final Map<String, Toggle> toggleMap = getMap(Toggle.class);
    public static final Map<String, TrackAnimation> trackAnimationMap = getMap(TrackAnimation.class);
    public static final Map<String, TrackColorPulse> trackColorPulseMap = getMap(TrackColorPulse.class);
    public static final Map<String, TrackColorType> trackColorTypeMap = getMap(TrackColorType.class);
    public static final Map<String, TrackDisappearAnimation> trackDisappearAnimationMap = getMap(TrackDisappearAnimation.class);
    public static final Map<String, TrackStyle> trackStyleMap = getMap(TrackStyle.class);

    private static <T extends Enum<T> & JsonParsable> Map<String, T> getMap(Class<T> klass) {
        T[] values = klass.getEnumConstants();
        Map<String, T> map = new HashMap<>(values.length);

        for (T value : values) {
            map.put(value.getJsonName(), value);
        }

        return Collections.unmodifiableMap(map);
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
            map.remove("floor");
            action = read(map);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (action == null) {
            System.err.println("Failed to read action rest=" + map + ", " + node);
            action = new UnknownAction(node);
        }

        return action;
    }

    private static Action read(Map<String, JsonNode> map) {
        JsonPropertyReader reader = new JsonPropertyReader(map);

        EventType eventType = reader.read("eventType", o -> o
                        .map(JsonNode::asText)
                        .map(eventTypeMap::get))
                .orElse(EventType.UNKNOWN);

        Action action = null;
        switch (eventType) {
            case SET_SPEED: {
                SetSpeed.Builder builder = new SetSpeed.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("speedType", builder::speedType, JsonNode::asText, speedTypeMap::get);
                reader.read("beatsPerMinute", builder::beatsPerMinute, JsonNode::asDouble);
                reader.read("bpmMultiplier", builder::bpmMultiplier, JsonNode::asDouble);
                action = builder.build();
                break;
            }
            case TWIRL: {
                Twirl.Builder builder = new Twirl.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                action = builder.build();
                break;
            }
            case BOOKMARK: {
                Bookmark.Builder builder = new Bookmark.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                action = builder.build();
                break;
            }
            case CHECK_POINT: {
                Checkpoint.Builder builder = new Checkpoint.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                action = builder.build();
                break;
            }
            case EDITOR_COMMENT: {
                EditorComment.Builder builder = new EditorComment.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("comment", builder::comment, JsonNode::asText);
                action = builder.build();
                break;
            }
            case CUSTOM_BACKGROUND: {
                CustomBackground.Builder builder = new CustomBackground.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("color", builder::color, JsonNode::asText);
                reader.read("bgImage", builder::bgImage, JsonNode::asText);
                reader.read("imageColor", builder::imageColor, JsonNode::asText);
                reader.read("parallax", builder::parallax, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("bgDisplayMode", builder::bgDisplayMode, JsonNode::asText, bgDisplayModeTypeMap::get);
                reader.read("lockRot", builder::lockRot, JsonNode::asText, toggleMap::get);
                reader.read("loopBG", builder::loopBG, JsonNode::asText, toggleMap::get);
                reader.read("unscaledSize", builder::unscaledSize, JsonNode::asLong);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case COLOR_TRACK: {
                ColorTrack.Builder builder = new ColorTrack.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("trackColorType", builder::trackColorType, JsonNode::asText, trackColorTypeMap::get);
                reader.read("trackColor", builder::trackColor, JsonNode::asText);
                reader.read("secondaryTrackColor", builder::secondaryTrackColor, JsonNode::asText);
                reader.read("trackColorAnimDuration", builder::trackColorAnimDuration, JsonNode::asDouble);
                reader.read("trackColorPulse", builder::trackColorPulse, JsonNode::asText, trackColorPulseMap::get);
                reader.read("trackPulseLength", builder::trackPulseLength, JsonNode::asLong);
                reader.read("trackStyle", builder::trackStyle, JsonNode::asText, trackStyleMap::get);
                reader.read("trackTexture", builder::trackTexture, JsonNode::asText);
                reader.read("trackTextureScale", builder::trackTextureScale, JsonNode::asDouble);

                action = builder.build();
                break;
            }
            case ANIMATE_TRACK: {
                AnimateTrack.Builder builder = new AnimateTrack.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("trackAnimation", builder::trackAnimation, JsonNode::asText, trackAnimationMap::get);
                reader.read("beatsAhead", builder::beatsAhead, JsonNode::asDouble);
                reader.read("trackDisappearAnimation", builder::trackDisappearAnimation, JsonNode::asText, trackDisappearAnimationMap::get);
                reader.read("beatsBehind", builder::beatsBehind, JsonNode::asDouble);

                action = builder.build();
                break;
            }
            case ADD_DECORATION: {
                AddDecoration.Builder builder = new AddDecoration.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);

                // parameter를 읽기 쉽게 해주는 class만들어서 사용하기
                Stream.of(reader.read("decorationImage", o -> o.map(JsonNode::asText)),
                                reader.read("decText", o -> o.map(JsonNode::asText)))
                        .filter(Optional::isPresent).map(Optional::get)
                        .findFirst().ifPresent(builder::decorationImage);
                reader.read("position", builder::position, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("relativeTo", builder::relativeTo, JsonNode::asText, decorationRelativeToMap::get);
                reader.read("pivotOffset", builder::pivotOffset, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("rotation", builder::rotation, JsonNode::asDouble);
                reader.read("scale", builder::scale, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("tile", builder::tile, nodeToXYListFunc(JsonNode::asLong));
                reader.read("color", builder::color, JsonNode::asText);
                reader.read("opacity", builder::opacity, JsonNode::asDouble);
                reader.read("depth", builder::depth, JsonNode::asLong);
                reader.read("parallax", builder::parallax, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("tag", builder::tag, JsonNode::asText);
                reader.read("imageSmoothing", builder::imageSmoothing, JsonNode::asText, toggleMap::get);
                reader.read("components", builder::components, JsonNode::asText);

                action = builder.build();
                break;
            }
            case FLASH: {
                Flash.Builder builder = new Flash.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("duration", builder::duration, JsonNode::asDouble);
                reader.read("plane", builder::plane, JsonNode::asText, planeMap::get);
                reader.read("startColor", builder::startColor, JsonNode::asText);
                reader.read("startOpacity", builder::startOpacity, JsonNode::asDouble);
                reader.read("endColor", builder::endColor, JsonNode::asText);
                reader.read("endOpacity", builder::endOpacity, JsonNode::asDouble);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("ease", builder::ease, JsonNode::asText, easeMap::get);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case MOVE_CAMERA: {
                MoveCamera.Builder builder = new MoveCamera.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("duration", builder::duration, JsonNode::asDouble);
                reader.read("relativeTo", builder::relativeTo, JsonNode::asText, cameraRelativeToMap::get);
                reader.read("position", builder::position, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("rotation", builder::rotation, JsonNode::asDouble);
                reader.read("zoom", builder::zoom, JsonNode::asLong);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("ease", builder::ease, JsonNode::asText, easeMap::get);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case SET_HITSOUND: {
                SetHitsound.Builder builder = new SetHitsound.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("gameSound", builder::gameSound, JsonNode::asText, gameSoundMap::get);
                reader.read("hitsound", builder::hitsound, JsonNode::asText, hitSoundMap::get);
                reader.read("hitsoundVolume", builder::hitsoundVolume, JsonNode::asLong);

                action = builder.build();
                break;
            }
            case PLAY_SOUND: {
                PlaySound.Builder builder = new PlaySound.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("hitsound", builder::hitsound, JsonNode::asText, hitSoundMap::get);
                reader.read("hitsoundVolume", builder::hitsoundVolume, JsonNode::asLong);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);

                action = builder.build();
                break;
            }
            case RECOLOR_TRACK: {
                RecolorTrack.Builder builder = new RecolorTrack.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);

                reader.read("startTile", startTile -> {
                            builder.startTileNum((Long) startTile.get(0));
                            builder.startTilePosition((TilePosition) startTile.get(1)); },
                        jsonNode -> Arrays.asList(
                                jsonNode.get(0).asLong(),
                                tilePositionMap.get(jsonNode.get(1).asText())));

                reader.read("endTile", endTile -> {
                            builder.endTileNum((Long) endTile.get(0));
                            builder.endTilePosition((TilePosition) endTile.get(1)); },
                        jsonNode -> Arrays.asList(
                                jsonNode.get(0).asLong(),
                                tilePositionMap.get(jsonNode.get(1).asText())));

                reader.read("trackColorType", builder::trackColorType, JsonNode::asText, trackColorTypeMap::get);
                reader.read("trackColor", builder::trackColor, JsonNode::asText);
                reader.read("secondaryTrackColor", builder::secondaryTrackColor, JsonNode::asText);
                reader.read("trackColorAnimDuration", builder::trackColorAnimDuration, JsonNode::asDouble);
                reader.read("trackColorPulse", builder::trackColorPulse, JsonNode::asText, trackColorPulseMap::get);
                reader.read("trackPulseLength", builder::trackPulseLength, JsonNode::asLong);
                reader.read("trackStyle", builder::trackStyle, JsonNode::asText, trackStyleMap::get);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case MOVE_TRACK: {
                MoveTrack.Builder builder = new MoveTrack.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);

                reader.read("startTile", startTile -> {
                    builder.startTileNum((Long) startTile.get(0));
                    builder.startTilePosition((TilePosition) startTile.get(1)); },
                        jsonNode -> Arrays.asList(
                                jsonNode.get(0).asLong(),
                                tilePositionMap.get(jsonNode.get(1).asText())));

                reader.read("endTile", endTile -> {
                    builder.endTileNum((Long) endTile.get(0));
                    builder.endTilePosition((TilePosition) endTile.get(1)); },
                        jsonNode -> Arrays.asList(
                                jsonNode.get(0).asLong(),
                                tilePositionMap.get(jsonNode.get(1).asText())));

                reader.read("duration", builder::duration, JsonNode::asDouble);
                reader.readO("positionOffset", builder::positionOffset, nodeToXYListFunc(JsonNode::asDouble));
                reader.readO("rotationOffset", builder::rotationOffset, JsonNode::asDouble);
                reader.readO("scale", builder::scale, nodeToXYListFunc(JsonNode::asDouble));
                reader.readO("opacity", builder::opacity, JsonNode::asDouble);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("ease", builder::ease, JsonNode::asText, easeMap::get);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case SET_FILTER: {
                SetFilter.Builder builder = new SetFilter.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("filter", builder::filter, JsonNode::asText, filterMap::get);
                reader.read("enabled", builder::enabled, JsonNode::asText, toggleMap::get);
                reader.read("intensity", builder::intensity, JsonNode::asLong);
                reader.read("duration", builder::duration, JsonNode::asDouble);
                reader.read("ease", builder::ease, JsonNode::asText, easeMap::get);
                reader.read("disableOthers", builder::disableOthers, JsonNode::asText, toggleMap::get);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case HALL_OF_MIRRORS: {
                HallOfMirrors.Builder builder = new HallOfMirrors.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("enabled", builder::enabled, JsonNode::asText, toggleMap::get);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case SHAKE_SCREEN: {
                ShakeScreen.Builder builder = new ShakeScreen.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("duration", builder::duration, JsonNode::asDouble);
                reader.read("strength", builder::strength, JsonNode::asDouble);
                reader.read("intensity", builder::intensity, JsonNode::asDouble);
                reader.read("fadeOut", builder::fadeOut, JsonNode::asText, toggleMap::get);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case SET_PLANET_ROTATION: {
                SetPlanetRotation.Builder builder = new SetPlanetRotation.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("ease", builder::ease, JsonNode::asText, easeMap::get);
                reader.read("easeParts", builder::easeParts, JsonNode::asLong);

                action = builder.build();
                break;
            }
            case MOVE_DECORATIONS: {
                MoveDecorations.Builder builder = new MoveDecorations.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("duration", builder::duration, JsonNode::asDouble);
                reader.read("tag", builder::tag, JsonNode::asText);
                reader.readO("decorationImage", builder::decorationImage, JsonNode::asText);
                reader.readO("positionOffset", builder::positionOffset, nodeToXYListFunc(JsonNode::asDouble));
                reader.readO("rotationOffset", builder::rotationOffset, JsonNode::asDouble);
                reader.readO("scale", builder::scale, nodeToXYListFunc(JsonNode::asDouble));
                reader.readO("color", builder::color, JsonNode::asText);
                reader.readO("opacity", builder::opacity, JsonNode::asDouble);
                reader.readO("depth", builder::depth, JsonNode::asLong);
                reader.readO("parallax", builder::parallax, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("ease", builder::ease, JsonNode::asText, easeMap::get);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case POSITION_TRACK: {
                PositionTrack.Builder builder = new PositionTrack.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("positionOffset", builder::positionOffset, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("editorOnly", builder::editorOnly, JsonNode::asText, toggleMap::get);

                action = builder.build();
                break;
            }
            case REPEAT_EVENTS: {
                RepeatEvents.Builder builder = new RepeatEvents.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("repetitions", builder::repetitions, JsonNode::asLong);
                reader.read("interval", builder::interval, JsonNode::asDouble);
                reader.read("tag", builder::tag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case BLOOM: {
                Bloom.Builder builder = new Bloom.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("enabled", builder::enabled, JsonNode::asText, toggleMap::get);
                reader.read("threshold", builder::threshold, JsonNode::asDouble);
                reader.read("intensity", builder::intensity, JsonNode::asDouble);
                reader.read("color", builder::color, JsonNode::asText);
                reader.read("duration", builder::duration, JsonNode::asDouble);
                reader.read("ease", builder::ease, JsonNode::asText, easeMap::get);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case SET_CONDITIONAL_EVENTS: {
                SetConditionalEvents.Builder builder = new SetConditionalEvents.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("perfectTag", builder::perfectTag, JsonNode::asText);
                reader.read("hitTag", builder::hitTag, JsonNode::asText);
                reader.read("barelyTag", builder::barelyTag, JsonNode::asText);
                reader.read("missTag", builder::missTag, JsonNode::asText);
                reader.read("lossTag", builder::lossTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case SCREEN_TILE: {
                ScreenTile.Builder builder = new ScreenTile.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("tile", builder::tile, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case SCREEN_SCROLL: {
                ScreenScroll.Builder builder = new ScreenScroll.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("scroll", builder::scroll, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case ADD_TEXT: {
                AddText.Builder builder = new AddText.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("decText", builder::decText, JsonNode::asText);
                reader.read("decText", builder::decText, JsonNode::asText);
                reader.read("font", builder::font, JsonNode::asText, fontMap::get);
                reader.read("position", builder::position, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("relativeTo", builder::relativeTo, JsonNode::asText, decorationRelativeToMap::get);
                reader.read("pivotOffset", builder::pivotOffset, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("rotation", builder::rotation, JsonNode::asDouble);
                reader.read("scale", builder::scale, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("color", builder::color, JsonNode::asText);
                reader.read("opacity", builder::opacity, JsonNode::asDouble);
                reader.read("depth", builder::depth, JsonNode::asLong);
                reader.read("parallax", builder::parallax, nodeToXYListFunc(JsonNode::asDouble));
                reader.read("tag", builder::tag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case SET_TEXT: {
                SetText.Builder builder = new SetText.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("decText", builder::decText, JsonNode::asText);
                reader.read("tag", builder::tag, JsonNode::asText);
                reader.read("angleOffset", builder::angleOffset, JsonNode::asDouble);
                reader.read("eventTag", builder::eventTag, JsonNode::asText);

                action = builder.build();
                break;
            }
            case CHANGE_TRACK: {
                ChangeTrack.Builder builder = new ChangeTrack.Builder();
                reader.read("active", builder::active, JsonNode::asBoolean);
                reader.read("trackColorType", builder::trackColorType, JsonNode::asText, trackColorTypeMap::get);
                reader.read("trackColor", builder::trackColor, JsonNode::asText);
                reader.read("secondaryTrackColor", builder::secondaryTrackColor, JsonNode::asText);
                reader.read("trackColorAnimDuration", builder::trackColorAnimDuration, JsonNode::asDouble);
                reader.read("trackColorPulse", builder::trackColorPulse, JsonNode::asText, trackColorPulseMap::get);
                reader.read("trackPulseLength", builder::trackPulseLength, JsonNode::asLong);
                reader.read("trackStyle", builder::trackStyle, JsonNode::asText, trackStyleMap::get);
                reader.read("trackAnimation", builder::trackAnimation, JsonNode::asText, trackAnimationMap::get);
                reader.read("beatsAhead", builder::beatsAhead, JsonNode::asDouble);
                reader.read("trackDisappearAnimation", builder::trackDisappearAnimation, JsonNode::asText, trackDisappearAnimationMap::get);
                reader.read("beatsBehind", builder::beatsBehind, JsonNode::asDouble);

                action = builder.build();
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

        writeVar(sb, "active", action.getActive());

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
                writeVar(sb, "relativeTo", e.getRelativeTo(), DecorationRelativeTo::getJsonName);
                writeVar(sb, "pivotOffset", e.getPivotOffset());
                writeVar(sb, "rotation", e.getRotation());
                writeVar(sb, "scale", e.getScale());
                writeVar(sb, "tile", e.getTile());
                writeVar(sb, "color", e.getColor());
                writeVar(sb, "opacity", e.getOpacity());
                writeVar(sb, "depth", e.getDepth());
                writeVar(sb, "parallax", e.getParallax());
                writeVar(sb, "tag", e.getTag());
                writeVar(sb, "imageSmoothing", e.getImageSmoothing(), Toggle::getJsonName);
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
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "ease", e.getEase(), Ease::getJsonName);
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
                writeVar(sb, "decorationImage", e.getDecorationImage());
                writeVar(sb, "positionOffset", e.getPositionOffset());
                writeVar(sb, "rotationOffset", e.getRotationOffset());
                writeVar(sb, "scale", e.getScale());
                writeVar(sb, "color", e.getColor());
                writeVar(sb, "opacity", e.getOpacity());
                writeVar(sb, "depth", e.getDepth());
                writeVar(sb, "parallax", e.getParallax());
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
                writeVar(sb, "duration", e.getDuration());
                writeVar(sb, "ease", e.getEase(), Ease::getJsonName);
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


/*
Failed to read action rest={enabled=false}, {"floor":5,"eventType":"SetSpeed","enabled":false,"speedType":"Multiplier","beatsPerMinute":100,"bpmMultiplier":1.1}
Failed to read action rest={enabled=false}, {"floor":10,"eventType":"Twirl","enabled":false}
Failed to read action rest={enabled=false}, {"floor":15,"eventType":"Checkpoint","enabled":false}
Failed to read action rest={enabled=false}, {"floor":42,"eventType":"SetHitsound","enabled":false,"gameSound":"Hitsound","hitsound":"ReverbClap","hitsoundVolume":100}
Failed to read action rest={enabled=false}, {"floor":47,"eventType":"PlaySound","enabled":false,"hitsound":"Hat","hitsoundVolume":10,"angleOffset":1.1}
Failed to read action rest={enabled=false}, {"floor":85,"eventType":"SetPlanetRotation","enabled":false,"ease":"InOutFlash","easeParts":1}
Failed to read action rest={enabled=false}, {"floor":95,"eventType":"ColorTrack","enabled":false,"trackColorType":"Volume","trackColor":"debb7b","secondaryTrackColor":"ffffff","trackColorAnimDuration":2,"trackColorPulse":"None","trackPulseLength":10,"trackStyle":"Standard","trackTexture":"","trackTextureScale":1}
Failed to read action rest={enabled=false}, {"floor":108,"eventType":"AnimateTrack","enabled":false,"trackAnimation":"Rise","beatsAhead":3,"trackDisappearAnimation":"Scatter_Far","beatsBehind":4}
Failed to read action rest={enabled=false}, {"floor":124,"eventType":"RecolorTrack","enabled":false,"startTile":[0,"ThisTile"],"endTile":[0,"ThisTile"],"trackColorType":"Single","trackColor":"debb7b","secondaryTrackColor":"ffffff","trackColorAnimDuration":2,"trackColorPulse":"None","trackPulseLength":10,"trackStyle":"Standard","angleOffset":0,"eventTag":""}
Failed to read action rest={enabled=false}, {"floor":130,"eventType":"MoveTrack","enabled":false,"startTile":[0,"End"],"endTile":[0,"End"],"duration":1,"positionOffset":[0,99.1],"rotationOffset":2.2,"opacity":1.1,"angleOffset":0,"ease":"Linear","eventTag":""}
Failed to read action rest={enabled=false}, {"floor":135,"eventType":"PositionTrack","enabled":false,"positionOffset":[0.1,0.1],"editorOnly":"Enabled"}
Failed to read action rest={enabled=false}, {"floor":144,"eventType":"AddDecoration","enabled":false,"decorationImage":"","position":[0,0],"relativeTo":"CameraAspect","pivotOffset":[0,0],"rotation":0,"scale":[100,100],"tile":[1,1],"color":"ffffff","opacity":100,"depth":0,"parallax":[0,0],"tag":"","imageSmoothing":"Enabled","components":""}
Failed to read action rest={enabled=false}, {"floor":149,"eventType":"MoveDecorations","enabled":false,"duration":1.1,"tag":"asdfasd \" 123b \" bw\"","decorationImage":"1px.png","positionOffset":[1.1,1.1],"rotationOffset":1.1,"scale":[1.1,1.1],"color":"ff0000b7","opacity":1.1,"depth":-3,"parallax":[1.1,1.1],"angleOffset":1.1,"ease":"OutSine","eventTag":"asdb\\\" b\" wcd"}
Failed to read action rest={enabled=false}, {"floor":159,"eventType":"AddText","enabled":false,"decText":"텍스트","font":"TimesNewRoman","position":[0,0],"relativeTo":"Tile","pivotOffset":[0,0],"rotation":0,"scale":[100,100],"color":"ffffff","opacity":100,"depth":-1,"parallax":[-1,-1],"tag":""}
Failed to read action rest={enabled=false}, {"floor":164,"eventType":"SetText","enabled":false,"decText":"ffasbwef","tag":"123b\"cw weg\\w\\ca","angleOffset":1.1,"eventTag":"ASCasdc\"ef\\"}
Failed to read action rest={enabled=false}, {"floor":172,"eventType":"CustomBackground","enabled":false,"color":"000000","bgImage":"1px.png","imageColor":"ffffff","parallax":[100,100],"bgDisplayMode":"Tiled","lockRot":"Disabled","loopBG":"Disabled","unscaledSize":100,"angleOffset":1.1,"eventTag":""}
Failed to read action rest={enabled=false}, {"floor":177,"eventType":"Flash","enabled":false,"duration":1.1,"plane":"Foreground","startColor":"ffffffff","startOpacity":1.1,"endColor":"ffffffff","endOpacity":1.1,"angleOffset":1.1,"ease":"InSine","eventTag":"asdfasdf123"}
Failed to read action rest={enabled=false}, {"floor":184,"eventType":"MoveCamera","enabled":false,"duration":1,"relativeTo":"LastPosition","position":[0,0],"rotation":0,"zoom":100,"angleOffset":1.1,"ease":"Linear","eventTag":""}
Failed to read action rest={enabled=false}, {"floor":224,"eventType":"ShakeScreen","enabled":false,"duration":1.1,"strength":1.1,"intensity":1.1,"fadeOut":"Disabled","angleOffset":1.1,"eventTag":"asdf12"}
Failed to read action rest={enabled=false}, {"floor":234,"eventType":"ScreenTile","enabled":false,"tile":[1.1,1.1],"angleOffset":1.1,"eventTag":"asfvbw"}
Failed to read action rest={enabled=false}, {"floor":239,"eventType":"ScreenScroll","enabled":false,"scroll":[1.1,1.1],"angleOffset":1.1,"eventTag":"asdfasdbvwe123"}
Failed to read action rest={enabled=false}, {"floor":244,"eventType":"RepeatEvents","enabled":false,"repetitions":12,"interval":1.1,"tag":"asdfasdf"}
Failed to read action rest={enabled=false}, {"floor":249,"eventType":"SetConditionalEvents","enabled":false,"perfectTag":"1231r2vadvasd","hitTag":"wfhy4hrtr","barelyTag":"eg5u5gt\\\\","missTag":"af///ewf\\","lossTag":"asdfb//"}
Failed to read action rest={enabled=false}, {"floor":254,"eventType":"EditorComment","enabled":false,"comment":"gawefwiofjsoibhaoifowofosad;fos\n<green>asdfasdf</green>\n\n\n\nasbwef\\\\\"\n\n\\\n\\"}
Failed to read action rest={enabled=false}, {"floor":259,"eventType":"Bookmark","enabled":false}
SetSpeed, Twirl, Checkpoint, SetHitsound, PlaySound, SetPlanetRotation, ColorTrack, AnimateTrack, RecolorTrack, MoveTrack, PositionTrack, AddDecoration, MoveDecorations, AddText, SetText, CustomBackground, Flash, MoveCamera, ShakeScreen, ScreenTile, ScreenScroll, RepeatEvents, SetConditionalEvents, EditorComment, Bookmark

*/


