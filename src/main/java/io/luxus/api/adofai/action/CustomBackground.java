package io.luxus.api.adofai.action;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.converter.SafeDatatypeConverter;
import io.luxus.api.adofai.type.EventType;

public class CustomBackground extends Action {
	
	private String color;
	private String bgImage;
	private String imageColor;
	private List<Long> parallax;
	private String bgDisplayMode;
	private String lockRot;
	private String loopBG;
	private Long unscaledSize;
	private Double angleOffset;
	private String eventTag;
	
	public CustomBackground() {
		super(EventType.CUSTOM_BACKGROUND);
	}
	
	public CustomBackground(String color, String bgImage, String imageColor, List<Long> parallax,
			String bgDisplayMode, String lockRot, String loopBG, Long unscaledSize, Double angleOffset,
			String eventTag) {
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



	@SuppressWarnings("unchecked")
	@Override
	public void load(JSONObject json) throws ParseException {
		this.color = (String) json.get("color");
		this.bgImage = (String) json.get("bgImage");
		this.imageColor = (String) json.get("imageColor");
		this.parallax = (List<Long>) json.get("parallax");
		this.bgDisplayMode = (String) json.get("bgDisplayMode");
		this.lockRot = (String) json.get("lockRot");
		this.loopBG = (String) json.get("loopBG");
		this.unscaledSize = (Long) json.get("unscaledSize");
		this.angleOffset = SafeDatatypeConverter.toDouble(json.get("angleOffset"));
		this.eventTag = (String) json.get("eventTag");
	}
	
	@Override
	public void save(StringBuilder sb, int floor) {
		saveBefore(sb, floor);
		save(sb, "color", color);
		save(sb, "bgImage", bgImage);
		save(sb, "imageColor", imageColor);
		save(sb, "parallax", parallax);
		save(sb, "bgDisplayMode", bgDisplayMode);
		save(sb, "lockRot", lockRot);
		save(sb, "loopBG", loopBG);
		save(sb, "unscaledSize", unscaledSize);
		save(sb, "angleOffset", angleOffset);
		save(sb, "eventTag", eventTag);
		saveAfter(sb);
	}

	public String getColor() {
		return color;
	}

	public String getBgImage() {
		return bgImage;
	}

	public String getImageColor() {
		return imageColor;
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

	public Double getAngleOffset() {
		return angleOffset;
	}

	public String getEventTag() {
		return eventTag;
	}
	
}
