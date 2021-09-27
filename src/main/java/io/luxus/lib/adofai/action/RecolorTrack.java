package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class RecolorTrack extends Action {

	private Long startTileNum = 0L;
	private TilePosition startTilePosition = TilePosition.THIS_TILE;
	private Long endTileNum = 0L;
	private TilePosition endTilePosition = TilePosition.THIS_TILE;
	private TrackColorType trackColorType = TrackColorType.SINGLE;
	private String trackColor = "debb7b";
	private String secondaryTrackColor = "ffffff";
	private Double trackColorAnimDuration = 2.0;
	private TrackColorPulse trackColorPulse = TrackColorPulse.NONE;
	private Long trackPulseLength = 10L;
	private TrackStyle trackStyle = TrackStyle.STANDARD;
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public RecolorTrack() {
		super(EventType.RECOLOR_TRACK);
	}

	public RecolorTrack(Long startTileNum, TilePosition startTilePosition, Long endTileNum, TilePosition endTilePosition,
						TrackColorType trackColorType, String trackColor, String secondaryTrackColor, Double trackColorAnimDuration,
						TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle, Double angleOffset, String eventTag) {
		this();
		this.startTileNum = startTileNum;
		this.startTilePosition = startTilePosition;
		this.endTileNum = endTileNum;
		this.endTilePosition = endTilePosition;
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

}
