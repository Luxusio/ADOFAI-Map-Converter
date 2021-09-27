package io.luxus.lib.adofai;

import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.PositionTrack;
import io.luxus.lib.adofai.action.SetSpeed;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.SpeedType;
import io.luxus.lib.adofai.action.type.Toggle;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

@Getter
@ToString
public class TileMeta {

    private double bpm = 100.0;
    private double relativeAngle = 0.0;
    private double staticAngle = 0.0;
    private boolean reversed = false;

    private double realX = 0.0;
    private double realY = 0.0;

    private double editorX = 0.0;
    private double editorY = 0.0;

    public TileMeta() {
    }

    public void update(Map<EventType, List<Action>> actionMap, LevelSetting levelSetting) {
        this.bpm = levelSetting.getBpm();
        this.relativeAngle = 360.0;
        this.staticAngle = 0.0;
        this.reversed = false;

        this.realX = 0.0;
        this.realY = 0.0;
        this.editorX = 0.0;
        this.editorY = 0.0;

        update(actionMap);
    }

    public void update(Map<EventType, List<Action>> actionMap, Double staticAngle, TileMeta prevTileMeta) {
        this.bpm = prevTileMeta.bpm;
        this.relativeAngle = prevTileMeta.relativeAngle;
        this.staticAngle = prevTileMeta.staticAngle;
        this.reversed = prevTileMeta.reversed;

        this.realX = prevTileMeta.realX;
        this.realY = prevTileMeta.realY;
        this.editorX = prevTileMeta.editorX;
        this.editorY = prevTileMeta.editorY;

        update(actionMap);

        if (staticAngle == ANGLE_MID_TILE) {
            this.staticAngle = prevTileMeta.getStaticAngle();
            this.relativeAngle = 0.0;
        }
        else {
            this.staticAngle = staticAngle;
            this.relativeAngle = staticAngle - prevTileMeta.staticAngle;

            if (reversed) {
                this.relativeAngle = - this.relativeAngle;
            }

            if (prevTileMeta.relativeAngle != 0.0) {
                this.relativeAngle += 180;
            }

            if (this.relativeAngle <= 0) {
                this.relativeAngle += 360;
            }
            else if (this.relativeAngle > 360) {
                this.relativeAngle -= 360;
            }
        }

        double rad = Math.toRadians(this.staticAngle);
        double x = Math.cos(rad);
        double y = Math.sin(rad);

        this.realX += x;
        this.realY += y;
        this.editorX += x;
        this.editorY += y;

    }

    private void update(Map<EventType, List<Action>> actionMap) {
        List<Action> actions;

        actions = actionMap.get(EventType.SET_SPEED);
        if (actions != null && !actions.isEmpty()) {
            SetSpeed setSpeed = (SetSpeed) actions.get(0);

            if (setSpeed.getSpeedType() == SpeedType.BPM) {
                this.bpm = setSpeed.getBeatsPerMinute();
            }
            else if (setSpeed.getSpeedType() == SpeedType.MULTIPLIER) {
                this.bpm *= setSpeed.getBpmMultiplier();
            }
        }

        actions = actionMap.get(EventType.TWIRL);
        if (actions != null && !actions.isEmpty()) {
            this.reversed = !this.reversed;
        }

        actions = actionMap.get(EventType.POSITION_TRACK);
        if (actions != null && !actions.isEmpty()) {
            PositionTrack positionTrack = (PositionTrack) actions.get(0);

            if (positionTrack.getEditorOnly() == Toggle.DISABLED) {
                this.realX += positionTrack.getPositionOffset().get(0);
                this.realY += positionTrack.getPositionOffset().get(1);
            }
            this.editorX += positionTrack.getPositionOffset().get(0);
            this.editorY += positionTrack.getPositionOffset().get(1);
        }
    }

}
