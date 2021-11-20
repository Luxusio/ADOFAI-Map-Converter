package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.MoveTrack;
import io.luxus.lib.adofai.action.type.Ease;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.TilePosition;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.luxus.adofai.converter.MapConverterBase.copyTiles;

public class MapEffectConverter {

    public static CustomLevel onlyBpmSetConvert(String path) throws IOException {
        CustomLevel customLevel = CustomLevelParser.readPath(path);
        return MapConverterBase.convert(customLevel, false,
                applyEach -> copyTiles(applyEach.getOneTimingTiles()));
    }

    public static CustomLevel removeEffectConvert(String path, boolean removeDecoration, boolean removeTileMove, boolean removeCameraEvents, boolean removeFlash) throws IOException {
        CustomLevel customLevel = CustomLevelParser.readPath(path);
        removeActions(customLevel.getTiles().get(0), removeDecoration, removeTileMove, removeCameraEvents, removeFlash);
        return MapConverterBase.convert(customLevel, false,
                applyEach -> {
                    List<Tile> newTiles = copyTiles(applyEach.getOneTimingTiles());
                    newTiles.forEach(newTile -> removeActions(newTile, removeDecoration, removeTileMove, removeCameraEvents, removeFlash));
                    return newTiles;
                });
    }

    public static CustomLevel transparentConvert(String path, int opacity) throws IOException {
        CustomLevel customLevel = CustomLevelParser.readPath(path);

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

    private static MoveTrack getTransparentMoveTrack(long opacity) {
        return new MoveTrack(0L, TilePosition.START, 0L, TilePosition.END,
                0.0, Arrays.asList(0.0, 0.0), 0.0, Arrays.asList(100L, 100L), opacity, 0.0, Ease.LINEAR, "");
    }

    private static void editOpacityEvents(Tile tile, long opacity) {
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

    private static void removeActions(Tile tile, boolean removeDecoration, boolean removeTileMove, boolean removeCameraEvents, boolean removeFlash) {
        tile.getActions(EventType.BLOOM).clear();
        tile.getActions(EventType.ANIMATE_TRACK).clear();
        tile.getActions(EventType.HALL_OF_MIRRORS).clear();
        tile.getActions(EventType.SET_FILTER).clear();

        if (removeDecoration) {
            tile.getActions(EventType.ADD_DECORATION).clear();
            tile.getActions(EventType.MOVE_DECORATIONS).clear();
        }

        if (removeTileMove) {
            tile.getActions(EventType.MOVE_TRACK).clear();
        }

        if (removeCameraEvents) {
            tile.getActions(EventType.MOVE_CAMERA).clear();
        }

        if (removeFlash) {
            tile.getActions(EventType.FLASH).clear();
        }
    }



}
