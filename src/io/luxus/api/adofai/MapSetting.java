package io.luxus.api.adofai;

import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;

public class MapSetting {
	
	private Long version;
	private String artist;
	private String specialArtistType;
	private String artistPermission;
	private String song;
	private String author;
	private String separateCountdownTime;
	private String previewImage;
	private String previewIcon;
	private String previewIconColor;
	private Long previewSongStart;
	private Long previewSongDuration;
	private String seizureWarning;
	private String levelDesc;
	private String levelTags;
	//private String artistPermission;
	private String artistLinks;
	private Long difficulty;
	private String songFilename;
	private Double bpm;
	private Long volume;
	private Long offset;
	private Long pitch;
	private String hitsound;
	private Long hitsoundVolume;
	private Long countdownTicks;
	private String trackColorType;
	private String trackColor;
	private String secondaryTrackColor;
	private Double trackColorAnimDuration;
	private String trackColorPulse;
	private Long trackPulseLength;
	private String trackStyle;
	private String trackAnimation;
	private Double beatsAhead;
	private String trackDisappearAnimation;
	private Double beatsBehind;
	private String backgroundColor;
	private String bgImage;
	private String bgImageColor;
	private List<Long> parallax;
	private String bgDisplayMode;
	private String lockRot;
	private String loopBG;
	private Long unscaledSize;
	private String relativeTo;
	private List<Long> position;
	private Double rotation;
	private Long zoom;
	private String bgVideo;
	private String loopVideo;
	private Long vidOffset;
	private String floorIconOutlines;
	private String stickToFloors;
	private String planetEase;
	private Long planetEaseParts;
	
	public MapSetting() {
		
	}
	
	@SuppressWarnings("unchecked")
	public void load(JSONObject json) {
		this.version = (Long) json.get("version");
		this.artist = (String) json.get("artist");
		this.specialArtistType = (String) json.get("specialArtistType");
		this.artistPermission = (String) json.get("artistPermission");
		this.song = (String) json.get("song");
		this.author = (String) json.get("author");
		this.separateCountdownTime = (String) json.get("separateCountdownTime");
		this.songFilename = (String) json.get("songFilename");
		this.bpm = SafeDatatypeConverter.toDouble(json.get("bpm"));
		this.volume = (Long) json.get("volume");
		this.offset = (Long) json.get("offset");
		this.pitch = (Long) json.get("pitch");
		this.hitsound = (String) json.get("hitsound");
		this.hitsoundVolume = (Long) json.get("hitsoundVolume");
		this.trackColorType = (String) json.get("trackColorType");
		this.trackColor = (String) json.get("trackColor");
		this.secondaryTrackColor = (String) json.get("secondaryTrackColor"); 
		this.trackColorAnimDuration = SafeDatatypeConverter.toDouble(json.get("trackColorAnimDuration"));
		this.trackColorPulse = (String) json.get("trackColorPulse"); 
		this.trackPulseLength = (Long) json.get("trackPulseLength");
		this.trackStyle = (String) json.get("trackStyle"); 
		this.trackAnimation = (String) json.get("trackAnimation");
		this.beatsAhead = SafeDatatypeConverter.toDouble(json.get("beatsAhead"));
		this.trackDisappearAnimation = (String) json.get("trackDisappearAnimation");
		this.beatsBehind = SafeDatatypeConverter.toDouble(json.get("beatsBehind"));
		this.backgroundColor = (String) json.get("backgroundColor");
		this.bgImage = (String) json.get("bgImage");
		this.bgImageColor = (String) json.get("bgImageColor");
		//this.parallax = JSONObjectConverter.toIntArray((JSONArray) json.get("parallax"));
		this.parallax = (List<Long>) json.get("parallax");
		this.bgDisplayMode = (String) json.get("bgDisplayMode");
		this.lockRot = (String) json.get("lockRot");
		this.loopBG = (String) json.get("loopBG");
		this.unscaledSize = (Long) json.get("unscaledSize");
		this.relativeTo = (String) json.get("relativeTo");
		//this.position = JSONObjectConverter.toIntArray((JSONArray) json.get("position"));
		this.position = (List<Long>) json.get("position");
		this.rotation = SafeDatatypeConverter.toDouble(json.get("rotation"));
		this.zoom = (Long) json.get("zoom");
		this.bgVideo = (String) json.get("bgVideo");
		this.loopVideo = (String) json.get("loopVideo");
		this.vidOffset = (Long) json.get("vidOffset");
		this.floorIconOutlines = (String) json.get("floorIconOutlines");
		this.stickToFloors = (String) json.get("stickToFloors");
		this.planetEase = (String) json.get("planetEase");
		this.planetEaseParts = (Long) json.get("planetEaseParts");
	}
	
	public void save(StringBuilder sb) {
		saveVariable(sb, "version", version);
		saveVariable(sb, "artist", artist);
		saveVariable(sb, "specialArtistType", specialArtistType);
		saveVariable(sb, "artistPermission", artistPermission);
		saveVariable(sb, "song", song);
		saveVariable(sb, "author", author);
		saveVariable(sb, "separateCountdownTime", separateCountdownTime);
		saveVariable(sb, "previewImage", previewImage);
		saveVariable(sb, "previewIcon", previewIcon);
		saveVariable(sb, "previewIconColor", previewIconColor);
		saveVariable(sb, "previewSongStart", previewSongStart);
		saveVariable(sb, "previewSongDuration", previewSongDuration);
		saveVariable(sb, "seizureWarning", seizureWarning);
		saveVariable(sb, "levelDesc", levelDesc);
		saveVariable(sb, "levelTags", levelTags);
		saveVariable(sb, "artistLinks", artistLinks);
		saveVariableSpecial(sb, "difficulty", difficulty); // todo : add more
		saveVariable(sb, "songFilename", songFilename);
		saveVariable(sb, "bpm", bpm);
		saveVariable(sb, "volume", volume);
		saveVariable(sb, "offset", offset);
		saveVariable(sb, "pitch", pitch);
		saveVariable(sb, "hitsound", hitsound);
		saveVariable(sb, "hitsoundVolume", hitsoundVolume);
		saveVariable(sb, "countdownTicks", countdownTicks);
		saveVariable(sb, "trackColorType", trackColorType);
		saveVariable(sb, "trackColor", trackColor);
		saveVariable(sb, "secondaryTrackColor", secondaryTrackColor);
		saveVariable(sb, "trackColorAnimDuration", trackColorAnimDuration);
		saveVariable(sb, "trackColorPulse", trackColorPulse);
		saveVariable(sb, "trackPulseLength", trackPulseLength);
		saveVariable(sb, "trackStyle", trackStyle);
		saveVariable(sb, "trackAnimation", trackAnimation);
		saveVariable(sb, "beatsAhead", beatsAhead);
		saveVariable(sb, "trackDisappearAnimation", trackDisappearAnimation);
		saveVariable(sb, "beatsBehind", beatsBehind);
		saveVariable(sb, "backgroundColor", backgroundColor);
		saveVariable(sb, "bgImage", bgImage);
		saveVariable(sb, "bgImageColor", bgImageColor);
		saveVariable(sb, "parallax", parallax);
		saveVariable(sb, "bgDisplayMode", bgDisplayMode);
		saveVariable(sb, "lockRot", lockRot);
		saveVariable(sb, "loopBG", loopBG);
		saveVariable(sb, "unscaledSize", unscaledSize);
		saveVariable(sb, "relativeTo", relativeTo);
		saveVariable(sb, "position", position);
		saveVariable(sb, "rotation", rotation);
		saveVariable(sb, "zoom", zoom);
		saveVariable(sb, "bgVideo", bgVideo);
		saveVariable(sb, "loopVideo", loopVideo);
		saveVariable(sb, "vidOffset", vidOffset);
		saveVariable(sb, "floorIconOutlines", floorIconOutlines);
		saveVariable(sb, "stickToFloors", stickToFloors);
		saveVariable(sb, "planetEase", planetEase);
		saveVariable(sb, "planetEaseParts", planetEaseParts);
	}
	
	private void saveVariable(StringBuilder sb, String name, String value) {
		if(value == null) return;
		sb.append("\t\t\"").append(name).append("\": \"").append(value).append("\", \n");
	}
	
	private void saveVariable(StringBuilder sb, String name, Long value) {
		if(value == null) return;
		sb.append("\t\t\"").append(name).append("\": ").append(value).append(", \n");
	}
	
	private void saveVariable(StringBuilder sb, String name, Double value) {
		if(value == null) return;
		sb.append("\t\t\"").append(name).append("\": ");
		double doubleValue = (double) value;
		long longValue = (long) doubleValue;
		if(doubleValue == longValue) {
			sb.append(longValue);
		} else {
			sb.append(value);
		}
		sb.append(", \n");
	}
	
	private void saveVariableSpecial(StringBuilder sb, String name, Long value) {
		if(value == null) return;
		sb.append("\t\t\"").append(name).append("\": ").append(value).append(", ,\n");
	}
	
	private void saveVariable(StringBuilder sb, String name, List<Long> valueList) {
		if(valueList == null) return;
		sb.append("\t\t\"").append(name).append("\": [");
		Iterator<Long> it = valueList.iterator();
		while(true) {
			sb.append(it.next());
			if(it.hasNext()) {
				sb.append(", ");
			}
			else {
				break;
			}
		}
		sb.append("], \n");
	}
	
	
	
	public Long getVersion() {
		return version;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getSong() {
		return song;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getSeparateCountdownTime() {
		return separateCountdownTime;
	}
	
	public String getPreviewImage() {
		return previewImage;
	}
	
	public String getPreviewIcon() {
		return previewIcon;
	}
	
	public String getPreviewIconColor() {
		return previewIconColor;
	}
	
	public Long getPreviewSongStart() {
		return previewSongStart;
	}
	
	public Long getPreviewSongDuration() {
		return previewSongDuration;
	}
	
	public String getSeizureWarning() {
		return seizureWarning;
	}
	
	public String getLevelDesc() {
		return levelDesc;
	}
	
	public String getLevelTags() {
		return levelTags;
	}
	
	public String getArtistPermission() {
		return artistPermission;
	}
	
	public String getArtistLinks() {
		return artistLinks;
	}
	
	public Long getDifficulty() {
		return difficulty;
	}
	
	public String getSongFilename() {
		return songFilename;
	}
	
	public Double getBpm() {
		return bpm;
	}
	
	public Long getVolume() {
		return volume;
	}
	
	public Long getOffset() {
		return offset;
	}
	
	public Long getPitch() {
		return pitch;
	}
	
	public String getHitsound() {
		return hitsound;
	}
	
	public Long getHitsoundVolume() {
		return hitsoundVolume;
	}
	
	public Long getCountdownTicks() {
		return countdownTicks;
	}
	
	public String getTrackColorType() {
		return trackColorType;
	}
	
	public String getTrackColor() {
		return trackColor;
	}
	
	public String getSecondaryTrackColor() {
		return secondaryTrackColor;
	}
	
	public Double getTrackColorAnimDuration() {
		return trackColorAnimDuration;
	}
	
	public String getTrackColorPulse() {
		return trackColorPulse;
	}
	
	public Long getTrackPulseLength() {
		return trackPulseLength;
	}
	
	public String getTrackStyle() {
		return trackStyle;
	}
	
	public String getTrackAnimation() {
		return trackAnimation;
	}
	
	public Double getBeatsAhead() {
		return beatsAhead;
	}
	
	public String getTrackDisappearAnimation() {
		return trackDisappearAnimation;
	}
	
	public Double getBeatsBehind() {
		return beatsBehind;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	public String getBgImage() {
		return bgImage;
	}
	
	public String getBgImageColor() {
		return bgImageColor;
	}
	
	public List<Long> getParallax() {
		return parallax;
	}
	
	public String getBgDisplayMode() {
		return bgDisplayMode;
	}
	
	public String getLockRot() {
		return lockRot;
	}
	
	public String getLoopBG() {
		return loopBG;
	}
	
	public Long getUnscaledSize() {
		return unscaledSize;
	}
	
	public String getRelativeTo() {
		return relativeTo;
	}
	
	public List<Long> getPosition() {
		return position;
	}
	
	public Double getRotation() {
		return rotation;
	}
	
	public Long getZoom() {
		return zoom;
	}
	
	public String getBgVideo() {
		return bgVideo;
	}
	
	public String getLoopVideo() {
		return loopVideo;
	}
	
	public Long getVidOffset() {
		return vidOffset;
	}
	
	public String getFloorIconOutlines() {
		return floorIconOutlines;
	}
	
	public String getStickToFloors() {
		return stickToFloors;
	}
	
	public String getPlanetEase() {
		return planetEase;
	}
	
	public Long getPlanetEaseParts() {
		return planetEaseParts;
	}
	
}
