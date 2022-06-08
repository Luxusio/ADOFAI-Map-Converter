package io.luxus.lib.adofai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.helper.JsonPropertyReader;
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

    public static LevelSetting.Builder read(JsonNode node) {
        Map<String, JsonNode> map = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> it = node.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> field = it.next();
            map.put(field.getKey(), field.getValue());
        }
        map.remove("requiredMods"); // removed

        JsonPropertyReader reader = new JsonPropertyReader(map);

        LevelSetting.Builder builder = new LevelSetting.Builder();
        reader.read("version", builder::version, JsonNode::asLong);
        reader.read("artist", builder::artist, JsonNode::asText);
        reader.read("specialArtistType", builder::specialArtistType, JsonNode::asText);
        reader.read("artistPermission", builder::artistPermission, JsonNode::asText);
        reader.read("song", builder::song, JsonNode::asText);
        reader.read("author", builder::author, JsonNode::asText);
        reader.read("separateCountdownTime", builder::separateCountdownTime, JsonNode::asText, toggleMap::get);
        reader.read("previewImage", builder::previewImage, JsonNode::asText);
        reader.read("previewIcon", builder::previewIcon, JsonNode::asText);
        reader.read("previewIconColor", builder::previewIconColor, JsonNode::asText);
        reader.read("previewSongStart", builder::previewSongStart, JsonNode::asLong);
        reader.read("previewSongDuration", builder::previewSongDuration, JsonNode::asLong);
        reader.read("seizureWarning", builder::seizureWarning, JsonNode::asText, toggleMap::get);
        reader.read("levelDesc", builder::levelDesc, JsonNode::asText);
        reader.read("levelTags", builder::levelTags, JsonNode::asText);
        reader.read("artistLinks", builder::artistLinks, JsonNode::asText);
        reader.read("difficulty", builder::difficulty, JsonNode::asLong);
        reader.read("songFilename", builder::songFilename, JsonNode::asText);
        reader.read("bpm", builder::bpm, JsonNode::asDouble);
        reader.read("volume", builder::volume, JsonNode::asLong);
        reader.read("offset", builder::offset, JsonNode::asLong);
        reader.read("pitch", builder::pitch, JsonNode::asLong);
        reader.read("hitsound", builder::hitsound, JsonNode::asText, hitSoundMap::get);
        reader.read("hitsoundVolume", builder::hitsoundVolume, JsonNode::asLong);
        reader.read("countdownTicks", builder::countdownTicks, JsonNode::asLong);
        reader.read("trackColorType", builder::trackColorType, JsonNode::asText, trackColorTypeMap::get);
        reader.read("trackColor", builder::trackColor, JsonNode::asText);
        reader.read("secondaryTrackColor", builder::secondaryTrackColor, JsonNode::asText);
        reader.read("trackColorAnimDuration", builder::trackColorAnimDuration, JsonNode::asDouble);
        reader.read("trackColorPulse", builder::trackColorPulse, JsonNode::asText, trackColorPulseMap::get);
        reader.read("trackPulseLength", builder::trackPulseLength, JsonNode::asLong);
        reader.read("trackStyle", builder::trackStyle, JsonNode::asText, trackStyleMap::get);
        reader.read("trackAnimation", builder::trackAnimation, JsonNode::asText, trackAnimationMap::get);
        reader.read("beatsAhead", builder::beatsAhead, JsonNode::asLong);
        reader.read("trackDisappearAnimation", builder::trackDisappearAnimation, JsonNode::asText, trackDisappearAnimationMap::get);
        reader.read("beatsBehind", builder::beatsBehind, JsonNode::asLong);
        reader.read("backgroundColor", builder::backgroundColor, JsonNode::asText);
        reader.read("showDefaultBGIfNoImage", builder::showDefaultBGIfNoImage, JsonNode::asText, toggleMap::get);
        reader.read("bgImage", builder::bgImage, JsonNode::asText);
        reader.read("bgImageColor", builder::bgImageColor, JsonNode::asText);
        reader.read("parallax", builder::parallax, nodeToXYListFunc(JsonNode::asLong));
        reader.read("bgDisplayMode", builder::bgDisplayMode, JsonNode::asText, bgDisplayModeTypeMap::get);
        reader.read("lockRot", builder::lockRot, JsonNode::asText, toggleMap::get);
        reader.read("loopBG", builder::loopBG, JsonNode::asText, toggleMap::get);
        reader.read("unscaledSize", builder::unscaledSize, JsonNode::asLong);
        reader.read("relativeTo", builder::relativeTo, JsonNode::asText, cameraRelativeToMap::get);
        reader.read("position", builder::position, nodeToXYListFunc(JsonNode::asDouble));
        reader.read("rotation", builder::rotation, JsonNode::asDouble);
        reader.read("zoom", builder::zoom, JsonNode::asLong);
        reader.read("bgVideo", builder::bgVideo, JsonNode::asText);
        reader.read("loopVideo", builder::loopVideo, JsonNode::asText, toggleMap::get);
        reader.read("vidOffset", builder::vidOffset, JsonNode::asLong);
        reader.read("floorIconOutlines", builder::floorIconOutlines, JsonNode::asText, toggleMap::get);
        reader.read("stickToFloors", builder::stickToFloors, JsonNode::asText, toggleMap::get);
        reader.read("planetEase", builder::planetEase, JsonNode::asText, easeMap::get);
        reader.read("planetEaseParts", builder::planetEaseParts, JsonNode::asLong);
        reader.read("customClass", builder::customClass, JsonNode::asText);
        reader.read("legacyFlash", builder::legacyFlash, JsonNode::asBoolean);
        reader.read("legacySpriteTiles", builder::legacySpriteTiles, JsonNode::asBoolean);
        builder.unknownProperties(reader.getPropertyMap());

        return builder;
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

        for (Map.Entry<String, JsonNode> entry : s.getUnknownProperties().entrySet()) {
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
