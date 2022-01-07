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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MapConverterBase {

    @Getter
    @RequiredArgsConstructor
    public static class ApplyEach {
        private final int floor;
        private final List<Tile> oneTimingTiles;
    }


    public static CustomLevel convertBasedOnTravelAngle(CustomLevel customLevel, boolean useCameraOptimization,
                                                        Function<Tile, Double> travelAngleMapper,
                                                        Consumer<Tile.Builder> tileBuilderConsumer) {
        return convertBasedOnTravelAngle(customLevel, useCameraOptimization, travelAngleMapper, tileBuilderConsumer, dummy -> {});
    }

    public static CustomLevel convertBasedOnTravelAngle(CustomLevel customLevel, boolean useCameraOptimization,
                                                        Function<Tile, Double> travelAngleMapper,
                                                        Consumer<Tile.Builder> tileBuilderConsumer,
                                                        Consumer<CustomLevel.Builder> customLevelConsumer) {
        return convert(customLevel, useCameraOptimization, new Function<ApplyEach, List<Tile.Builder>>() {

            private double currStaticAngle = AngleHelper.getNextStaticAngle(0.0, customLevel.getTiles().get(0).getTileMeta().getTravelAngle(), false);
            private boolean reversed = false;

            @Override
            public List<Tile.Builder> apply(ApplyEach applyEach) {
                return applyEach.getOneTimingTiles()
                        .stream()
                        .map(tile -> {
                            Double travelAngle = travelAngleMapper.apply(tile);

                            Tile.Builder builder = new Tile.Builder().from(tile);
                            tileBuilderConsumer.accept(builder);

                            if (!AngleHelper.isMidAngle(builder.getAngle())) {
                                builder.angle(currStaticAngle);
                            }

                            if (builder.getActions(EventType.TWIRL).size() % 2 == 1) {
                                reversed = !reversed;
                            }

                            currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, travelAngle, reversed);

                            return builder;
                        })
                        .collect(Collectors.toList());
            }
        }, customLevelConsumer);
    }

    public static CustomLevel convert(CustomLevel customLevel, boolean useCameraOptimization,
                                      Function<ApplyEach, List<Tile.Builder>> applyEachFunction) {
        return convert(customLevel, useCameraOptimization, applyEachFunction, dummy -> {});
    }

    public static CustomLevel convert(CustomLevel customLevel, boolean useCameraOptimization,
                                      Function<ApplyEach, List<Tile.Builder>> applyEachFunction,
                                      Consumer<CustomLevel.Builder> customLevelConsumer)  {

        final List<Tile> tiles = customLevel.getTiles();

        final List<Tile.Builder> newTileBuilders = new ArrayList<>();
        newTileBuilders.add(new Tile.Builder().actionMap(tiles.get(0).getActionMap()));

        final List<List<Integer>> newTileAmounts = new ArrayList<>(tiles.size());
        newTileAmounts.add(Arrays.asList(0, 1));

        for (int i = 1; i < tiles.size();) {
            List<Tile> oneTimingTiles = getSameTimingTiles(tiles, i);

            List<Tile.Builder> newTilesResult = applyEachFunction.apply(new ApplyEach(i, oneTimingTiles));
            newTileAmounts.add(Arrays.asList(i, newTilesResult.size()));
            newTileBuilders.addAll(newTilesResult);

            i += oneTimingTiles.size();
        }

        final CustomLevel.Builder newCustomLevelBuilder = new CustomLevel.Builder()
                .levelSetting(customLevel.getLevelSetting())
                .tileBuilders(newTileBuilders);

        customLevelConsumer.accept(newCustomLevelBuilder);
        return arrangeCustomLevelSync(customLevel, newCustomLevelBuilder.build(), newTileAmounts);
    }

    private static CustomLevel arrangeCustomLevelSync(CustomLevel oldCustomLevel, CustomLevel newCustomLevel, List<List<Integer>> newTileAmountPairs) {

        CustomLevel.Builder newCustomLevelBuilder = new CustomLevel.Builder().from(newCustomLevel);

        List<Tile> oldTiles = oldCustomLevel.getTiles();
        List<Tile> newTiles = newCustomLevel.getTiles();
        List<Tile.Builder> newTileBuilders = newCustomLevelBuilder.getTileBuilders();

        // perceivedBpm
        // temporaryBpm
        // bpm

        double prevBpm = newCustomLevel.getLevelSetting().getBpm();
        int newTileIdx = 0;

        for (List<Integer> newTileAmountPair : newTileAmountPairs) {
            int oldTileIdx = newTileAmountPair.get(0);
            int newTileAmount = newTileAmountPair.get(1);

            List<Tile> timingTiles = getSameTimingTiles(oldTiles, oldTileIdx);
            List<Tile.Builder> newTimingTileBuilders = newTileBuilders.subList(newTileIdx, newTileIdx + newTileAmount);

            double timingTravelAngle = TileMeta.calculateTotalTravelAngle(timingTiles);
            double newTravelAngle = TileMeta.calculateTotalTravelAngle(newTiles.subList(newTileIdx, newTileIdx + newTileAmount));

            newTimingTileBuilders.forEach(builder -> builder.removeActions(EventType.SET_SPEED));

            if (oldTileIdx == 0) {
                double originalZeroTileTravelMs = TileHelper.calculateZeroTileTravelMs(oldCustomLevel);
                double newZeroTileTravelMs = TileHelper.calculateZeroTileTravelMs(newCustomLevel);

                long originalStraightTravelMs = oldCustomLevel.getLevelSetting().getOffset();

                double additionalTravelMs = originalZeroTileTravelMs- newZeroTileTravelMs;

                newCustomLevelBuilder.getLevelSettingBuilder()
                        .offset(originalStraightTravelMs + (long) additionalTravelMs);
            }
            else if (newTileIdx + newTileAmount < newTiles.size()) {

                double timingBpm = timingTiles.get(timingTiles.size() - 1).getTileMeta().getBpm();

                double multiplyValue = newTravelAngle / timingTravelAngle;
                double currBpm = timingBpm * multiplyValue;

                // SetSpeed
                if (!NumberUtil.fuzzyEquals(currBpm, prevBpm)) {
                    System.out.println("setSpeed " + newTileIdx + "~" + (newTileIdx + newTileAmount) + "(" + currBpm + ", " + timingBpm + ", " +
                            "multiplyValue=" + multiplyValue +
                            ", newTravelAngle=" + newTravelAngle +
                            ", timingTravelAngle=" + timingTravelAngle + ")");

                    if (!Double.isFinite(currBpm) || NumberUtil.fuzzyEquals(currBpm, 0.0)) {
                        System.err.println("Wrong TempBpm value (" + currBpm + ", " + timingBpm + ", " +
                                "multiplyValue=" + multiplyValue +
                                ", newTravelAngle=" + newTravelAngle +
                                ", timingTravelAngle=" + timingTravelAngle + ")");
                    }
                    newTimingTileBuilders.get(0).addAction(
                            new SetSpeed.Builder()
                                    .beatsPerMinute(currBpm)
                                    .build());
                }

                newTimingTileBuilders
                        .forEach(builder -> fixAction(builder, multiplyValue));

                prevBpm = currBpm;
            }


            newTileIdx += newTileAmount;
        }

        return newCustomLevelBuilder.build();
        //return fixFilterTiming(newCustomLevelBuilder.build());
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
     * fix tileBuilder's action timing
     *
     * @param tileBuilder tileBuilder object to fix action timing
     * @param multiplyValue multiply value about bpm duration, angle duration
     */
    private static void fixAction(Tile.Builder tileBuilder, double multiplyValue) {
        tileBuilder
                .<CustomBackground>editActions(EventType.CUSTOM_BACKGROUND, a -> new CustomBackground.Builder().from(a)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<AnimateTrack>editActions(EventType.ANIMATE_TRACK, a -> new AnimateTrack.Builder().from(a)
                        .beatsAhead(a.getBeatsAhead() * multiplyValue)
                        .beatsBehind(a.getBeatsBehind() * multiplyValue)
                        .build())
                .<Flash>editActions(EventType.FLASH, a -> new Flash.Builder().from(a)
                        .duration(a.getDuration() * multiplyValue)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<MoveCamera>editActions(EventType.MOVE_CAMERA, a -> new MoveCamera.Builder().from(a)
                        .duration(a.getDuration() * multiplyValue)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<RecolorTrack>editActions(EventType.RECOLOR_TRACK, a -> new RecolorTrack.Builder().from(a)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<MoveTrack>editActions(EventType.MOVE_TRACK, a -> new MoveTrack.Builder().from(a)
                        .duration(a.getDuration() * multiplyValue)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<SetFilter>editActions(EventType.SET_FILTER, a -> new SetFilter.Builder().from(a)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<HallOfMirrors>editActions(EventType.HALL_OF_MIRRORS, a -> new HallOfMirrors.Builder().from(a)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<ShakeScreen>editActions(EventType.SHAKE_SCREEN, a -> new ShakeScreen.Builder().from(a)
                        .duration(a.getDuration() * multiplyValue)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<MoveDecorations>editActions(EventType.MOVE_DECORATIONS, a -> new MoveDecorations.Builder().from(a)
                        .duration(a.getDuration() * multiplyValue)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<RepeatEvents>editActions(EventType.REPEAT_EVENTS, a -> new RepeatEvents.Builder().from(a)
                        .interval(a.getInterval() * multiplyValue)
                        .build())
                .<Bloom>editActions(EventType.BLOOM, a -> new Bloom.Builder().from(a)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build())
                .<PlaySound>editActions(EventType.PLAY_SOUND, a -> new PlaySound.Builder().from(a)
                        .angleOffset(a.getAngleOffset() * multiplyValue)
                        .build());
    }

    public static CustomLevel fixFilterTiming(CustomLevel customLevel) {

        CustomLevel.Builder customLevelBuilder = new CustomLevel.Builder().from(customLevel);

        customLevel.getTiles().stream()
                .map(Tile::getTileMeta)
                .forEach(tileMeta -> customLevelBuilder.getTileBuilders().get(tileMeta.getFloor())
                        .<SetFilter>editActions(EventType.SET_FILTER, a -> {

                            double angleOffset = a.getAngleOffset();

                            if (a.getDisableOthers() == Toggle.ENABLED &&
                                    (angleOffset > tileMeta.getTravelAngle() ||
                                            NumberUtil.fuzzyEquals(angleOffset, tileMeta.getTravelAngle()))) {
                                angleOffset = Math.max(angleOffset - 0.0001, 0.0);
                            }

                            return new SetFilter.Builder().from(a)
                                    .angleOffset(angleOffset)
                                    .build();
                        }));

        return customLevelBuilder.build();
    }

}
