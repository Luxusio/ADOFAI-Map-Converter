package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.action.MoveTrack;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TilePosition;

import java.util.Scanner;
import java.util.stream.Collectors;


public class TransparentMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {
        System.out.print("투명도(0~100):");
        double opacity = scanner.nextDouble();
        scanner.nextLine();

        return new Object[] { opacity };
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {
        double opacity = (double) args[0];

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

        double opacity = (double) args[0];

        return MapConverterBase.convert(customLevel, false,
                applyEach -> applyEach.getOneTimingTiles().stream()
                        .map(tile -> new Tile.Builder().from(tile))
                        .peek(newTileBuilder -> editOpacityEvents(newTileBuilder, opacity))
                        .collect(Collectors.toList()),
                customLevelBuilder -> {
                    Tile.Builder zeroTile = customLevelBuilder.getTileBuilders().get(0);
                    editOpacityEvents(zeroTile, opacity);
                    zeroTile.addAction(getTransparentMoveTrack(opacity));
                });
    }

    private MoveTrack getTransparentMoveTrack(double opacity) {
        return new MoveTrack.Builder()
                .startTilePosition(TilePosition.START).endTilePosition(TilePosition.END)
                .duration(0.0)
                .opacity(opacity)
                .build();
    }

    private void editOpacityEvents(Tile.Builder tileBuilder, double opacity) {
        tileBuilder.removeActions(EventType.ANIMATE_TRACK);
        tileBuilder.<MoveTrack>editActions(EventType.MOVE_TRACK, a -> new MoveTrack.Builder().from(a)
                .opacity(a.getOpacity() == 0 ? 0 : opacity)
                .build());
    }

}
