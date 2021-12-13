package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.EventType;
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
	private List<Double> scale = Arrays.asList(100.0, 100.0);
	private List<Long> tile = Arrays.asList(1L, 1L);
	private String color = "ffffff";
	private Long opacity = 100L;
	private Long depth = 0L;
	private Long parallax = 0L;
	private String tag = "";
	private String components = "";
	
	public AddDecoration() {
		super(EventType.ADD_DECORATION);
	}

	public AddDecoration(String decorationImage, List<Double> position, DecorationRelativeTo decorationRelativeTo,
						 List<Double> pivotOffset, Double rotation, List<Double> scale, List<Long> tile, String color,
						 Long opacity, Long depth, Long parallax, String tag, String components) {
		this();
		this.decorationImage = decorationImage;
		this.position = position;
		this.decorationRelativeTo = decorationRelativeTo;
		this.pivotOffset = pivotOffset;
		this.rotation = rotation;
		this.scale = scale;
		this.tile = tile;
		this.color = color;
		this.opacity = opacity;
		this.depth = depth;
		this.parallax = parallax;
		this.tag = tag;
		this.components = components;
	}

}
