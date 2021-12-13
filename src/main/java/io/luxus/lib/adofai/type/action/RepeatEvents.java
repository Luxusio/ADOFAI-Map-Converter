package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class RepeatEvents extends Action {

	private Long repetitions = 1L;
	private Double interval = 1.0;
	private String tag = "";

	public RepeatEvents() {
		super(EventType.REPEAT_EVENTS);
	}

	public RepeatEvents(Long repetitions, Double interval, String tag) {
		this();
		this.repetitions = repetitions;
		this.interval = interval;
		this.tag = tag;
	}

}
