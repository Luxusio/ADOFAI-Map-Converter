package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.CameraRelativeTo;
import io.luxus.lib.adofai.action.type.Ease;
import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class MoveCamera extends Action {

	private Double duration = 1.0;
	private CameraRelativeTo relativeTo = CameraRelativeTo.PLAYER;
	private List<Double> position = Arrays.asList(0.0, 0.0);
	private Double rotation = 0.0;
	private Long zoom = 100L;
	private Double angleOffset = 0.0;
	private Ease ease = Ease.LINEAR;
	private String eventTag = "";

	public MoveCamera() {
		super(EventType.MOVE_CAMERA);
	}

	public MoveCamera(Double duration, CameraRelativeTo relativeTo, List<Double> position, Double rotation, Long zoom, Double angleOffset, Ease ease, String eventTag) {
		this();
		this.duration = duration;
		this.relativeTo = relativeTo;
		this.position = position;
		this.rotation = rotation;
		this.zoom = zoom;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

}
