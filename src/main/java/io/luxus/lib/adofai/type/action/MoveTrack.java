package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.Ease;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TilePosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
@ToString
public class MoveTrack extends Action {

	private Long startTileNum = 0L;
	private TilePosition startTilePosition = TilePosition.THIS_TILE;
	private Long endTileNum = 0L;
	private TilePosition endTilePosition = TilePosition.THIS_TILE;
	private Double duration = 1.0;
	private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
	private Double rotationOffset = 0.0;
	private List<Long> scale = Arrays.asList(100L, 100L);
	private Long opacity = 100L;
	private Double angleOffset = 0.0;
	private Ease ease = Ease.LINEAR;
	private String eventTag = "";
	
	public MoveTrack() {
		super(EventType.MOVE_TRACK);
	}

	public MoveTrack(Long startTileNum, TilePosition startTilePosition, Long endTileNum, TilePosition endTilePosition,
					 Double duration, List<Double> positionOffset, Double rotationOffset, List<Long> scale, Long opacity,
					 Double angleOffset, Ease ease, String eventTag) {
		this();
		this.startTileNum = startTileNum;
		this.startTilePosition = startTilePosition;
		this.endTileNum = endTileNum;
		this.endTilePosition = endTilePosition;
		this.duration = duration;
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
		this.opacity = opacity;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

}
