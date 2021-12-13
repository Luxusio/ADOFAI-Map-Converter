package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.DecorationRelativeTo;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Font;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class AddText extends Action {

	private String decText = "text";
	private Font font = Font.DEFAULT;
	private List<Double> position = Arrays.asList(0.0, 0.0);
	private DecorationRelativeTo relativeTo = DecorationRelativeTo.TILE;
	private List<Double> pivotOffset = Arrays.asList(0.0, 0.0);
	private Double rotation = 0.0;
	private List<Long> scale = Arrays.asList(100L, 100L);
	private String color = "ffffff";
	private Long opacity = 100L;
	private Long depth = -1L;
	private Long parallax = -1L;
	private String tag = "";
	
	public AddText() {
		super(EventType.ADD_TEXT);
	}

	public AddText(String decText, Font font, List<Double> position, DecorationRelativeTo relativeTo, List<Double> pivotOffset,
				   Double rotation, List<Long> scale, String color, Long opacity, Long depth, Long parallax, String tag) {
		this();
		this.decText = decText;
		this.font = font;
		this.position = position;
		this.relativeTo = relativeTo;
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
