package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SetText extends Action {
	
	private String decText = "text";
	private String tag = "";
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public SetText() {
		super(EventType.SET_TEXT);
	}

	public SetText(String decText, String tag, Double angleOffset, String eventTag) {
		this();
		this.decText = decText;
		this.tag = tag;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

}
