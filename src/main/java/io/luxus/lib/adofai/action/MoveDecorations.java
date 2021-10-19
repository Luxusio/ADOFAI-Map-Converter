package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.Ease;
import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class MoveDecorations extends Action {

	private Double duration = 1.0;
	private String tag = "";
	private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
	private Double rotationOffset = 0.0;
	private List<Long> scale = Arrays.asList(100L, 100L);
	private String color = "ffffff";
	private Long opacity = 100L;
	private Double angleOffset = 0.0;
	private Ease ease = Ease.LINEAR;
	private String eventTag = "";

	public MoveDecorations() {
		super(EventType.MOVE_DECORATIONS);
	}

	public MoveDecorations(Double duration, String tag, List<Double> positionOffset, Double rotationOffset,
						   List<Long> scale, String color, Long opacity, Double angleOffset, Ease ease, String eventTag) {
		this();
		this.duration = duration;
		this.tag = tag;
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
		this.color = color;
		this.opacity = opacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}
}
