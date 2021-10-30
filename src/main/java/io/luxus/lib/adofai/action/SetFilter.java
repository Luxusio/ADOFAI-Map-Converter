package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.Filter;
import io.luxus.lib.adofai.action.type.Toggle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SetFilter extends Action {
	
	private Filter filter = Filter.GRAYSCALE;
	private Toggle enabled = Toggle.ENABLED;
	private Long intensity = 100L;
	private Toggle disableOthers = Toggle.DISABLED;
	private Double angleOffset = 0.0;
	private String eventTag = "";
	
	public SetFilter() {
		super(EventType.SET_FILTER);
	}

	public SetFilter(Filter filter, Toggle enabled, Long intensity, Toggle disableOthers, Double angleOffset, String eventTag) {
		this();
		this.filter = filter;
		this.enabled = enabled;
		this.intensity = intensity;
		this.disableOthers = disableOthers;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}
}
