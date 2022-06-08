package io.luxus.lib.adofai;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.type.*;
import io.luxus.lib.adofai.util.ListUtil;
import lombok.*;

import java.util.*;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelSetting {

    private final Long version;
    private final String artist;
    private final String specialArtistType;
    private final String artistPermission;
    private final String song;
    private final String author;
    private final Toggle separateCountdownTime;
    private final String previewImage;
    private final String previewIcon;
    private final String previewIconColor;
    private final Long previewSongStart;
    private final Long previewSongDuration;
    private final Toggle seizureWarning;
    private final String levelDesc;
    private final String levelTags;
    private final String artistLinks;
    private final Long difficulty;
    private final String songFilename;
    private final Double bpm;
    private final Long volume;
    private final Long offset;
    private final Long pitch;
    private final HitSound hitsound;
    private final Long hitsoundVolume;
    private final Long countdownTicks;
    private final TrackColorType trackColorType;
    private final String trackColor;
    private final String secondaryTrackColor;
    private final Double trackColorAnimDuration;
    private final TrackColorPulse trackColorPulse;
    private final Long trackPulseLength;
    private final TrackStyle trackStyle;
    private final TrackAnimation trackAnimation;
    private final Long beatsAhead;
    private final TrackDisappearAnimation trackDisappearAnimation;
    private final Long beatsBehind;
    private final String backgroundColor;
    private final Toggle showDefaultBGIfNoImage;
    private final String bgImage;
    private final String bgImageColor;
    private final List<Long> parallax;
    private final BGDisplayModeType bgDisplayMode;
    private final Toggle lockRot;
    private final Toggle loopBG;
    private final Long unscaledSize;
    private final CameraRelativeTo relativeTo;
    private final List<Double> position;
    private final Double rotation;
    private final Long zoom;
    private final String bgVideo;
    private final Toggle loopVideo;
    private final Long vidOffset;
    private final Toggle floorIconOutlines;
    private final Toggle stickToFloors;
    private final Ease planetEase;
    private final Long planetEaseParts;
    private final String customClass;
    private final Boolean legacyFlash;
    private final Boolean legacySpriteTiles;
    private final Map<String, JsonNode> unknownProperties;

    @Getter
    @ToString
    public static final class Builder {

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
        private Map<String, JsonNode> unknownProperties = new HashMap<>();

        /**
         * set all parameter with given setting
         *
         * @param src source setting
         * @return self
         */
        public Builder from(LevelSetting src) {
            return self()
                    .version(src.version)
                    .artist(src.artist)
                    .specialArtistType(src.specialArtistType)
                    .artistPermission(src.artistPermission)
                    .song(src.song)
                    .author(src.author)
                    .separateCountdownTime(src.separateCountdownTime)
                    .previewImage(src.previewImage)
                    .previewIcon(src.previewIcon)
                    .previewIconColor(src.previewIconColor)
                    .previewSongStart(src.previewSongStart)
                    .previewSongDuration(src.previewSongDuration)
                    .seizureWarning(src.seizureWarning)
                    .levelDesc(src.levelDesc)
                    .levelTags(src.levelTags)
                    .artistLinks(src.artistLinks)
                    .difficulty(src.difficulty)
                    .songFilename(src.songFilename)
                    .bpm(src.bpm)
                    .volume(src.volume)
                    .offset(src.offset)
                    .pitch(src.pitch)
                    .hitsound(src.hitsound)
                    .hitsoundVolume(src.hitsoundVolume)
                    .countdownTicks(src.countdownTicks)
                    .trackColorType(src.trackColorType)
                    .trackColor(src.trackColor)
                    .secondaryTrackColor(src.secondaryTrackColor)
                    .trackColorAnimDuration(src.trackColorAnimDuration)
                    .trackColorPulse(src.trackColorPulse)
                    .trackPulseLength(src.trackPulseLength)
                    .trackStyle(src.trackStyle)
                    .trackAnimation(src.trackAnimation)
                    .beatsAhead(src.beatsAhead)
                    .trackDisappearAnimation(src.trackDisappearAnimation)
                    .beatsBehind(src.beatsBehind)
                    .backgroundColor(src.backgroundColor)
                    .showDefaultBGIfNoImage(src.showDefaultBGIfNoImage)
                    .bgImage(src.bgImage)
                    .bgImageColor(src.bgImageColor)
                    .parallax(src.parallax)
                    .bgDisplayMode(src.bgDisplayMode)
                    .lockRot(src.lockRot)
                    .loopBG(src.loopBG)
                    .unscaledSize(src.unscaledSize)
                    .relativeTo(src.relativeTo)
                    .position(src.position)
                    .rotation(src.rotation)
                    .zoom(src.zoom)
                    .bgVideo(src.bgVideo)
                    .loopVideo(src.loopVideo)
                    .vidOffset(src.vidOffset)
                    .floorIconOutlines(src.floorIconOutlines)
                    .stickToFloors(src.stickToFloors)
                    .planetEase(src.planetEase)
                    .planetEaseParts(src.planetEaseParts)
                    .customClass(src.customClass)
                    .legacyFlash(src.legacyFlash)
                    .legacySpriteTiles(src.legacySpriteTiles)
                    .unknownProperties(src.unknownProperties);
        }

        /**
         * build LevelSetting with builder
         *
         * @return Built LevelSetting
         */
        public LevelSetting build() {
            return new LevelSetting(version, artist, specialArtistType, artistPermission, song, author, separateCountdownTime,
                    previewImage, previewIcon, previewIconColor, previewSongStart, previewSongDuration, seizureWarning,
                    levelDesc, levelTags, artistLinks, difficulty, songFilename, bpm, volume, offset, pitch,
                    hitsound, hitsoundVolume, countdownTicks, trackColorType, trackColor, secondaryTrackColor,
                    trackColorAnimDuration, trackColorPulse, trackPulseLength, trackStyle, trackAnimation, beatsAhead,
                    trackDisappearAnimation, beatsBehind, backgroundColor, showDefaultBGIfNoImage, bgImage, bgImageColor,
                    parallax, bgDisplayMode, lockRot, loopBG, unscaledSize, relativeTo, position, rotation, zoom, bgVideo,
                    loopVideo, vidOffset, floorIconOutlines, stickToFloors, planetEase, planetEaseParts, customClass,
                    legacyFlash, legacySpriteTiles, unknownProperties);
        }

        /**
         * return self
         *
         * @return self
         */
        public Builder self() {
            return this;
        }

        /**
         * setter of version
         *
         * @param version version of LevelSetting
         * @return self
         * @throws NullPointerException when version is null
         */
        public Builder version(Long version) {
            Objects.requireNonNull(version);
            this.version = version;
            return self();
        }

        /**
         * setter of artist
         *
         * @param artist artist of LevelSetting
         * @return self
         * @throws NullPointerException when artist is null
         */
        public Builder artist(String artist) {
            Objects.requireNonNull(artist);
            this.artist = artist;
            return self();
        }

        /**
         * setter of specialArtistType
         *
         * @param specialArtistType specialArtistType of LevelSetting
         * @return self
         * @throws NullPointerException when specialArtistType is null
         */
        public Builder specialArtistType(String specialArtistType) {
            Objects.requireNonNull(specialArtistType);
            this.specialArtistType = specialArtistType;
            return self();
        }

        /**
         * setter of artistPermission
         *
         * @param artistPermission artistPermission of LevelSetting
         * @return self
         * @throws NullPointerException when artistPermission is null
         */
        public Builder artistPermission(String artistPermission) {
            Objects.requireNonNull(artistPermission);
            this.artistPermission = artistPermission;
            return self();
        }

        /**
         * setter of song
         *
         * @param song song of LevelSetting
         * @return self
         * @throws NullPointerException when song is null
         */
        public Builder song(String song) {
            Objects.requireNonNull(song);
            this.song = song;
            return self();
        }

        /**
         * setter of author
         *
         * @param author author of LevelSetting
         * @return self
         * @throws NullPointerException when author is null
         */
        public Builder author(String author) {
            Objects.requireNonNull(author);
            this.author = author;
            return self();
        }

        /**
         * setter of separateCountdownTime
         *
         * @param separateCountdownTime separateCountdownTime of LevelSetting
         * @return self
         * @throws NullPointerException when separateCountdownTime is null
         */
        public Builder separateCountdownTime(Toggle separateCountdownTime) {
            Objects.requireNonNull(separateCountdownTime);
            this.separateCountdownTime = separateCountdownTime;
            return self();
        }

        /**
         * setter of previewImage
         *
         * @param previewImage previewImage of LevelSetting
         * @return self
         * @throws NullPointerException when previewImage is null
         */
        public Builder previewImage(String previewImage) {
            Objects.requireNonNull(previewImage);
            this.previewImage = previewImage;
            return self();
        }

        /**
         * setter of previewIcon
         *
         * @param previewIcon previewIcon of LevelSetting
         * @return self
         * @throws NullPointerException when previewIcon is null
         */
        public Builder previewIcon(String previewIcon) {
            Objects.requireNonNull(previewIcon);
            this.previewIcon = previewIcon;
            return self();
        }

        /**
         * setter of previewIconColor
         *
         * @param previewIconColor previewIconColor of LevelSetting
         * @return self
         * @throws NullPointerException when previewIconColor is null
         */
        public Builder previewIconColor(String previewIconColor) {
            Objects.requireNonNull(previewIconColor);
            this.previewIconColor = previewIconColor;
            return self();
        }

        /**
         * setter of previewSongStart
         *
         * @param previewSongStart previewSongStart of LevelSetting
         * @return self
         * @throws NullPointerException when previewSongStart is null
         */
        public Builder previewSongStart(Long previewSongStart) {
            Objects.requireNonNull(previewSongStart);
            this.previewSongStart = previewSongStart;
            return self();
        }

        /**
         * setter of previewSongDuration
         *
         * @param previewSongDuration previewSongDuration of LevelSetting
         * @return self
         * @throws NullPointerException when previewSongDuration is null
         */
        public Builder previewSongDuration(Long previewSongDuration) {
            Objects.requireNonNull(previewSongDuration);
            this.previewSongDuration = previewSongDuration;
            return self();
        }

        /**
         * setter of seizureWarning
         *
         * @param seizureWarning seizureWarning of LevelSetting
         * @return self
         * @throws NullPointerException when seizureWarning is null
         */
        public Builder seizureWarning(Toggle seizureWarning) {
            Objects.requireNonNull(seizureWarning);
            this.seizureWarning = seizureWarning;
            return self();
        }

        /**
         * setter of levelDesc
         *
         * @param levelDesc levelDesc of LevelSetting
         * @return self
         * @throws NullPointerException when levelDesc is null
         */
        public Builder levelDesc(String levelDesc) {
            Objects.requireNonNull(levelDesc);
            this.levelDesc = levelDesc;
            return self();
        }

        /**
         * setter of levelTags
         *
         * @param levelTags levelTags of LevelSetting
         * @return self
         * @throws NullPointerException when levelTags is null
         */
        public Builder levelTags(String levelTags) {
            Objects.requireNonNull(levelTags);
            this.levelTags = levelTags;
            return self();
        }

        /**
         * setter of artistLinks
         *
         * @param artistLinks artistLinks of LevelSetting
         * @return self
         * @throws NullPointerException when artistLinks is null
         */
        public Builder artistLinks(String artistLinks) {
            Objects.requireNonNull(artistLinks);
            this.artistLinks = artistLinks;
            return self();
        }

        /**
         * setter of difficulty
         *
         * @param difficulty difficulty of LevelSetting
         * @return self
         * @throws NullPointerException when difficulty is null
         */
        public Builder difficulty(Long difficulty) {
            Objects.requireNonNull(difficulty);
            this.difficulty = difficulty;
            return self();
        }

        /**
         * setter of songFilename
         *
         * @param songFilename songFilename of LevelSetting
         * @return self
         * @throws NullPointerException when songFilename is null
         */
        public Builder songFilename(String songFilename) {
            Objects.requireNonNull(songFilename);
            this.songFilename = songFilename;
            return self();
        }

        /**
         * setter of bpm
         *
         * @param bpm bpm of LevelSetting
         * @return self
         * @throws NullPointerException when bpm is null
         */
        public Builder bpm(Double bpm) {
            Objects.requireNonNull(bpm);
            this.bpm = bpm;
            return self();
        }

        /**
         * setter of volume
         *
         * @param volume volume of LevelSetting
         * @return self
         * @throws NullPointerException when volume is null
         */
        public Builder volume(Long volume) {
            Objects.requireNonNull(volume);
            this.volume = volume;
            return self();
        }

        /**
         * setter of offset
         *
         * @param offset offset of LevelSetting
         * @return self
         * @throws NullPointerException when offset is null
         */
        public Builder offset(Long offset) {
            Objects.requireNonNull(offset);
            this.offset = offset;
            return self();
        }

        /**
         * setter of pitch
         *
         * @param pitch pitch of LevelSetting
         * @return self
         * @throws NullPointerException when pitch is null
         */
        public Builder pitch(Long pitch) {
            Objects.requireNonNull(pitch);
            this.pitch = pitch;
            return self();
        }

        /**
         * setter of hitsound
         *
         * @param hitsound hitsound of LevelSetting
         * @return self
         * @throws NullPointerException when hitsound is null
         */
        public Builder hitsound(HitSound hitsound) {
            Objects.requireNonNull(hitsound);
            this.hitsound = hitsound;
            return self();
        }

        /**
         * setter of hitsoundVolume
         *
         * @param hitsoundVolume hitsoundVolume of LevelSetting
         * @return self
         * @throws NullPointerException when hitsoundVolume is null
         */
        public Builder hitsoundVolume(Long hitsoundVolume) {
            Objects.requireNonNull(hitsoundVolume);
            this.hitsoundVolume = hitsoundVolume;
            return self();
        }

        /**
         * setter of countdownTicks
         *
         * @param countdownTicks countdownTicks of LevelSetting
         * @return self
         * @throws NullPointerException when countdownTicks is null
         */
        public Builder countdownTicks(Long countdownTicks) {
            Objects.requireNonNull(countdownTicks);
            this.countdownTicks = countdownTicks;
            return self();
        }

        /**
         * setter of trackColorType
         *
         * @param trackColorType trackColorType of LevelSetting
         * @return self
         * @throws NullPointerException when trackColorType is null
         */
        public Builder trackColorType(TrackColorType trackColorType) {
            Objects.requireNonNull(trackColorType);
            this.trackColorType = trackColorType;
            return self();
        }

        /**
         * setter of trackColor
         *
         * @param trackColor trackColor of LevelSetting
         * @return self
         * @throws NullPointerException when trackColor is null
         */
        public Builder trackColor(String trackColor) {
            Objects.requireNonNull(trackColor);
            this.trackColor = trackColor;
            return self();
        }

        /**
         * setter of secondaryTrackColor
         *
         * @param secondaryTrackColor secondaryTrackColor of LevelSetting
         * @return self
         * @throws NullPointerException when secondaryTrackColor is null
         */
        public Builder secondaryTrackColor(String secondaryTrackColor) {
            Objects.requireNonNull(secondaryTrackColor);
            this.secondaryTrackColor = secondaryTrackColor;
            return self();
        }

        /**
         * setter of trackColorAnimDuration
         *
         * @param trackColorAnimDuration trackColorAnimDuration of LevelSetting
         * @return self
         * @throws NullPointerException when trackColorAnimDuration is null
         */
        public Builder trackColorAnimDuration(Double trackColorAnimDuration) {
            Objects.requireNonNull(trackColorAnimDuration);
            this.trackColorAnimDuration = trackColorAnimDuration;
            return self();
        }

        /**
         * setter of trackColorPulse
         *
         * @param trackColorPulse trackColorPulse of LevelSetting
         * @return self
         * @throws NullPointerException when trackColorPulse is null
         */
        public Builder trackColorPulse(TrackColorPulse trackColorPulse) {
            Objects.requireNonNull(trackColorPulse);
            this.trackColorPulse = trackColorPulse;
            return self();
        }

        /**
         * setter of trackPulseLength
         *
         * @param trackPulseLength trackPulseLength of LevelSetting
         * @return self
         * @throws NullPointerException when trackPulseLength is null
         */
        public Builder trackPulseLength(Long trackPulseLength) {
            Objects.requireNonNull(trackPulseLength);
            this.trackPulseLength = trackPulseLength;
            return self();
        }

        /**
         * setter of trackStyle
         *
         * @param trackStyle trackStyle of LevelSetting
         * @return self
         * @throws NullPointerException when trackStyle is null
         */
        public Builder trackStyle(TrackStyle trackStyle) {
            Objects.requireNonNull(trackStyle);
            this.trackStyle = trackStyle;
            return self();
        }

        /**
         * setter of trackAnimation
         *
         * @param trackAnimation trackAnimation of LevelSetting
         * @return self
         * @throws NullPointerException when trackAnimation is null
         */
        public Builder trackAnimation(TrackAnimation trackAnimation) {
            Objects.requireNonNull(trackAnimation);
            this.trackAnimation = trackAnimation;
            return self();
        }

        /**
         * setter of beatsAhead
         *
         * @param beatsAhead beatsAhead of LevelSetting
         * @return self
         * @throws NullPointerException when beatsAhead is null
         */
        public Builder beatsAhead(Long beatsAhead) {
            Objects.requireNonNull(beatsAhead);
            this.beatsAhead = beatsAhead;
            return self();
        }

        /**
         * setter of trackDisappearAnimation
         *
         * @param trackDisappearAnimation trackDisappearAnimation of LevelSetting
         * @return self
         * @throws NullPointerException when trackDisappearAnimation is null
         */
        public Builder trackDisappearAnimation(TrackDisappearAnimation trackDisappearAnimation) {
            Objects.requireNonNull(trackDisappearAnimation);
            this.trackDisappearAnimation = trackDisappearAnimation;
            return self();
        }

        /**
         * setter of beatsBehind
         *
         * @param beatsBehind beatsBehind of LevelSetting
         * @return self
         * @throws NullPointerException when beatsBehind is null
         */
        public Builder beatsBehind(Long beatsBehind) {
            Objects.requireNonNull(beatsBehind);
            this.beatsBehind = beatsBehind;
            return self();
        }

        /**
         * setter of backgroundColor
         *
         * @param backgroundColor backgroundColor of LevelSetting
         * @return self
         * @throws NullPointerException when backgroundColor is null
         */
        public Builder backgroundColor(String backgroundColor) {
            Objects.requireNonNull(backgroundColor);
            this.backgroundColor = backgroundColor;
            return self();
        }

        /**
         * setter of showDefaultBGIfNoImage
         *
         * @param showDefaultBGIfNoImage showDefaultBGIfNoImage of LevelSetting
         * @return self
         * @throws NullPointerException when showDefaultBGIfNoImage is null
         */
        public Builder showDefaultBGIfNoImage(Toggle showDefaultBGIfNoImage) {
            Objects.requireNonNull(showDefaultBGIfNoImage);
            this.showDefaultBGIfNoImage = showDefaultBGIfNoImage;
            return self();
        }

        /**
         * setter of bgImage
         *
         * @param bgImage bgImage of LevelSetting
         * @return self
         * @throws NullPointerException when bgImage is null
         */
        public Builder bgImage(String bgImage) {
            Objects.requireNonNull(bgImage);
            this.bgImage = bgImage;
            return self();
        }

        /**
         * setter of bgImageColor
         *
         * @param bgImageColor bgImageColor of LevelSetting
         * @return self
         * @throws NullPointerException when bgImageColor is null
         */
        public Builder bgImageColor(String bgImageColor) {
            Objects.requireNonNull(bgImageColor);
            this.bgImageColor = bgImageColor;
            return self();
        }

        /**
         * setter of parallax
         *
         * @param parallax parallax of LevelSetting
         * @return self
         * @throws NullPointerException when parallax is null
         * @throws IllegalArgumentException when size of parallax is not 2
         */
        public Builder parallax(List<Long> parallax) {
            Objects.requireNonNull(parallax);
            if (parallax.size() != 2) {
                throw new IllegalArgumentException("size of parallax must be 2");
            }
            this.parallax = ListUtil.createNewUnmodifiableList(parallax);
            return self();
        }

        /**
         * setter of bgDisplayMode
         *
         * @param bgDisplayMode bgDisplayMode of LevelSetting
         * @return self
         * @throws NullPointerException when bgDisplayMode is null
         */
        public Builder bgDisplayMode(BGDisplayModeType bgDisplayMode) {
            Objects.requireNonNull(bgDisplayMode);
            this.bgDisplayMode = bgDisplayMode;
            return self();
        }

        /**
         * setter of lockRot
         *
         * @param lockRot lockRot of LevelSetting
         * @return self
         * @throws NullPointerException when lockRot is null
         */
        public Builder lockRot(Toggle lockRot) {
            Objects.requireNonNull(lockRot);
            this.lockRot = lockRot;
            return self();
        }

        /**
         * setter of loopBG
         *
         * @param loopBG loopBG of LevelSetting
         * @return self
         * @throws NullPointerException when loopBG is null
         */
        public Builder loopBG(Toggle loopBG) {
            Objects.requireNonNull(loopBG);
            this.loopBG = loopBG;
            return self();
        }

        /**
         * setter of unscaledSize
         *
         * @param unscaledSize unscaledSize of LevelSetting
         * @return self
         * @throws NullPointerException when unscaledSize is null
         */
        public Builder unscaledSize(Long unscaledSize) {
            Objects.requireNonNull(unscaledSize);
            this.unscaledSize = unscaledSize;
            return self();
        }

        /**
         * setter of relativeTo
         *
         * @param relativeTo relativeTo of LevelSetting
         * @return self
         * @throws NullPointerException when relativeTo is null
         */
        public Builder relativeTo(CameraRelativeTo relativeTo) {
            Objects.requireNonNull(relativeTo);
            this.relativeTo = relativeTo;
            return self();
        }

        /**
         * setter of position
         *
         * @param position position of LevelSetting
         * @return self
         * @throws NullPointerException when position is null
         * @throws IllegalArgumentException when size of position is not 2
         */
        public Builder position(List<Double> position) {
            Objects.requireNonNull(position);
            if (position.size() != 2) {
                throw new IllegalArgumentException("size of position must be 2");
            }
            this.position = ListUtil.createNewUnmodifiableList(position);
            return self();
        }

        /**
         * setter of rotation
         *
         * @param rotation rotation of LevelSetting
         * @return self
         * @throws NullPointerException when rotation is null
         */
        public Builder rotation(Double rotation) {
            Objects.requireNonNull(rotation);
            this.rotation = rotation;
            return self();
        }

        /**
         * setter of zoom
         *
         * @param zoom zoom of LevelSetting
         * @return self
         * @throws NullPointerException when zoom is null
         */
        public Builder zoom(Long zoom) {
            Objects.requireNonNull(zoom);
            this.zoom = zoom;
            return self();
        }

        /**
         * setter of bgVideo
         *
         * @param bgVideo bgVideo of LevelSetting
         * @return self
         * @throws NullPointerException when bgVideo is null
         */
        public Builder bgVideo(String bgVideo) {
            Objects.requireNonNull(bgVideo);
            this.bgVideo = bgVideo;
            return self();
        }

        /**
         * setter of loopVideo
         *
         * @param loopVideo loopVideo of LevelSetting
         * @return self
         * @throws NullPointerException when loopVideo is null
         */
        public Builder loopVideo(Toggle loopVideo) {
            Objects.requireNonNull(loopVideo);
            this.loopVideo = loopVideo;
            return self();
        }

        /**
         * setter of vidOffset
         *
         * @param vidOffset vidOffset of LevelSetting
         * @return self
         * @throws NullPointerException when vidOffset is null
         */
        public Builder vidOffset(Long vidOffset) {
            Objects.requireNonNull(vidOffset);
            this.vidOffset = vidOffset;
            return self();
        }

        /**
         * setter of floorIconOutlines
         *
         * @param floorIconOutlines floorIconOutlines of LevelSetting
         * @return self
         * @throws NullPointerException when floorIconOutlines is null
         */
        public Builder floorIconOutlines(Toggle floorIconOutlines) {
            Objects.requireNonNull(floorIconOutlines);
            this.floorIconOutlines = floorIconOutlines;
            return self();
        }

        /**
         * setter of stickToFloors
         *
         * @param stickToFloors stickToFloors of LevelSetting
         * @return self
         * @throws NullPointerException when stickToFloors is null
         */
        public Builder stickToFloors(Toggle stickToFloors) {
            Objects.requireNonNull(stickToFloors);
            this.stickToFloors = stickToFloors;
            return self();
        }

        /**
         * setter of planetEase
         *
         * @param planetEase planetEase of LevelSetting
         * @return self
         * @throws NullPointerException when planetEase is null
         */
        public Builder planetEase(Ease planetEase) {
            Objects.requireNonNull(planetEase);
            this.planetEase = planetEase;
            return self();
        }

        /**
         * setter of planetEaseParts
         *
         * @param planetEaseParts planetEaseParts of LevelSetting
         * @return self
         * @throws NullPointerException when planetEaseParts is null
         */
        public Builder planetEaseParts(Long planetEaseParts) {
            Objects.requireNonNull(planetEaseParts);
            this.planetEaseParts = planetEaseParts;
            return self();
        }

        /**
         * setter of customClass
         *
         * @param customClass customClass of LevelSetting
         * @return self
         * @throws NullPointerException when customClass is null
         */
        public Builder customClass(String customClass) {
            Objects.requireNonNull(customClass);
            this.customClass = customClass;
            return self();
        }

        /**
         * setter of legacyFlash
         *
         * @param legacyFlash legacyFlash of LevelSetting
         * @return self
         * @throws NullPointerException when legacyFlash is null
         */
        public Builder legacyFlash(Boolean legacyFlash) {
            Objects.requireNonNull(legacyFlash);
            this.legacyFlash = legacyFlash;
            return self();
        }

        /**
         * setter of legacySpriteTiles
         *
         * @param legacySpriteTiles legacySpriteTiles of LevelSetting
         * @return self
         * @throws NullPointerException when legacySpriteTiles is null
         */
        public Builder legacySpriteTiles(Boolean legacySpriteTiles) {
            Objects.requireNonNull(legacySpriteTiles);
            this.legacySpriteTiles = legacySpriteTiles;
            return self();
        }

        /**
         * setter of unknownProperties
         *
         * @param unknownProperties unknownProperties of LevelSetting
         * @return self
         * @throws NullPointerException when unknownProperties is null
         */
        public Builder unknownProperties(Map<String, JsonNode> unknownProperties) {
            Objects.requireNonNull(unknownProperties);
            this.unknownProperties = Collections.unmodifiableMap(new HashMap<>(unknownProperties));
            return self();
        }

    }

}
