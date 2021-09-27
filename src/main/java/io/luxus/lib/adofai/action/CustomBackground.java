package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.BGDisplayModeType;
import io.luxus.lib.adofai.action.type.Toggle;
import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class CustomBackground extends Action {
	
	private String color = "000000";
	private String bgImage = "";
	private String imageColor = "ffffff";
	private List<Double> parallax = Arrays.asList(100.0, 100.0);
	private BGDisplayModeType bgDisplayMode = BGDisplayModeType.FIT_TO_SCREEN;
	private Toggle lockRot = Toggle.DISABLED;
	private Toggle loopBG = Toggle.DISABLED;
	private Long unscaledSize = 100L;
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public CustomBackground() {
		super(EventType.CUSTOM_BACKGROUND);
	}

	public CustomBackground(String color, String bgImage, String imageColor, List<Double> parallax, BGDisplayModeType bgDisplayMode, Toggle lockRot, Toggle loopBG, Long unscaledSize, Double angleOffset, String eventTag) {
		this();
		this.color = color;
		this.bgImage = bgImage;
		this.imageColor = imageColor;
		this.parallax = parallax;
		this.bgDisplayMode = bgDisplayMode;
		this.lockRot = lockRot;
		this.loopBG = loopBG;
		this.unscaledSize = unscaledSize;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

}
