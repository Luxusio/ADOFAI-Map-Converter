package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.action.MoveTrack;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TilePosition;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

import java.util.Scanner;
import java.util.stream.Collectors;


public class TransparentMapConverter implements MapConverter<TransparentMapConverter.Parameters> {

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        System.out.print("투명도(0~100):");
        double opacity = scanner.nextDouble();
        scanner.nextLine();

        return new Parameters(opacity);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        if (parameters.opacity < 0 || parameters.opacity > 100) {
            System.err.println("투명도는 0이상 100이하여야합니다. (opacity=" + parameters.opacity + ")");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
        return "Transparency " + parameters.opacity;
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        if (impossible(customLevel, parameters)) {
            return null;
        }

        return MapConverterBase.convert(customLevel, false,
                applyEach -> applyEach.getOneTimingTiles().stream()
                        .map(tile -> new Tile.Builder().from(tile))
                        .peek(newTileBuilder -> editOpacityEvents(newTileBuilder, parameters.opacity))
                        .collect(Collectors.toList()),
                customLevelBuilder -> {
                    Tile.Builder zeroTile = customLevelBuilder.getTileBuilders().get(0);
                    editOpacityEvents(zeroTile, parameters.opacity);
                    zeroTile.addAction(getTransparentMoveTrack(parameters.opacity));
                });
    }

    private MoveTrack getTransparentMoveTrack(double opacity) {
        return new MoveTrack.Builder()
                .startTile(Pair.with(0L, TilePosition.START))
                .endTile(Pair.with(0L, TilePosition.END))
                .duration(0.0)
                .opacity(opacity)
                .build();
    }

    private void editOpacityEvents(Tile.Builder tileBuilder, double opacity) {
        tileBuilder.removeActions(EventType.ANIMATE_TRACK);
        tileBuilder.editActions(EventType.MOVE_TRACK, MoveTrack.class, a ->
                new MoveTrack.Builder().from(a)
                        .opacity(a.getOpacity() == 0 ? 0 : opacity)
                        .build());
    }

    @RequiredArgsConstructor
    public static class Parameters {
        private final double opacity;
    }

}
