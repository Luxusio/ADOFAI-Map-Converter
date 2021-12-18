package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.type.action.*;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.SpeedType;
import io.luxus.lib.adofai.type.Toggle;
import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.helper.TileHelper;
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
                double originalZeroTileTravelMs = TileHelper.calculateZeroTileTravelMs(oldCustomLevel);
                double newZeroTileTravelMs = TileHelper.calculateZeroTileTravelMs(newCustomLevel);

                long originalStraightTravelMs = oldCustomLevel.getLevelSetting().getOffset();

                double additionalTravelMs = originalZeroTileTravelMs- newZeroTileTravelMs;

                newCustomLevel.getLevelSetting().setOffset(originalStraightTravelMs + (long) additionalTravelMs);
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
            return new CustomBackground.Builder().from(a)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // AnimateTrack
        editAction(tile, EventType.ANIMATE_TRACK, (action) -> {
            AnimateTrack a = (AnimateTrack) action;
            return new AnimateTrack.Builder().from(a)
                    .beatsAhead(a.getBeatsAhead() * multiplyValue)
                    .beatsBehind(a.getBeatsBehind() * multiplyValue)
                    .build();
        });

        // Flash
        editAction(tile, EventType.FLASH, (action) -> {
            Flash a = (Flash) action;
            return new Flash.Builder().from(a)
                    .duration(a.getDuration() * multiplyValue)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // MoveCamera
        editAction(tile, EventType.MOVE_CAMERA, (action) -> {
            MoveCamera a = (MoveCamera) action;
            return new MoveCamera.Builder().from(a)
                    .duration(a.getDuration() * multiplyValue)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // RecolorTrack
        editAction(tile, EventType.RECOLOR_TRACK, (action) -> {
            RecolorTrack a = (RecolorTrack) action;
            return new RecolorTrack.Builder().from(a)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // MoveTrack
        editAction(tile, EventType.MOVE_TRACK, (action) -> {
            MoveTrack a = (MoveTrack) action;
            return new MoveTrack.Builder().from(a)
                    .duration(a.getDuration() * multiplyValue)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // SetFilter
        editAction(tile, EventType.SET_FILTER, (action) -> {
            SetFilter a = (SetFilter) action;
            return new SetFilter.Builder().from(a)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // HallOfMirrors
        editAction(tile, EventType.HALL_OF_MIRRORS, (action) -> {
            HallOfMirrors a = (HallOfMirrors) action;
            return new HallOfMirrors.Builder().from(a)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // ShakeScreen
        editAction(tile, EventType.SHAKE_SCREEN, (action) -> {
            ShakeScreen a = (ShakeScreen) action;
            return new ShakeScreen.Builder().from(a)
                    .duration(a.getDuration() * multiplyValue)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // MoveDecorations
        editAction(tile, EventType.MOVE_DECORATIONS, (action) -> {
            MoveDecorations a = (MoveDecorations) action;
            return new MoveDecorations.Builder().from(a)
                    .duration(a.getDuration() * multiplyValue)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        // RepeatEvents
        editAction(tile, EventType.REPEAT_EVENTS, (action) -> {
            RepeatEvents a = (RepeatEvents) action;
            return new RepeatEvents.Builder().from(a)
                    .interval(a.getInterval() * multiplyValue)
                    .build();
        });

        // Bloom
        editAction(tile, EventType.BLOOM, (action) -> {
            Bloom a = (Bloom) action;
            return new Bloom.Builder().from(a)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

        editAction(tile, EventType.PLAY_SOUND, (action) -> {
            PlaySound a = (PlaySound) action;
            return new PlaySound.Builder().from(a)
                    .angleOffset(a.getAngleOffset() * multiplyValue)
                    .build();
        });

    }

    /**
     * edit action with given function
     *
     * @param tile The tile has action to edit
     * @param eventType the eventType to edit
     * @param function individual edit function
     */
    // todo : use generic(<T extends Action>) to remove casting
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

                return new SetFilter.Builder().from(a)
                        .angleOffset(angleOffset)
                        .build();
            });
        }
    }

    public static List<Tile> copyTiles(List<Tile> tiles) {
        return tiles.stream()
                .map(tile -> new Tile(tile.getAngle(), tile.getActionMap()))
                .collect(Collectors.toList());
    }

}
