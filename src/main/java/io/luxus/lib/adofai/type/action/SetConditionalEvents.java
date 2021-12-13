package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SetConditionalEvents extends Action {

	private String perfectTag = "NONE";
	private String hitTag = "NONE";
	private String barelyTag = "NONE";
	private String missTag = "NONE";
	private String lossTag = "NONE";

	public SetConditionalEvents() {
		super(EventType.SET_CONDITIONAL_EVENTS);
	}

	public SetConditionalEvents(String perfectTag, String hitTag, String barelyTag, String missTag,
			String lossTag) {
		this();
		this.perfectTag = perfectTag;
		this.hitTag = hitTag;
		this.barelyTag = barelyTag;
		this.missTag = missTag;
		this.lossTag = lossTag;
	}

}
