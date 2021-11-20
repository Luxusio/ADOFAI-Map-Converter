package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.action.*;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.SpeedType;
import io.luxus.lib.adofai.action.type.Toggle;
import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.util.NumberUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class MapConverterBase {

    @Getter
    @RequiredArgsConstructor
    public static class ApplyEach {
        private final int floor;
        private final List<Tile> oneTimingTiles;
    }

    public static CustomLevel convertBasedOnTravelAngle(CustomLevel customLevel, boolean useCameraOptimization, Function<Tile, Double> travelAngleMapper) {
        return convert(customLevel, useCameraOptimization, new Function<ApplyEach, List<Tile>>() {

            private double currStaticAngle = AngleHelper.getNextStaticAngle(0.0, customLevel.getTiles().get(0).getTileMeta().getTravelAngle(), false);
            private boolean reversed = false;

            @Override
            public List<Tile> apply(ApplyEach applyEach) {
                return applyEach.getOneTimingTiles()
                        .stream()
                        .map(tile -> {
                            Double travelAngle = travelAngleMapper.apply(tile);

                            if (tile.getAngle() != ANGLE_MID_TILE) {
                                tile.setAngle(currStaticAngle);
                            }

                            if (tile.getActions(EventType.TWIRL).size() % 2 == 1) {
                                reversed = !reversed;
                            }

                            currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, travelAngle, reversed);
                            return new Tile(tile.getAngle(), new HashMap<>(tile.getActionMap()));
                        })
                        .collect(Collectors.toList());
            }
        });
    }

    public static CustomLevel convert(CustomLevel customLevel, boolean useCameraOptimization,
                                      Function<ApplyEach, List<Tile>> applyEachFunction)  {

        final List<Tile> tiles = customLevel.getTiles();

        final List<Tile> newTiles = new ArrayList<>();
        newTiles.add(new Tile(0.0, new HashMap<>(tiles.get(0).getActionMap())));

        final List<List<Integer>> newTileAmounts = new ArrayList<>(tiles.size());
        newTileAmounts.add(Arrays.asList(0, 1));

        for (int i = 1; i < tiles.size();) {
            List<Tile> oneTimingTiles = getSameTimingTiles(tiles, i);

            List<Tile> newTilesResult = applyEachFunction.apply(new ApplyEach(i, oneTimingTiles));
            newTileAmounts.add(Arrays.asList(i, newTilesResult.size()));
            newTiles.addAll(newTilesResult);

            i += oneTimingTiles.size();
        }

        final LevelSetting newLevelSetting = customLevel.getLevelSetting()
                .toBuilder().build();
        final CustomLevel newCustomLevel = new CustomLevel(newLevelSetting, newTiles);

        arrangeCustomLevelSync(customLevel, newCustomLevel, newTileAmounts);

        return newCustomLevel;
    }

    private static void arrangeCustomLevelSync(CustomLevel oldCustomLevel, CustomLevel newCustomLevel, List<List<Integer>> newTileAmountPairs) {

        List<Tile> oldTiles = oldCustomLevel.getTiles();
        List<Tile> newTiles = newCustomLevel.getTiles();

        // perceivedBpm
        // temporaryBpm
        // bpm

        double prevBpm = newCustomLevel.getLevelSetting().getBpm();
        int newTileIdx = 0;

        for (List<Integer> newTileAmountPair : newTileAmountPairs) {
            int oldTileIdx = newTileAmountPair.get(0);
            int newTileAmount = newTileAmountPair.get(1);

            List<Tile> timingTiles = getSameTimingTiles(oldTiles, oldTileIdx);
            List<Tile> newTimingTiles = newTiles.subList(newTileIdx, newTileIdx + newTileAmount);

            double timingTravelAngle = TileMeta.calculateTotalTravelAngle(timingTiles);
            double newTravelAngle = TileMeta.calculateTotalTravelAngle(newTimingTiles);


            List<Action> actionList = newTimingTiles.get(0).getActions(EventType.SET_SPEED);
            actionList.clear();

            if (oldTileIdx == 0) {

                long originalZeroAngleOffset = oldCustomLevel.getLevelSetting().getOffset();

                long originalAdditionalOffset = (long) (60000.0 / (oldCustomLevel.getLevelSetting().getBpm() * (180 / (timingTravelAngle - 180.0))));
                long newAdditionalOffset =  (long) (60000.0 / (newCustomLevel.getLevelSetting().getBpm() * (180 / (newTravelAngle - 180.0))));

                long additionalOffset = originalAdditionalOffset - newAdditionalOffset;

                newCustomLevel.getLevelSetting().setOffset(originalZeroAngleOffset + additionalOffset);
            }
            else if (newTileIdx + newTileAmount < newTiles.size()) {

                double timingBpm = timingTiles.get(timingTiles.size() - 1).getTileMeta().getBpm();

                double multiplyValue = newTravelAngle / timingTravelAngle;
                double currBpm = timingBpm * multiplyValue;

                // SetSpeed
                if (!NumberUtil.fuzzyEquals(currBpm, prevBpm)) {
                    if (!Double.isFinite(currBpm) || NumberUtil.fuzzyEquals(currBpm, 0.0)) {
                        System.err.println("Wrong TempBpm value (" + currBpm + ")");
                    }
                    actionList.add(new SetSpeed(SpeedType.BPM, currBpm, 1.0));
                }

                for (Tile newTile : newTimingTiles) {
                    fixAction(newTile, multiplyValue);
                }

                prevBpm = currBpm;
            }


            newTileIdx += newTileAmount;
        }

        fixFilterTiming(newCustomLevel);
    }

    /**
     * get same timing tiles as list(normal tile + n midspin tile)
     *
     * @param tiles all tiles of level
     * @param fromIndex get tile index.
     * @return same tile list including midspin
     */
    public static List<Tile> getSameTimingTiles(List<Tile> tiles, int fromIndex) {
        List<Tile> sameTimingTiles = new ArrayList<>();

        Tile tile = tiles.get(fromIndex);
        sameTimingTiles.add(tile);

        for (int i = fromIndex + 1; i < tiles.size() && tile.getTileMeta().getTravelAngle() == 0.0; i++) {
            tile = tiles.get(i);
            sameTimingTiles.add(tile);
        }

        return sameTimingTiles;
    }

    /**
     * fix tile's action timing
     *
     * @param tile tile object to fix action timing
     * @param multiplyValue multiply value about bpm duration, angle duration
     */
    private static void fixAction(Tile tile, double multiplyValue) {

        // CustomBackground
        editAction(tile, EventType.CUSTOM_BACKGROUND, (action) -> {
            CustomBackground a = (CustomBackground) action;
            return new CustomBackground(a.getColor(), a.getBgImage(), a.getImageColor(), a.getParallax(),
                    a.getBgDisplayMode(), a.getLockRot(), a.getLoopBG(), a.getUnscaledSize(),
                    a.getAngleOffset() * multiplyValue, a.getEventTag());
        });

        // AnimateTrack
        editAction(tile, EventType.ANIMATE_TRACK, (action) -> {
            AnimateTrack a = (AnimateTrack) action;
            return new AnimateTrack(a.getTrackAnimation(), a.getBeatsAhead() * multiplyValue,
                    a.getTrackDisappearAnimation(), a.getBeatsBehind() * multiplyValue);
        });

        // Flash
        editAction(tile, EventType.FLASH, (action) -> {
            Flash a = (Flash) action;
            return new Flash(a.getDuration() * multiplyValue, a.getPlane(), a.getStartColor(), a.getStartOpacity(),
                    a.getEndColor(), a.getEndOpacity(), a.getAngleOffset() * multiplyValue, a.getEase(), a.getEventTag());
        });

        // MoveCamera
        editAction(tile, EventType.MOVE_CAMERA, (action) -> {
            MoveCamera a = (MoveCamera) action;
            return new MoveCamera(a.getDuration() * multiplyValue, a.getRelativeTo(), a.getPosition(),
                    a.getRotation(), a.getZoom(), a.getAngleOffset() * multiplyValue, a.getEase(),
                    a.getEventTag());
        });

        // RecolorTrack
        editAction(tile, EventType.RECOLOR_TRACK, (action) -> {
            RecolorTrack a = (RecolorTrack) action;
            return new RecolorTrack(a.getStartTileNum(), a.getStartTilePosition(), a.getEndTileNum(), a.getEndTilePosition(), a.getTrackColorType(), a.getTrackColor(),
                    a.getSecondaryTrackColor(), a.getTrackColorAnimDuration(), a.getTrackColorPulse(),
                    a.getTrackPulseLength(), a.getTrackStyle(), a.getAngleOffset() * multiplyValue,
                    a.getEventTag());
        });

        // MoveTrack
        editAction(tile, EventType.MOVE_TRACK, (action) -> {
            MoveTrack a = (MoveTrack) action;
            return new MoveTrack(a.getStartTileNum(), a.getStartTilePosition(), a.getEndTileNum(), a.getEndTilePosition(), a.getDuration() * multiplyValue,
                    a.getPositionOffset(), a.getRotationOffset(), a.getScale(), a.getOpacity(),
                    a.getAngleOffset() * multiplyValue, a.getEase(), a.getEventTag());
        });

        // SetFilter
        editAction(tile, EventType.SET_FILTER, (action) -> {
            SetFilter a = (SetFilter) action;
            return new SetFilter(a.getFilter(), a.getEnabled(), a.getIntensity(), a.getDisableOthers(),
                    a.getAngleOffset() * multiplyValue, a.getEventTag());
        });

        // HallOfMirrors
        editAction(tile, EventType.HALL_OF_MIRRORS, (action) -> {
            HallOfMirrors a = (HallOfMirrors) action;
            return new HallOfMirrors(a.getEnabled(), a.getAngleOffset() * multiplyValue, a.getEventTag());
        });

        // ShakeScreen
        editAction(tile, EventType.SHAKE_SCREEN, (action) -> {
            ShakeScreen a = (ShakeScreen) action;
            return new ShakeScreen(a.getDuration() * multiplyValue, a.getStrength(), a.getIntensity(),
                    a.getFadeOut(), a.getAngleOffset() * multiplyValue, a.getEventTag());
        });

        // MoveDecorations
        editAction(tile, EventType.MOVE_DECORATIONS, (action) -> {
            MoveDecorations a = (MoveDecorations) action;
            return new MoveDecorations(a.getDuration() * multiplyValue, a.getTag(), a.getPositionOffset(),
                    a.getRotationOffset(), a.getScale(), a.getColor(), a.getOpacity(), a.getAngleOffset() * multiplyValue, a.getEase(),
                    a.getEventTag());
        });

        // RepeatEvents
        editAction(tile, EventType.REPEAT_EVENTS, (action) -> {
            RepeatEvents a = (RepeatEvents) action;
            return new RepeatEvents(a.getRepetitions(), a.getInterval() * multiplyValue, a.getTag());
        });

        // Bloom
        editAction(tile, EventType.BLOOM, (action) -> {
            Bloom a = (Bloom) action;
            return new Bloom(a.getEnabled(), a.getThreshold(), a.getIntensity(), a.getColor(),
                    a.getAngleOffset() * multiplyValue, a.getEventTag());
        });
    }

    /**
     * edit action with given function
     *
     * @param tile The tile has action to edit
     * @param eventType the eventType to edit
     * @param function individual edit function
     */
    private static void editAction(Tile tile, EventType eventType, Function<Action, Action> function) {
        List<Action> actions = tile.getActions(eventType);
        List<Action> newActionList = new ArrayList<>();

        for (Action action : actions) {
            newActionList.add(function.apply(action));
        }

        actions.clear();
        actions.addAll(newActionList);
    }

    public static void fixFilterTiming(CustomLevel customLevel) {

        for (Tile tile : customLevel.getTiles()) {
            TileMeta tileMeta = tile.getTileMeta();

            editAction(tile, EventType.SET_FILTER, (action) -> {
                SetFilter a = (SetFilter) action;

                double angleOffset = a.getAngleOffset();

                if (a.getDisableOthers() == Toggle.ENABLED &&
                        NumberUtil.fuzzyEquals(tileMeta.getTravelAngle(), angleOffset)) {
                    angleOffset = Math.max(angleOffset - 0.000001, 0.0);
                }

                return new SetFilter(a.getFilter(), a.getEnabled(), a.getIntensity(), a.getDisableOthers(),
                        angleOffset, a.getEventTag());
            });
        }
    }

    public static List<Tile> copyTiles(List<Tile> tiles) {
        return tiles.stream()
                .map(tile -> new Tile(tile.getAngle(), tile.getActionMap()))
                .collect(Collectors.toList());
    }

}
