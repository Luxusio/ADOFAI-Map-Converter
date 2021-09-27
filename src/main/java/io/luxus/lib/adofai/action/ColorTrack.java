package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.TrackColorPulse;
import io.luxus.lib.adofai.action.type.TrackColorType;
import io.luxus.lib.adofai.action.type.TrackStyle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ColorTrack extends Action {
	
	private TrackColorType trackColorType = TrackColorType.SINGLE;
	private String trackColor = "debb7b";
	private String secondaryTrackColor = "ffffff";
	private Double trackColorAnimDuration = 2.0;
	private TrackColorPulse trackColorPulse = TrackColorPulse.NONE;
	private Long trackPulseLength = 10L;
	private TrackStyle trackStyle = TrackStyle.STANDARD;
	
	public ColorTrack() {
		super(EventType.COLOR_TRACK);
	}

	public ColorTrack(TrackColorType trackColorType, String trackColor, String secondaryTrackColor, Double trackColorAnimDuration, TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle) {
		this();
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
	}

}
