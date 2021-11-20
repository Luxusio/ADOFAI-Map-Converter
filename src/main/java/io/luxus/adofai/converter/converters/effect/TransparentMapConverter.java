package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.MoveTrack;
import io.luxus.lib.adofai.action.type.Ease;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.TilePosition;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static io.luxus.adofai.converter.MapConverterBase.copyTiles;

public class TransparentMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {
        System.out.print("투명도(0~100):");
        int opacity = scanner.nextInt();
        scanner.nextLine();

        return new Object[] { opacity };
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {
        int opacity = (int) args[0];

        if (opacity < 0 || opacity > 100) {
            System.err.println("투명도는 0이상 100이하여야합니다. (opacity=" + opacity + ")");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Object... args) {
        return "Transparency " + args[0];
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        if (impossible(customLevel, args)) {
            return null;
        }

        int opacity = (int) args[0];

        Tile zeroTile = customLevel.getTiles().get(0);
        editOpacityEvents(zeroTile, opacity);
        zeroTile.getActions(EventType.MOVE_TRACK).add(getTransparentMoveTrack(opacity));

        return MapConverterBase.convert(customLevel, false,
                applyEach -> {
                    List<Tile> newTiles = copyTiles(applyEach.getOneTimingTiles());
                    newTiles.forEach(newTile -> editOpacityEvents(newTile, opacity));
                    return newTiles;
                });
    }

    private MoveTrack getTransparentMoveTrack(long opacity) {
        return new MoveTrack(0L, TilePosition.START, 0L, TilePosition.END,
                0.0, Arrays.asList(0.0, 0.0), 0.0, Arrays.asList(100L, 100L), opacity, 0.0, Ease.LINEAR, "");
    }

    private void editOpacityEvents(Tile tile, long opacity) {
        tile.getActions(EventType.ANIMATE_TRACK).clear();
        List<Action> actions = tile.getActions(EventType.MOVE_TRACK);

        List<Action> newActions = actions.stream()
                .map(action -> {
                    MoveTrack a = (MoveTrack) action;
                    return new MoveTrack(a.getStartTileNum(), a.getStartTilePosition(), a.getEndTileNum(), a.getEndTilePosition(),
                            a.getDuration(), a.getPositionOffset(), a.getRotationOffset(), a.getScale(), a.getOpacity() == 0 ? 0 : opacity,
                            a.getAngleOffset(), a.getEase(), a.getEventTag());
                })
                .collect(Collectors.toList());

        actions.clear();
        actions.addAll(newActions);
    }

}
