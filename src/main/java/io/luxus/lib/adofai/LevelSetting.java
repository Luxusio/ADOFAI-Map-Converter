package io.luxus.lib.adofai;

import io.luxus.lib.adofai.type.*;
import lombok.*;

import java.util.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "")
public class LevelSetting {

    private Long version = 5L;
    private String artist = "Artist";
    private String specialArtistType = "None";
    private String artistPermission = "";
    private String song = "Song";
    private String author = "Author";
    private Toggle separateCountdownTime = Toggle.ENABLED;
    private String previewImage = "";
    private String previewIcon = "";
    private String previewIconColor = "003f52";
    private Long previewSongStart = 0L;
    private Long previewSongDuration = 10L;
    private Toggle seizureWarning = Toggle.DISABLED;
    private String levelDesc = "Say something about your level!";
    private String levelTags = "";
    private String artistLinks = "";
    private Long difficulty = 1L;
    private List<String> requiredMods = new ArrayList<>();
    private String songFilename = "";
    private Double bpm = 100.0;
    private Long volume = 100L;
    private Long offset = 0L;
    private Long pitch = 100L;
    private HitSound hitsound = HitSound.KICK;
    private Long hitsoundVolume = 100L;
    private Long countdownTicks = 4L;
    private TrackColorType trackColorType = TrackColorType.SINGLE;
    private String trackColor = "debb7b";
    private String secondaryTrackColor = "ffffff";
    private Double trackColorAnimDuration = 2.0;
    private TrackColorPulse trackColorPulse = TrackColorPulse.NONE;
    private Long trackPulseLength = 10L;
    private TrackStyle trackStyle = TrackStyle.STANDARD;
    private TrackAnimation trackAnimation = TrackAnimation.NONE;
    private Long beatsAhead = 3L;
    private TrackDisappearAnimation trackDisappearAnimation = TrackDisappearAnimation.NONE;
    private Long beatsBehind = 4L;
    private String backgroundColor = "000000";
    private Toggle showDefaultBGIfNoImage = Toggle.ENABLED;
    private String bgImage = "";
    private String bgImageColor = "ffffff";
    private List<Long> parallax = Arrays.asList(100L, 100L);
    private BGDisplayModeType bgDisplayMode = BGDisplayModeType.FIT_TO_SCREEN;
    private Toggle lockRot = Toggle.DISABLED;
    private Toggle loopBG = Toggle.DISABLED;
    private Long unscaledSize = 100L;
    private CameraRelativeTo relativeTo = CameraRelativeTo.PLAYER;
    private List<Double> position = Arrays.asList(0.0, 0.0);
    private Double rotation = 0.0;
    private Long zoom = 100L;
    private String bgVideo = "";
    private Toggle loopVideo = Toggle.DISABLED;
    private Long vidOffset = 0L;
    private Toggle floorIconOutlines = Toggle.DISABLED;
    private Toggle stickToFloors = Toggle.DISABLED;
    private Ease planetEase = Ease.LINEAR;
    private Long planetEaseParts = 1L;
    private String customClass = "";
    private Boolean legacyFlash = false;
    private Boolean legacySpriteTiles = false;
    private Map<String, Object> unknownProperties = new HashMap<>();

}
