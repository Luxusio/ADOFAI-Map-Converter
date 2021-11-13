package io.luxus.lib.adofai;

import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.PositionTrack;
import io.luxus.lib.adofai.action.SetSpeed;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.SpeedType;
import io.luxus.lib.adofai.action.type.Toggle;
import io.luxus.lib.adofai.converter.AngleConverter;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
public class TileMeta {

    private double bpm = 100.0;         // bpm of current angle
    private double travelAngle = 0.0;   // angle planet would travel in this tile
    private double staticAngle = 0.0;   // static angle of this tile
    private boolean reversed = false;

    private double realX = 0.0;
    private double realY = 0.0;

    private double editorX = 0.0;
    private double editorY = 0.0;

    public TileMeta() {
    }

    public void forceInit(TileMeta tileMeta) {
        this.bpm = tileMeta.bpm;
        this.travelAngle = tileMeta.travelAngle;
        this.staticAngle = tileMeta.staticAngle;
        this.reversed = tileMeta.reversed;

        this.realX = tileMeta.realX;
        this.realY = tileMeta.realY;
        this.editorX = tileMeta.editorX;
        this.editorY = tileMeta.editorY;
    }

    public void update(Map<EventType, List<Action>> actionMap, LevelSetting levelSetting, Double nextAngle) {
        this.bpm = levelSetting.getBpm();
        this.travelAngle = 360.0;
        this.staticAngle = 0.0;
        this.reversed = false;

        this.realX = 0.0;
        this.realY = 0.0;
        this.editorX = 0.0;
        this.editorY = 0.0;

        update(actionMap);

        AngleConverter.Result result = AngleConverter.convert(0.0, 0.0, nextAngle, reversed);
        this.staticAngle = result.getCurrStaticAngle();
        this.travelAngle = result.getCurrTravelAngle();
    }

    public void update(Map<EventType, List<Action>> actionMap, Double currAngle, TileMeta prevTileMeta, Double nextAngle) {
        this.bpm = prevTileMeta.bpm;
        this.staticAngle = prevTileMeta.staticAngle;
        this.reversed = prevTileMeta.reversed;

        this.realX = prevTileMeta.realX;
        this.realY = prevTileMeta.realY;
        this.editorX = prevTileMeta.editorX;
        this.editorY = prevTileMeta.editorY;

        update(actionMap);

        AngleConverter.Result result = AngleConverter.convert(prevTileMeta.staticAngle, currAngle, nextAngle, reversed);
        this.staticAngle = result.getCurrStaticAngle();
        this.travelAngle = result.getCurrTravelAngle();

        double rad = Math.toRadians(prevTileMeta.staticAngle);
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

    public static double calculateTotalPerceivedBpm(List<Tile> tiles) {
        return calculatePerceivedBpmFromDurationMs(
                calculateTotalDurationMs(tiles));
    }

    public static double calculatePerceivedBpmFromDurationMs(double durationMs) {
        return 60000.0 / durationMs;
    }

    public static double calculateTotalDurationMs(List<Tile> tiles) {
        return tiles.stream()
                .map(Tile::getTileMeta)
                .map(TileMeta::getDurationMs)
                .reduce(0.0, Double::sum);
    }

    public static double calculateTotalTravelAngle(List<Tile> tiles) {
        return tiles.stream()
                .map(Tile::getTileMeta)
                .map(TileMeta::getTravelAngle)
                .reduce(0.0, Double::sum);
    }

    public static double calculatePerceivedBpm(double bpm, double travelAngle) {
        return (180.0 * bpm) / travelAngle;
    }

    public double getPerceivedBpm() {
        return calculatePerceivedBpm(bpm, travelAngle);
    }

    public double getReversedTempBPM() {
        return getReversedTravelAngle() * bpm / travelAngle;
    }

    public double getReversedTravelAngle() {
        return getTravelAngle() == 360.0 ? 360.0 : 360.0 - getTravelAngle();
    }

    public double getDurationMs() {
        return 60000.0 / getPerceivedBpm();
    }

}
