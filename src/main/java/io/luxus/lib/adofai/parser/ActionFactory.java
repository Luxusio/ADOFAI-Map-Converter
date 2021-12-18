package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.type.action.*;
import io.luxus.lib.adofai.type.*;

import java.util.*;
import java.util.stream.Stream;

import static io.luxus.lib.adofai.util.StringJsonUtil.*;

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
            action = read(map);
        } catch (Throwable t) {
            //t.printStackTrace();
        }

        if (action == null) {
            System.err.println("Failed to read action rest=" + map + ", " + node);
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
                        readProperty(map, "speedType", JsonNode::asText, speedTypeMap::get),
                        readProperty(map, "beatsPerMinute", JsonNode::asDouble),
                        readProperty(map, "bpmMultiplier", JsonNode::asDouble));
                break;
            }
            case TWIRL: {
                action = new Twirl.Builder().build();
                break;
            }
            case BOOKMARK: {
                action = new Bookmark.Builder().build();
                break;
            }
            case CHECK_POINT: {
                action = new Checkpoint.Builder().build();
                break;
            }
            case EDITOR_COMMENT: {
                EditorComment.Builder builder = new EditorComment.Builder();
                readPropertyOptionalChain(map, "comment", JsonNode::asText).ifPresent(builder::comment);
                action = builder.build();
                break;
            }
            case CUSTOM_BACKGROUND: {
                CustomBackground.Builder builder = new CustomBackground.Builder();
                readPropertyOptionalChain(map, "color", JsonNode::asText).ifPresent(builder::color);
                readPropertyOptionalChain(map, "bgImage", JsonNode::asText).ifPresent(builder::bgImage);
                readPropertyOptionalChain(map, "imageColor", JsonNode::asText).ifPresent(builder::imageColor);
                readPropertyOptionalChain(map, "parallax", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::parallax);
                readPropertyOptionalChain(map, "bgDisplayMode", JsonNode::asText, bgDisplayModeTypeMap::get).ifPresent(builder::bgDisplayMode);
                readPropertyOptionalChain(map, "lockRot", JsonNode::asText, toggleMap::get).ifPresent(builder::lockRot);
                readPropertyOptionalChain(map, "loopBG", JsonNode::asText, toggleMap::get).ifPresent(builder::loopBG);
                readPropertyOptionalChain(map, "unscaledSize", JsonNode::asLong).ifPresent(builder::unscaledSize);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case COLOR_TRACK: {
                ColorTrack.Builder builder = new ColorTrack.Builder();
                readPropertyOptionalChain(map, "trackColorType", JsonNode::asText, trackColorTypeMap::get).ifPresent(builder::trackColorType);
                readPropertyOptionalChain(map, "trackColor", JsonNode::asText).ifPresent(builder::trackColor);
                readPropertyOptionalChain(map, "secondaryTrackColor", JsonNode::asText).ifPresent(builder::secondaryTrackColor);
                readPropertyOptionalChain(map, "trackColorAnimDuration", JsonNode::asDouble).ifPresent(builder::trackColorAnimDuration);
                readPropertyOptionalChain(map, "trackColorPulse", JsonNode::asText, trackColorPulseMap::get).ifPresent(builder::trackColorPulse);
                readPropertyOptionalChain(map, "trackPulseLength", JsonNode::asLong).ifPresent(builder::trackPulseLength);
                readPropertyOptionalChain(map, "trackStyle", JsonNode::asText, trackStyleMap::get).ifPresent(builder::trackStyle);
                readPropertyOptionalChain(map, "trackTexture", JsonNode::asText).ifPresent(builder::trackTexture);
                readPropertyOptionalChain(map, "trackTextureScale", JsonNode::asDouble).ifPresent(builder::trackTextureScale);

                action = builder.build();
                break;
            }
            case ANIMATE_TRACK: {
                AnimateTrack.Builder builder = new AnimateTrack.Builder();
                readPropertyOptionalChain(map, "trackAnimation", JsonNode::asText, trackAnimationMap::get).ifPresent(builder::trackAnimation);
                readPropertyOptionalChain(map, "beatsAhead", JsonNode::asDouble).ifPresent(builder::beatsAhead);
                readPropertyOptionalChain(map, "trackDisappearAnimation", JsonNode::asText, trackDisappearAnimationMap::get).ifPresent(builder::trackDisappearAnimation);
                readPropertyOptionalChain(map, "beatsBehind", JsonNode::asDouble).ifPresent(builder::beatsBehind);

                action = builder.build();
                break;
            }
            case ADD_DECORATION: {
                AddDecoration.Builder builder = new AddDecoration.Builder();

                // parameter를 읽기 쉽게 해주는 class만들어서 사용하기
                Stream.of(
                        readPropertyOptional(map, "decorationImage", o -> o.map(JsonNode::asText)),
                        readPropertyOptional(map, "decText", o -> o.map(JsonNode::asText)))
                        .filter(Optional::isPresent).map(Optional::get).findFirst().ifPresent(builder::decorationImage);
                readPropertyOptionalChain(map, "position", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::position);
                readPropertyOptionalChain(map, "relativeTo", JsonNode::asText, decorationRelativeToMap::get).ifPresent(builder::decorationRelativeTo);
                readPropertyOptionalChain(map, "pivotOffset", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::pivotOffset);
                readPropertyOptionalChain(map, "rotation", JsonNode::asDouble).ifPresent(builder::rotation);
                readPropertyOptionalChain(map, "scale", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::scale);
                readPropertyOptionalChain(map, "tile", nodeToXYListFunc(JsonNode::asLong)).ifPresent(builder::tile);
                readPropertyOptionalChain(map, "color", JsonNode::asText).ifPresent(builder::color);
                readPropertyOptionalChain(map, "opacity", JsonNode::asLong).ifPresent(builder::opacity);
                readPropertyOptionalChain(map, "depth", JsonNode::asLong).ifPresent(builder::depth);
                readPropertyOptionalChain(map, "parallax", JsonNode::asLong).ifPresent(builder::parallax);
                readPropertyOptionalChain(map, "tag", JsonNode::asText).ifPresent(builder::tag);
                readPropertyOptionalChain(map, "components", JsonNode::asText).ifPresent(builder::components);

                action = builder.build();
                break;
            }
            case FLASH: {
                Flash.Builder builder = new Flash.Builder();
                readPropertyOptionalChain(map, "duration", JsonNode::asDouble).ifPresent(builder::duration);
                readPropertyOptionalChain(map, "plane", JsonNode::asText, planeMap::get).ifPresent(builder::plane);
                readPropertyOptionalChain(map, "startColor", JsonNode::asText).ifPresent(builder::startColor);
                readPropertyOptionalChain(map, "startOpacity", JsonNode::asLong).ifPresent(builder::startOpacity);
                readPropertyOptionalChain(map, "endColor", JsonNode::asText).ifPresent(builder::endColor);
                readPropertyOptionalChain(map, "endOpacity", JsonNode::asLong).ifPresent(builder::endOpacity);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "ease", JsonNode::asText, easeMap::get).ifPresent(builder::ease);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case MOVE_CAMERA: {
                MoveCamera.Builder builder = new MoveCamera.Builder();
                readPropertyOptionalChain(map, "duration", JsonNode::asDouble).ifPresent(builder::duration);
                readPropertyOptionalChain(map, "relativeTo", JsonNode::asText, cameraRelativeToMap::get).ifPresent(builder::relativeTo);
                readPropertyOptionalChain(map, "position", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::position);
                readPropertyOptionalChain(map, "rotation", JsonNode::asDouble).ifPresent(builder::rotation);
                readPropertyOptionalChain(map, "zoom", JsonNode::asLong).ifPresent(builder::zoom);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "ease", JsonNode::asText, easeMap::get).ifPresent(builder::ease);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case SET_HITSOUND: {
                SetHitsound.Builder builder = new SetHitsound.Builder();
                readPropertyOptionalChain(map, "gameSound", JsonNode::asText, gameSoundMap::get).ifPresent(builder::gameSound);
                readPropertyOptionalChain(map, "hitsound", JsonNode::asText, hitSoundMap::get).ifPresent(builder::hitsound);
                readPropertyOptionalChain(map, "hitsoundVolume", JsonNode::asLong).ifPresent(builder::hitsoundVolume);

                action = builder.build();
                break;
            }
            case PLAY_SOUND: {
                PlaySound.Builder builder = new PlaySound.Builder();
                readPropertyOptionalChain(map, "hitsound", JsonNode::asText, hitSoundMap::get).ifPresent(builder::hitsound);
                readPropertyOptionalChain(map, "hitsoundVolume", JsonNode::asLong).ifPresent(builder::hitsoundVolume);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);

                action = builder.build();
                break;
            }
            case RECOLOR_TRACK: {
                RecolorTrack.Builder builder = new RecolorTrack.Builder();

                readPropertyOptionalChain(map, "startTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())))
                        .ifPresent(startTile -> {
                            builder.startTileNum((Long) startTile.get(0));
                            builder.startTilePosition((TilePosition) startTile.get(1));
                        });

                readPropertyOptionalChain(map, "endTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())))
                        .ifPresent(endTile -> {
                            builder.endTileNum((Long) endTile.get(0));
                            builder.endTilePosition((TilePosition) endTile.get(1));
                        });

                readPropertyOptionalChain(map, "trackColorType", JsonNode::asText, trackColorTypeMap::get).ifPresent(builder::trackColorType);
                readPropertyOptionalChain(map, "trackColor", JsonNode::asText).ifPresent(builder::trackColor);
                readPropertyOptionalChain(map, "secondaryTrackColor", JsonNode::asText).ifPresent(builder::secondaryTrackColor);
                readPropertyOptionalChain(map, "trackColorAnimDuration", JsonNode::asDouble).ifPresent(builder::trackColorAnimDuration);
                readPropertyOptionalChain(map, "trackColorPulse", JsonNode::asText, trackColorPulseMap::get).ifPresent(builder::trackColorPulse);
                readPropertyOptionalChain(map, "trackPulseLength", JsonNode::asLong).ifPresent(builder::trackPulseLength);
                readPropertyOptionalChain(map, "trackStyle", JsonNode::asText, trackStyleMap::get).ifPresent(builder::trackStyle);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case MOVE_TRACK: {
                MoveTrack.Builder builder = new MoveTrack.Builder();

                readPropertyOptionalChain(map, "startTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())))
                        .ifPresent(startTile -> {
                            builder.startTileNum((Long) startTile.get(0));
                            builder.startTilePosition((TilePosition) startTile.get(1));
                        });

                readPropertyOptionalChain(map, "endTile", jsonNode -> Arrays.asList(
                        jsonNode.get(0).asLong(),
                        tilePositionMap.get(jsonNode.get(1).asText())))
                        .ifPresent(endTile -> {
                            builder.endTileNum((Long) endTile.get(0));
                            builder.endTilePosition((TilePosition) endTile.get(1));
                        });

                readPropertyOptionalChain(map, "duration", JsonNode::asDouble).ifPresent(builder::duration);
                readPropertyOptionalChain(map, "positionOffset", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::positionOffset);
                readPropertyOptionalChain(map, "rotationOffset", JsonNode::asDouble).ifPresent(builder::rotationOffset);
                readPropertyOptionalChain(map, "scale", nodeToXYListFunc(JsonNode::asLong)).ifPresent(builder::scale);
                readPropertyOptionalChain(map, "opacity", JsonNode::asLong).ifPresent(builder::opacity);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "ease", JsonNode::asText, easeMap::get).ifPresent(builder::ease);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case SET_FILTER: {
                SetFilter.Builder builder = new SetFilter.Builder();
                readPropertyOptionalChain(map, "filter", JsonNode::asText, filterMap::get).ifPresent(builder::filter);
                readPropertyOptionalChain(map, "enabled", JsonNode::asText, toggleMap::get).ifPresent(builder::enabled);
                readPropertyOptionalChain(map, "intensity", JsonNode::asLong).ifPresent(builder::intensity);
                readPropertyOptionalChain(map, "disableOthers", JsonNode::asText, toggleMap::get).ifPresent(builder::disableOthers);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case HALL_OF_MIRRORS: {
                HallOfMirrors.Builder builder = new HallOfMirrors.Builder();
                readPropertyOptionalChain(map, "enabled", JsonNode::asText, toggleMap::get).ifPresent(builder::enabled);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case SHAKE_SCREEN: {
                ShakeScreen.Builder builder = new ShakeScreen.Builder();
                readPropertyOptionalChain(map, "duration", JsonNode::asDouble).ifPresent(builder::duration);
                readPropertyOptionalChain(map, "strength", JsonNode::asLong).ifPresent(builder::strength);
                readPropertyOptionalChain(map, "intensity", JsonNode::asLong).ifPresent(builder::intensity);
                readPropertyOptionalChain(map, "fadeOut", JsonNode::asText, toggleMap::get).ifPresent(builder::fadeOut);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case SET_PLANET_ROTATION: {
                SetPlanetRotation.Builder builder = new SetPlanetRotation.Builder();
                readPropertyOptionalChain(map, "ease", JsonNode::asText, easeMap::get).ifPresent(builder::ease);
                readPropertyOptionalChain(map, "easeParts", JsonNode::asLong).ifPresent(builder::easeParts);

                action = builder.build();
                break;
            }
            case MOVE_DECORATIONS: {
                MoveDecorations.Builder builder = new MoveDecorations.Builder();
                readPropertyOptionalChain(map, "duration", JsonNode::asDouble).ifPresent(builder::duration);
                readPropertyOptionalChain(map, "tag", JsonNode::asText).ifPresent(builder::tag);
                readPropertyOptionalChain(map, "positionOffset", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::positionOffset);
                readPropertyOptionalChain(map, "rotationOffset", JsonNode::asDouble).ifPresent(builder::rotationOffset);
                readPropertyOptionalChain(map, "scale", nodeToXYListFunc(JsonNode::asLong)).ifPresent(builder::scale);
                readPropertyOptionalChain(map, "color", JsonNode::asText).ifPresent(builder::color);
                readPropertyOptionalChain(map, "opacity", JsonNode::asLong).ifPresent(builder::opacity);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "ease", JsonNode::asText, easeMap::get).ifPresent(builder::ease);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case POSITION_TRACK: {
                PositionTrack.Builder builder = new PositionTrack.Builder();
                readPropertyOptionalChain(map, "positionOffset", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::positionOffset);
                readPropertyOptionalChain(map, "editorOnly", JsonNode::asText, toggleMap::get).ifPresent(builder::editorOnly);

                action = builder.build();
                break;
            }
            case REPEAT_EVENTS: {
                RepeatEvents.Builder builder = new RepeatEvents.Builder();
                readPropertyOptionalChain(map, "repetitions", JsonNode::asLong).ifPresent(builder::repetitions);
                readPropertyOptionalChain(map, "interval", JsonNode::asDouble).ifPresent(builder::interval);
                readPropertyOptionalChain(map, "tag", JsonNode::asText).ifPresent(builder::tag);

                action = builder.build();
                break;
            }
            case BLOOM: {
                Bloom.Builder builder = new Bloom.Builder();
                readPropertyOptionalChain(map, "enabled", JsonNode::asText, toggleMap::get).ifPresent(builder::enabled);
                readPropertyOptionalChain(map, "threshold", JsonNode::asLong).ifPresent(builder::threshold);
                readPropertyOptionalChain(map, "intensity", JsonNode::asLong).ifPresent(builder::intensity);
                readPropertyOptionalChain(map, "color", JsonNode::asText).ifPresent(builder::color);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case SET_CONDITIONAL_EVENTS: {
                SetConditionalEvents.Builder builder = new SetConditionalEvents.Builder();
                readPropertyOptionalChain(map, "perfectTag", JsonNode::asText).ifPresent(builder::perfectTag);
                readPropertyOptionalChain(map, "hitTag", JsonNode::asText).ifPresent(builder::hitTag);
                readPropertyOptionalChain(map, "barelyTag", JsonNode::asText).ifPresent(builder::barelyTag);
                readPropertyOptionalChain(map, "missTag", JsonNode::asText).ifPresent(builder::missTag);
                readPropertyOptionalChain(map, "lossTag", JsonNode::asText).ifPresent(builder::lossTag);

                action = builder.build();
                break;
            }
            case SCREEN_TILE: {
                ScreenTile.Builder builder = new ScreenTile.Builder();
                readPropertyOptionalChain(map, "tile", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::tile);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case SCREEN_SCROLL: {
                ScreenScroll.Builder builder = new ScreenScroll.Builder();
                readPropertyOptionalChain(map, "scroll", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::scroll);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case ADD_TEXT: {
                AddText.Builder builder = new AddText.Builder();
                readPropertyOptionalChain(map, "decText", JsonNode::asText).ifPresent(builder::decText);
                readPropertyOptionalChain(map, "decText", JsonNode::asText).ifPresent(builder::decText);
                readPropertyOptionalChain(map, "font", JsonNode::asText, fontMap::get).ifPresent(builder::font);
                readPropertyOptionalChain(map, "position", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::position);
                readPropertyOptionalChain(map, "relativeTo", JsonNode::asText, decorationRelativeToMap::get).ifPresent(builder::relativeTo);
                readPropertyOptionalChain(map, "pivotOffset", nodeToXYListFunc(JsonNode::asDouble)).ifPresent(builder::pivotOffset);
                readPropertyOptionalChain(map, "rotation", JsonNode::asDouble).ifPresent(builder::rotation);
                readPropertyOptionalChain(map, "scale", nodeToXYListFunc(JsonNode::asLong)).ifPresent(builder::scale);
                readPropertyOptionalChain(map, "color", JsonNode::asText).ifPresent(builder::color);
                readPropertyOptionalChain(map, "opacity", JsonNode::asLong).ifPresent(builder::opacity);
                readPropertyOptionalChain(map, "depth", JsonNode::asLong).ifPresent(builder::depth);
                readPropertyOptionalChain(map, "parallax", JsonNode::asLong).ifPresent(builder::parallax);
                readPropertyOptionalChain(map, "tag", JsonNode::asText).ifPresent(builder::tag);

                action = builder.build();
                break;
            }
            case SET_TEXT: {
                SetText.Builder builder = new SetText.Builder();
                readPropertyOptionalChain(map, "decText", JsonNode::asText).ifPresent(builder::decText);
                readPropertyOptionalChain(map, "tag", JsonNode::asText).ifPresent(builder::tag);
                readPropertyOptionalChain(map, "angleOffset", JsonNode::asDouble).ifPresent(builder::angleOffset);
                readPropertyOptionalChain(map, "eventTag", JsonNode::asText).ifPresent(builder::eventTag);

                action = builder.build();
                break;
            }
            case CHANGE_TRACK: {
                ChangeTrack.Builder builder = new ChangeTrack.Builder();
                readPropertyOptionalChain(map, "trackColorType", JsonNode::asText, trackColorTypeMap::get).ifPresent(builder::trackColorType);
                readPropertyOptionalChain(map, "trackColor", JsonNode::asText).ifPresent(builder::trackColor);
                readPropertyOptionalChain(map, "secondaryTrackColor", JsonNode::asText).ifPresent(builder::secondaryTrackColor);
                readPropertyOptionalChain(map, "trackColorAnimDuration", JsonNode::asDouble).ifPresent(builder::trackColorAnimDuration);
                readPropertyOptionalChain(map, "trackColorPulse", JsonNode::asText, trackColorPulseMap::get).ifPresent(builder::trackColorPulse);
                readPropertyOptionalChain(map, "trackPulseLength", JsonNode::asLong).ifPresent(builder::trackPulseLength);
                readPropertyOptionalChain(map, "trackStyle", JsonNode::asText, trackStyleMap::get).ifPresent(builder::trackStyle);
                readPropertyOptionalChain(map, "trackAnimation", JsonNode::asText, trackAnimationMap::get).ifPresent(builder::trackAnimation);
                readPropertyOptionalChain(map, "beatsAhead", JsonNode::asDouble).ifPresent(builder::beatsAhead);
                readPropertyOptionalChain(map, "trackDisappearAnimation", JsonNode::asText, trackDisappearAnimationMap::get).ifPresent(builder::trackDisappearAnimation);
                readPropertyOptionalChain(map, "beatsBehind", JsonNode::asDouble).ifPresent(builder::beatsBehind);

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