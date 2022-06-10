package io.luxus.lib.adofai;

import io.luxus.lib.adofai.type.TileAngle;
import io.luxus.lib.adofai.type.action.*;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.SpeedType;
import io.luxus.lib.adofai.type.Toggle;
import io.luxus.lib.adofai.helper.AngleHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TileMeta {

    private final int floor;

    private final double bpm;         // bpm of current angle
    private final double travelAngle;   // angle planet would travel in this tile
    private final double staticAngle;   // static angle of this tile
    private final boolean reversed;
    private final double additionalSleepAngle;
    private final boolean hold;

    private final long planets;

    private final double realX;
    private final double realY;

    private final double editorX;
    private final double editorY;


    public static TileMeta createFirstTileMeta(Map<EventType, List<Action>> actionMap, LevelSetting levelSetting, TileAngle nextAngle) {
        return new TileMeta.Builder(actionMap, levelSetting, nextAngle).build();
    }

    public static TileMeta createTileMeta(Map<EventType, List<Action>> actionMap, TileMeta prevTileMeta, TileAngle currAngle, TileAngle nextAngle) {
        return new TileMeta.Builder(actionMap, prevTileMeta, currAngle, nextAngle).build();
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
                .map(TileMeta::getTravelMs)
                .reduce(0.0, Double::sum);
    }


    public static double calculateTotalTravelAndPlanetAngle(List<Tile> tiles) {
        return tiles.stream()
                .map(Tile::getTileMeta)
                .map(tileMeta -> tileMeta.getTravelAngle() + tileMeta.getAdditionalSleepAngle())
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

    public static double calculateTravelMs(double bpm, double travelAngle) {
        return 60000.0 / calculatePerceivedBpm(bpm, travelAngle);
    }

    public static double calculatePlanetAngle(long planets) {
        return 360.0 / planets;
    }

    public double getPerceivedBpm() {
        return calculatePerceivedBpm(bpm, travelAngle);
    }

    public double getTravelMs() {
        return calculateTravelMs(bpm, travelAngle);
    }

    public double getPossibleMaxBpm() {
        return bpm * 360 / getTravelAngle();
    }

    public double getPlanetAngle() {
        return calculatePlanetAngle(planets);
    }

    private static class Builder {

        private final int floor;

        private double bpm;
        private double travelAngle;
        private double staticAngle;
        private boolean reversed;
        private double additionalSleepAngle;
        private boolean hold;

        private double realX;
        private double realY;
        private double editorX;
        private double editorY;
        private long planets;

        private Builder(Map<EventType, List<Action>> actionMap, LevelSetting levelSetting, TileAngle nextAngle) { // first
            this.floor = 0;

            this.bpm = levelSetting.getBpm();
            this.travelAngle = 360.0;
            this.staticAngle = 0.0;
            this.reversed = false;
            this.planets = 2L;

            this.realX = 0.0;
            this.realY = 0.0;
            this.editorX = 0.0;
            this.editorY = 0.0;

            update(actionMap, 0.0, TileAngle.ZERO, nextAngle);
        }

        private Builder(Map<EventType, List<Action>> actionMap, TileMeta prevTileMeta, TileAngle currAngle, TileAngle nextAngle) { // after first
            this.floor = prevTileMeta.floor + 1;

            this.bpm = prevTileMeta.bpm;
            this.staticAngle = prevTileMeta.staticAngle;
            this.reversed = prevTileMeta.reversed;
            this.planets = prevTileMeta.planets;

            this.realX = prevTileMeta.realX;
            this.realY = prevTileMeta.realY;
            this.editorX = prevTileMeta.editorX;
            this.editorY = prevTileMeta.editorY;

            update(actionMap, prevTileMeta.staticAngle, currAngle, nextAngle);

            double rad = Math.toRadians(prevTileMeta.staticAngle);
            double x = Math.cos(rad);
            double y = Math.sin(rad);

            this.realX += x;
            this.realY += y;
            this.editorX += x;
            this.editorY += y;
        }

        private void update(Map<EventType, List<Action>> actionMap, double staticAngle, TileAngle currAngle, TileAngle nextAngle) {
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

            // planets
            actions = actionMap.get(EventType.MULTI_PLANET);
            if (actions != null && !actions.isEmpty()) {
                MultiPlanet multiPlanet = (MultiPlanet) actions.get(0);

                this.planets = multiPlanet.getPlanets();
            }

            // additionalSleepAngle
            actions = actionMap.get(EventType.HOLD);
            if (actions != null && !actions.isEmpty()) {
                Hold hold = (Hold) actions.get(0);

                this.hold = true;
                this.additionalSleepAngle = 360 * hold.getDuration();
            }

            actions = actionMap.get(EventType.FREE_ROAM);
            if (actions != null && !actions.isEmpty()) {
                FreeRoam freeRoam = (FreeRoam) actions.get(0);

                this.additionalSleepAngle = 180 * freeRoam.getDuration();
            }

            actions = actionMap.get(EventType.PAUSE);
            if (actions != null && !actions.isEmpty()) {
                Pause pause = (Pause) actions.get(0);

                this.additionalSleepAngle = 180 * pause.getDuration();
            }


            actions = actionMap.get(EventType.POSITION_TRACK);
            if (actions != null && !actions.isEmpty()) {
                PositionTrack positionTrack = (PositionTrack) actions.get(0);

                if (positionTrack.getEditorOnly() == Toggle.DISABLED) {
                    this.realX += positionTrack.getPositionOffset().getValue0();
                    this.realY += positionTrack.getPositionOffset().getValue1();
                }
                this.editorX += positionTrack.getPositionOffset().getValue0();
                this.editorY += positionTrack.getPositionOffset().getValue1();
            }

            AngleHelper.Result result = AngleHelper.calculateAngleData(staticAngle, currAngle, nextAngle, calculatePlanetAngle(this.planets), reversed);
            this.staticAngle = result.getCurrStaticAngle();
            this.travelAngle = result.getCurrTravelAngle();
        }

        private TileMeta build() {
            return new TileMeta(floor, bpm, travelAngle, staticAngle, reversed, additionalSleepAngle, hold, planets, realX, realY, editorX, editorY);
        }

    }

}
