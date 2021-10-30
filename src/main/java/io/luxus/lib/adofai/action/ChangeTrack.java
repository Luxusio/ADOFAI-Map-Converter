package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ChangeTrack extends Action {

	private TrackColorType trackColorType;
	private String trackColor;
	private String secondaryTrackColor;
	private Double trackColorAnimDuration;
	private TrackColorPulse trackColorPulse;
	private Long trackPulseLength;
	private TrackStyle trackStyle;
	private TrackAnimation trackAnimation;
	private Double beatsAhead;
	private TrackDisappearAnimation trackDisappearAnimation;
	private Double beatsBehind;

	public ChangeTrack() {
		super(EventType.CHANGE_TRACK);
	}

	public ChangeTrack(TrackColorType trackColorType, String trackColor, String secondaryTrackColor,
					   Double trackColorAnimDuration, TrackColorPulse trackColorPulse, Long trackPulseLength, TrackStyle trackStyle,
					   TrackAnimation trackAnimation, Double beatsAhead, TrackDisappearAnimation trackDisappearAnimation, Double beatsBehind) {
		this();
		this.trackColorType = trackColorType;
		this.trackColor = trackColor;
		this.secondaryTrackColor = secondaryTrackColor;
		this.trackColorAnimDuration = trackColorAnimDuration;
		this.trackColorPulse = trackColorPulse;
		this.trackPulseLength = trackPulseLength;
		this.trackStyle = trackStyle;
		this.trackAnimation = trackAnimation;
		this.beatsAhead = beatsAhead;
		this.trackDisappearAnimation = trackDisappearAnimation;
		this.beatsBehind = beatsBehind;
	}
}
