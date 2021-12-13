package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.type.*;
import io.luxus.lib.adofai.util.StringJsonUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static io.luxus.lib.adofai.parser.ActionFactory.*;
import static io.luxus.lib.adofai.util.StringJsonUtil.*;

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
                readProperty(map, "separateCountdownTime", JsonNode::asText, toggleMap::get),
                readProperty(map, "previewImage", JsonNode::asText),
                readProperty(map, "previewIcon", JsonNode::asText),
                readProperty(map, "previewIconColor", JsonNode::asText),
                readProperty(map, "previewSongStart", JsonNode::asLong),
                readProperty(map, "previewSongDuration", JsonNode::asLong),
                readProperty(map, "seizureWarning", JsonNode::asText, toggleMap::get),
                readProperty(map, "levelDesc", JsonNode::asText),
                readProperty(map, "levelTags", JsonNode::asText),
                readProperty(map, "artistLinks", JsonNode::asText),
                readProperty(map, "difficulty", JsonNode::asLong),
                readProperty(map, "requiredMods", jsonNode -> nodeToList(jsonNode, JsonNode::asText)),
                readProperty(map, "songFilename", JsonNode::asText),
                readProperty(map, "bpm", JsonNode::asDouble),
                readProperty(map, "volume", JsonNode::asLong),
                readProperty(map, "offset", JsonNode::asLong),
                readProperty(map, "pitch", JsonNode::asLong),
                readProperty(map, "hitsound", JsonNode::asText, hitSoundMap::get),
                readProperty(map, "hitsoundVolume", JsonNode::asLong),
                readProperty(map, "countdownTicks", JsonNode::asLong),
                readProperty(map, "trackColorType", JsonNode::asText, trackColorTypeMap::get),
                readProperty(map, "trackColor", JsonNode::asText),
                readProperty(map, "secondaryTrackColor", JsonNode::asText),
                readProperty(map, "trackColorAnimDuration", JsonNode::asDouble),
                readProperty(map, "trackColorPulse", JsonNode::asText, trackColorPulseMap::get),
                readProperty(map, "trackPulseLength", JsonNode::asLong),
                readProperty(map, "trackStyle", JsonNode::asText, trackStyleMap::get),
                readProperty(map, "trackAnimation", JsonNode::asText, trackAnimationMap::get),
                readProperty(map, "beatsAhead", JsonNode::asLong),
                readProperty(map, "trackDisappearAnimation", JsonNode::asText, trackDisappearAnimationMap::get),
                readProperty(map, "beatsBehind", JsonNode::asLong),
                readProperty(map, "backgroundColor", JsonNode::asText),
                readProperty(map, "showDefaultBGIfNoImage", JsonNode::asText, toggleMap::get),
                readProperty(map, "bgImage", JsonNode::asText),
                readProperty(map, "bgImageColor", JsonNode::asText),
                readProperty(map, "parallax", nodeToXYListFunc(JsonNode::asLong)),
                readProperty(map, "bgDisplayMode", JsonNode::asText, bgDisplayModeTypeMap::get),
                readProperty(map, "lockRot", JsonNode::asText, toggleMap::get),
                readProperty(map, "loopBG", JsonNode::asText, toggleMap::get),
                readProperty(map, "unscaledSize", JsonNode::asLong),
                readProperty(map, "relativeTo", JsonNode::asText, cameraRelativeToMap::get),
                readProperty(map, "position", nodeToXYListFunc(JsonNode::asDouble)),
                readProperty(map, "rotation", JsonNode::asDouble),
                readProperty(map, "zoom", JsonNode::asLong),
                readProperty(map, "bgVideo", JsonNode::asText),
                readProperty(map, "loopVideo", JsonNode::asText, toggleMap::get),
                readProperty(map, "vidOffset", JsonNode::asLong),
                readProperty(map, "floorIconOutlines", JsonNode::asText, toggleMap::get),
                readProperty(map, "stickToFloors", JsonNode::asText, toggleMap::get),
                readProperty(map, "planetEase", JsonNode::asText, easeMap::get),
                readProperty(map, "planetEaseParts", JsonNode::asLong),
                readProperty(map, "customClass", JsonNode::asText),
                readProperty(map, "legacyFlash", JsonNode::asBoolean),
                readProperty(map, "legacySpriteTiles", JsonNode::asBoolean),
                new HashMap<>());

        levelSetting.getUnknownProperties().putAll(map);

        return levelSetting;
    }

    public static void write(StringBuilder sb, LevelSetting s) {
        sb.append("\t\"settings\": \n\t{\n");

        boolean f = true; // isFirst
        f = writeProperty(sb, f, "version", s.getVersion());
        f = writeProperty(sb, f, "artist", s.getArtist());
        f = writeProperty(sb, f, "specialArtistType", s.getSpecialArtistType());
        f = writeProperty(sb, f, "artistPermission", s.getArtistPermission());
        f = writeProperty(sb, f, "song", s.getSong());
        f = writeProperty(sb, f, "author", s.getAuthor());
        f = writeProperty(sb, f, "separateCountdownTime", s.getSeparateCountdownTime(), Toggle::getJsonName);
        f = writeProperty(sb, f, "previewImage", s.getPreviewImage());
        f = writeProperty(sb, f, "previewIcon", s.getPreviewIcon());
        f = writeProperty(sb, f, "previewIconColor", s.getPreviewIconColor());
        f = writeProperty(sb, f, "previewSongStart", s.getPreviewSongStart());
        f = writeProperty(sb, f, "previewSongDuration", s.getPreviewSongDuration());
        f = writeProperty(sb, f, "seizureWarning", s.getSeizureWarning(), Toggle::getJsonName);
        f = writeProperty(sb, f, "levelDesc", s.getLevelDesc());
        f = writeProperty(sb, f, "levelTags", s.getLevelTags());
        f = writeProperty(sb, f, "artistLinks", s.getArtistLinks());
        f = writeProperty(sb, f, "difficulty", s.getDifficulty());
        f = writeProperty(sb, f, "requiredMods", s.getRequiredMods());
        f = writeProperty(sb, f, "songFilename", s.getSongFilename());
        f = writeProperty(sb, f, "bpm", s.getBpm());
        f = writeProperty(sb, f, "volume", s.getVolume());
        f = writeProperty(sb, f, "offset", s.getOffset());
        f = writeProperty(sb, f, "pitch", s.getPitch());
        f = writeProperty(sb, f, "hitsound", s.getHitsound(), HitSound::getJsonName);
        f = writeProperty(sb, f, "hitsoundVolume", s.getHitsoundVolume());
        f = writeProperty(sb, f, "countdownTicks", s.getCountdownTicks());
        f = writeProperty(sb, f, "trackColorType", s.getTrackColorType(), TrackColorType::getJsonName);
        f = writeProperty(sb, f, "trackColor", s.getTrackColor());
        f = writeProperty(sb, f, "secondaryTrackColor", s.getSecondaryTrackColor());
        f = writeProperty(sb, f, "trackColorAnimDuration", s.getTrackColorAnimDuration());
        f = writeProperty(sb, f, "trackColorPulse", s.getTrackColorPulse(), TrackColorPulse::getJsonName);
        f = writeProperty(sb, f, "trackPulseLength", s.getTrackPulseLength());
        f = writeProperty(sb, f, "trackStyle", s.getTrackStyle(), TrackStyle::getJsonName);
        f = writeProperty(sb, f, "trackAnimation", s.getTrackAnimation(), TrackAnimation::getJsonName);
        f = writeProperty(sb, f, "beatsAhead", s.getBeatsAhead());
        f = writeProperty(sb, f, "trackDisappearAnimation", s.getTrackDisappearAnimation(),  TrackDisappearAnimation::getJsonName);
        f = writeProperty(sb, f, "beatsBehind", s.getBeatsBehind());
        f = writeProperty(sb, f, "backgroundColor", s.getBackgroundColor());
        f = writeProperty(sb, f, "showDefaultBGIfNoImage", s.getShowDefaultBGIfNoImage(), Toggle::getJsonName);
        f = writeProperty(sb, f, "bgImage", s.getBgImage());
        f = writeProperty(sb, f, "bgImageColor", s.getBgImageColor());
        f = writeProperty(sb, f, "parallax", s.getParallax());
        f = writeProperty(sb, f, "bgDisplayMode", s.getBgDisplayMode(), BGDisplayModeType::getJsonName);
        f = writeProperty(sb, f, "lockRot", s.getLockRot(), Toggle::getJsonName);
        f = writeProperty(sb, f, "loopBG", s.getLoopBG(), Toggle::getJsonName);
        f = writeProperty(sb, f, "unscaledSize", s.getUnscaledSize());
        f = writeProperty(sb, f, "relativeTo", s.getRelativeTo(), CameraRelativeTo::getJsonName);
        f = writeProperty(sb, f, "position", s.getPosition());
        f = writeProperty(sb, f, "rotation", s.getRotation());
        f = writeProperty(sb, f, "zoom", s.getZoom());
        f = writeProperty(sb, f, "bgVideo", s.getBgVideo());
        f = writeProperty(sb, f, "loopVideo", s.getLoopVideo(), Toggle::getJsonName);
        f = writeProperty(sb, f, "vidOffset", s.getVidOffset());
        f = writeProperty(sb, f, "floorIconOutlines", s.getFloorIconOutlines(), Toggle::getJsonName);
        f = writeProperty(sb, f, "stickToFloors", s.getStickToFloors(), Toggle::getJsonName);
        f = writeProperty(sb, f, "planetEase", s.getPlanetEase(), Ease::getJsonName);
        f = writeProperty(sb, f, "planetEaseParts", s.getPlanetEaseParts());
        f = writeProperty(sb, f, "customClass", s.getCustomClass());
        f = writeProperty(sb, f, "legacyFlash", s.getLegacyFlash());
        f = writeProperty(sb, f, "legacySpriteTiles", s.getLegacySpriteTiles());

        for (Map.Entry<String, Object> entry : s.getUnknownProperties().entrySet()) {
            f = writeProperty(sb, f, entry.getKey(), entry.getValue());
        }

        sb.append("\n\t}");
    }

    private static <T> boolean writeProperty(StringBuilder sb, boolean isFirst, String name, T value, Function<T, ?> mapper) {
        return Optional.ofNullable(value)
                .map(mapper)

                .map(o -> writeProperty(sb, isFirst, name, o)).orElse(isFirst);
    }

    private static boolean writeProperty(StringBuilder sb, boolean isFirst, String name, Object value) {
        if (value != null) {
            if (!isFirst) sb.append(", \n");
            sb.append("\t\t\"").append(name).append("\": ");
            StringJsonUtil.writeVar(sb, value);
            return false;
        }
        return isFirst;
    }


}
