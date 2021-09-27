package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.action.type.*;
import io.luxus.lib.adofai.util.StringJsonUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

import static io.luxus.lib.adofai.parser.ActionFactory.*;

public class LevelSettingFactory {

    public static LevelSetting read(JsonNode node) {
        Map<String, JsonNode> map = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> it = node.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> field = it.next();
            map.put(field.getKey(), field.getValue());
        }

        LevelSetting levelSetting = new LevelSetting(
                readProperty(map, "version", JsonNode::asLong),
                readProperty(map, "artist", JsonNode::asText),
                readProperty(map, "specialArtistType", JsonNode::asText),
                readProperty(map, "artistPermission", JsonNode::asText),
                readProperty(map, "song", JsonNode::asText),
                readProperty(map, "author", JsonNode::asText),
                readProperty(map, "separateCountdownTime", jsonNode -> toggleMap.get(jsonNode.asText())),
                readProperty(map, "previewImage", JsonNode::asText),
                readProperty(map, "previewIcon", JsonNode::asText),
                readProperty(map, "previewIconColor", JsonNode::asText),
                readProperty(map, "previewSongStart", JsonNode::asLong),
                readProperty(map, "previewSongDuration", JsonNode::asLong),
                readProperty(map, "seizureWarning", jsonNode -> toggleMap.get(jsonNode.asText())),
                readProperty(map, "levelDesc", JsonNode::asText),
                readProperty(map, "levelTags", JsonNode::asText),
                readProperty(map, "artistLinks", JsonNode::asText),
                readProperty(map, "difficulty", JsonNode::asLong),
                readProperty(map, "songFilename", JsonNode::asText),
                readProperty(map, "bpm", JsonNode::asDouble),
                readProperty(map, "volume", JsonNode::asLong),
                readProperty(map, "offset", JsonNode::asLong),
                readProperty(map, "pitch", JsonNode::asLong),
                readProperty(map, "hitsound", jsonNode -> hitSoundMap.get(jsonNode.asText())),
                readProperty(map, "hitsoundVolume", JsonNode::asLong),
                readProperty(map, "countdownTicks", JsonNode::asLong),
                readProperty(map, "trackColorType", jsonNode -> trackColorTypeMap.get(jsonNode.asText())),
                readProperty(map, "trackColor", JsonNode::asText),
                readProperty(map, "secondaryTrackColor", JsonNode::asText),
                readProperty(map, "trackColorAnimDuration", JsonNode::asLong),
                readProperty(map, "trackColorPulse", jsonNode -> trackColorPulseMap.get(jsonNode.asText())),
                readProperty(map, "trackPulseLength", JsonNode::asLong),
                readProperty(map, "trackStyle", jsonNode -> trackStyleMap.get(jsonNode.asText())),
                readProperty(map, "trackAnimation", jsonNode -> trackAnimationMap.get(jsonNode.asText())),
                readProperty(map, "beatsAhead", JsonNode::asLong),
                readProperty(map, "trackDisappearAnimation", jsonNode -> trackDisappearAnimationMap.get(jsonNode.asText())),
                readProperty(map, "beatsBehind", JsonNode::asLong),
                readProperty(map, "backgroundColor", JsonNode::asText),
                readProperty(map, "showDefaultBGIfNoImage", jsonNode -> toggleMap.get(jsonNode.asText())),
                readProperty(map, "bgImage", JsonNode::asText),
                readProperty(map, "bgImageColor", JsonNode::asText),
                readProperty(map, "parallax", jsonNode -> Arrays.asList(jsonNode.get(0).asLong(), jsonNode.get(1).asLong())),
                readProperty(map, "bgDisplayMode", jsonNode -> bgDisplayModeTypeMap.get(jsonNode.asText())),
                readProperty(map, "lockRot", jsonNode -> toggleMap.get(jsonNode.asText())),
                readProperty(map, "loopBG", jsonNode -> toggleMap.get(jsonNode.asText())),
                readProperty(map, "unscaledSize", JsonNode::asLong),
                readProperty(map, "relativeTo", jsonNode -> cameraRelativeToMap.get(jsonNode.asText())),
                readProperty(map, "position", jsonNode -> Arrays.asList(jsonNode.get(0).asDouble(), jsonNode.get(1).asDouble())),
                readProperty(map, "rotation", JsonNode::asDouble),
                readProperty(map, "zoom", JsonNode::asLong),
                readProperty(map, "bgVideo", JsonNode::asText),
                readProperty(map, "loopVideo", jsonNode -> toggleMap.get(jsonNode.asText())),
                readProperty(map, "vidOffset", JsonNode::asLong),
                readProperty(map, "floorIconOutlines", jsonNode -> toggleMap.get(jsonNode.asText())),
                readProperty(map, "stickToFloors", jsonNode -> toggleMap.get(jsonNode.asText())),
                readProperty(map, "planetEase", jsonNode -> easeMap.get(jsonNode.asText())),
                readProperty(map, "planetEaseParts", JsonNode::asLong),
                readProperty(map, "legacyFlash", JsonNode::asBoolean),
                readProperty(map, "legacySpriteTiles", JsonNode::asBoolean),
                new HashMap<>());

        levelSetting.getUnknownProperties().putAll(map);

        return levelSetting;
    }

    private static <R> R readProperty(Map<String, JsonNode> map, String name, Function<? super JsonNode, ? extends R> mapper) {
        JsonNode node = map.remove(name);
        if (node != null) {
            return mapper.apply(node);
        }
        return null;
    }

    public static void write(StringBuilder sb, LevelSetting levelSetting) {
        sb.append("\t\"settings\": \n\t{\n");

        boolean f = true; // isFirst
        f = writeProperty(sb, levelSetting, f, "version", LevelSetting::getVersion);
        f = writeProperty(sb, levelSetting, f, "artist", LevelSetting::getArtist);
        f = writeProperty(sb, levelSetting, f, "specialArtistType", LevelSetting::getSpecialArtistType);
        f = writeProperty(sb, levelSetting, f, "artistPermission", LevelSetting::getArtistPermission);
        f = writeProperty(sb, levelSetting, f, "song", LevelSetting::getSong);
        f = writeProperty(sb, levelSetting, f, "author", LevelSetting::getAuthor);
        f = writeProperty(sb, levelSetting, f, "separateCountdownTime", LevelSetting::getSeparateCountdownTime, Toggle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "previewImage", LevelSetting::getPreviewImage);
        f = writeProperty(sb, levelSetting, f, "previewIcon", LevelSetting::getPreviewIcon);
        f = writeProperty(sb, levelSetting, f, "previewIconColor", LevelSetting::getPreviewIconColor);
        f = writeProperty(sb, levelSetting, f, "previewSongStart", LevelSetting::getPreviewSongStart);
        f = writeProperty(sb, levelSetting, f, "previewSongDuration", LevelSetting::getPreviewSongDuration);
        f = writeProperty(sb, levelSetting, f, "seizureWarning", LevelSetting::getSeizureWarning, Toggle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "levelDesc", LevelSetting::getLevelDesc);
        f = writeProperty(sb, levelSetting, f, "levelTags", LevelSetting::getLevelTags);
        f = writeProperty(sb, levelSetting, f, "artistLinks", LevelSetting::getArtistLinks);
        f = writeProperty(sb, levelSetting, f, "difficulty", LevelSetting::getDifficulty);
        f = writeProperty(sb, levelSetting, f, "songFilename", LevelSetting::getSongFilename);
        f = writeProperty(sb, levelSetting, f, "bpm", LevelSetting::getBpm);
        f = writeProperty(sb, levelSetting, f, "volume", LevelSetting::getVolume);
        f = writeProperty(sb, levelSetting, f, "offset", LevelSetting::getOffset);
        f = writeProperty(sb, levelSetting, f, "pitch", LevelSetting::getPitch);
        f = writeProperty(sb, levelSetting, f, "hitsound", LevelSetting::getHitsound, HitSound::getJsonName);
        f = writeProperty(sb, levelSetting, f, "hitsoundVolume", LevelSetting::getHitsoundVolume);
        f = writeProperty(sb, levelSetting, f, "countdownTicks", LevelSetting::getCountdownTicks);
        f = writeProperty(sb, levelSetting, f, "trackColorType", LevelSetting::getTrackColorType, TrackColorType::getJsonName);
        f = writeProperty(sb, levelSetting, f, "trackColor", LevelSetting::getTrackColor);
        f = writeProperty(sb, levelSetting, f, "secondaryTrackColor", LevelSetting::getSecondaryTrackColor);
        f = writeProperty(sb, levelSetting, f, "trackColorAnimDuration", LevelSetting::getTrackColorAnimDuration);
        f = writeProperty(sb, levelSetting, f, "trackColorPulse", LevelSetting::getTrackColorPulse, TrackColorPulse::getJsonName);
        f = writeProperty(sb, levelSetting, f, "trackPulseLength", LevelSetting::getTrackPulseLength);
        f = writeProperty(sb, levelSetting, f, "trackStyle", LevelSetting::getTrackStyle, TrackStyle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "trackAnimation", LevelSetting::getTrackAnimation, TrackAnimation::getJsonName);
        f = writeProperty(sb, levelSetting, f, "beatsAhead", LevelSetting::getBeatsAhead);
        f = writeProperty(sb, levelSetting, f, "trackDisappearAnimation", LevelSetting::getTrackDisappearAnimation,  TrackDisappearAnimation::getJsonName);
        f = writeProperty(sb, levelSetting, f, "beatsBehind", LevelSetting::getBeatsBehind);
        f = writeProperty(sb, levelSetting, f, "backgroundColor", LevelSetting::getBackgroundColor);
        f = writeProperty(sb, levelSetting, f, "showDefaultBGIfNoImage", LevelSetting::getShowDefaultBGIfNoImage, Toggle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "bgImage", LevelSetting::getBgImage);
        f = writeProperty(sb, levelSetting, f, "bgImageColor", LevelSetting::getBgImageColor);
        f = writeProperty(sb, levelSetting, f, "parallax", LevelSetting::getParallax);
        f = writeProperty(sb, levelSetting, f, "bgDisplayMode", LevelSetting::getBgDisplayMode, BGDisplayModeType::getJsonName);
        f = writeProperty(sb, levelSetting, f, "lockRot", LevelSetting::getLockRot, Toggle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "loopBG", LevelSetting::getLoopBG, Toggle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "unscaledSize", LevelSetting::getUnscaledSize);
        f = writeProperty(sb, levelSetting, f, "relativeTo", LevelSetting::getRelativeTo, CameraRelativeTo::getJsonName);
        f = writeProperty(sb, levelSetting, f, "position", LevelSetting::getPosition);
        f = writeProperty(sb, levelSetting, f, "rotation", LevelSetting::getRotation);
        f = writeProperty(sb, levelSetting, f, "zoom", LevelSetting::getZoom);
        f = writeProperty(sb, levelSetting, f, "bgVideo", LevelSetting::getBgVideo);
        f = writeProperty(sb, levelSetting, f, "loopVideo", LevelSetting::getLoopVideo, Toggle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "vidOffset", LevelSetting::getVidOffset);
        f = writeProperty(sb, levelSetting, f, "floorIconOutlines", LevelSetting::getFloorIconOutlines, Toggle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "stickToFloors", LevelSetting::getStickToFloors, Toggle::getJsonName);
        f = writeProperty(sb, levelSetting, f, "planetEase", LevelSetting::getPlanetEase, Ease::getJsonName);
        f = writeProperty(sb, levelSetting, f, "planetEaseParts", LevelSetting::getPlanetEaseParts);
        f = writeProperty(sb, levelSetting, f, "legacyFlash", LevelSetting::getLegacyFlash);
        f = writeProperty(sb, levelSetting, f, "legacySpriteTiles", LevelSetting::getLegacySpriteTiles);

        for (Map.Entry<String, Object> entry : levelSetting.getUnknownProperties().entrySet()) {
            f = writeProperty(sb, f, entry.getKey(), entry.getValue());
        }

        sb.append("\n\t}");
    }

    private static <T> boolean writeProperty(StringBuilder sb, LevelSetting levelSetting, boolean isFirst, String name, Function<? super LevelSetting, T> mapper, Function<T, ?> mapper2) {
        return writeProperty(sb, levelSetting, isFirst, name, setting -> {
            T value = mapper.apply(setting);
            return value != null ? mapper2.apply(value) : null;
        });
    }

    private static boolean writeProperty(StringBuilder sb, LevelSetting levelSetting, boolean isFirst, String name, Function<? super LevelSetting, ?> mapper) {
        Object obj = mapper.apply(levelSetting);
        return writeProperty(sb, isFirst, name, obj);
    }

    private static boolean writeProperty(StringBuilder sb, boolean isFirst, String name, Object value) {
        if (value != null) {
            if (!isFirst) sb.append(", \n");
            sb.append("\t\t\"").append(name).append("\": ");
            StringJsonUtil.writeVar(sb, value);
            return false;
        }
        return true;
    }


}
