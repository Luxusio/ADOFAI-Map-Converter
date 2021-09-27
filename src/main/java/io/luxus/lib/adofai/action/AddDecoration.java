package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.DecorationRelativeTo;
import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class AddDecoration extends Action {
	
	private String decorationImage = "";
	private List<Double> position = Arrays.asList(0.0, 0.0);
	private DecorationRelativeTo decorationRelativeTo = DecorationRelativeTo.TILE;
	private List<Double> pivotOffset = Arrays.asList(0.0, 0.0);
	private Double rotation = 0.0;
	private Long scale = 0L;
	private String color = "ffffff";
	private Long opacity = 100L;
	private Long depth = 0L;
	private Long parallax = 0L;
	private String tag = "";
	
	public AddDecoration() {
		super(EventType.ADD_DECORATION);
	}

	public AddDecoration(String decorationImage, List<Double> position, DecorationRelativeTo decorationRelativeTo, List<Double> pivotOffset, Double rotation, Long scale, String color, Long opacity, Long depth, Long parallax, String tag) {
		this();
		this.decorationImage = decorationImage;
		this.position = position;
		this.decorationRelativeTo = decorationRelativeTo;
		this.pivotOffset = pivotOffset;
		this.rotation = rotation;
		this.scale = scale;
		this.color = color;
		this.opacity = opacity;
		this.depth = depth;
		this.parallax = parallax;
		this.tag = tag;
	}

}
