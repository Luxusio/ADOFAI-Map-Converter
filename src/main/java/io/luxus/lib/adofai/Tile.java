package io.luxus.lib.adofai;

import com.google.common.math.DoubleMath;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.Constants.EPSILON;

@ToString
@Getter
public class Tile {

    private Double angle = ANGLE_MID_TILE;
    private Map<EventType, List<Action>> actionMap;
    private final TileMeta tileMeta = new TileMeta();

    public Tile() {
        this.actionMap = new HashMap<>();
    }

    public Tile(Double angle) {
        this();
        setAngle(angle);
    }

    public Tile(Double angle, Map<EventType, List<Action>> actionMap) {
        this(angle);
        this.actionMap = actionMap;
    }

    public boolean addAction(Action action) {
        List<Action> actions = getActions(action.getEventType());
        if (action.getEventType().isSingleOnly() && !actions.isEmpty()) {
            return false;
        }
        actions.add(action);
        return true;
    }

    public List<Action> getActions(EventType eventType) {
        return actionMap.computeIfAbsent(eventType, k -> new ArrayList<>());
    }

    public void setAngle(Double angle) {
        if (angle != null && DoubleMath.fuzzyEquals(angle, 999.0, EPSILON)) angle = null;
        this.angle = angle;
    }

    public void update(LevelSetting levelSetting) {
        this.tileMeta.update(actionMap, levelSetting);
    }

    public void update(TileMeta prevTileMeta) {
        this.tileMeta.update(actionMap, angle, prevTileMeta);
    }

}
