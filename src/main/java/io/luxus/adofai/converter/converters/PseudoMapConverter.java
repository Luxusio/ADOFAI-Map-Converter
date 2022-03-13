package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.helper.AngleHelper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class PseudoMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {
        System.out.print("동타 수 : ");
        int pseudo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("동타 최대 각도 : ");
        double pseudoAngle = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("colorTrack을 제거하시겠습니까? (y/n) (ColorTrack이 많을 시 게임이 멈출 수 있습니다.) : ");
        boolean removeColorTrack = scanner.nextLine().equalsIgnoreCase("y");

        return new Object[] { pseudo, pseudoAngle, removeColorTrack };
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {
        int pseudo = (int) args[0];
        double pseudoAngle = (double) args[1];

        if (pseudo < 1) {
            System.err.println("Pseudo 값이 너무 낮습니다! 1 이상으로 해주세요!");
            return true;
        }
        if (pseudoAngle <= 0) {
            System.err.println("동타 최대 각도는 0도보다 커야합니다.");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Object... args) {
        return args[0] + " Pseudo";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        if (impossible(customLevel, args)) {
            return null;
        }

        int pseudo = (int) args[0];
        double pseudoAngle = (double) args[1];
        boolean removeColorTrackEvents = (boolean) args[2];

        return MapConverterBase.convert(customLevel, false,
                applyEach -> {
                    List<Tile> oneTimingTiles = applyEach.getOneTimingTiles();
                    List<Tile.Builder> newTileBuilders = oneTimingTiles.stream()
                            .map(tile -> new Tile.Builder().from(tile))
                            .collect(Collectors.toList());

                    double travelAngle = TileMeta.calculateTotalTravelAngle(oneTimingTiles);

                    double eachHitTravelAngle = Math.min(pseudoAngle, travelAngle / 2 / pseudo);

                    TileMeta lastTileMeta = oneTimingTiles.get(oneTimingTiles.size() - 1).getTileMeta();
                    double currStaticAngle = lastTileMeta.getStaticAngle();

                    if (oneTimingTiles.size() % 2 == 0) {
                        currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, 0.0, lastTileMeta.isReversed());
                    }

                    currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, eachHitTravelAngle, lastTileMeta.isReversed());

                    if (removeColorTrackEvents) {
                        newTileBuilders.forEach(newTileBuilder -> {
                            newTileBuilder.removeActions(EventType.RECOLOR_TRACK);
                            newTileBuilder.removeActions(EventType.COLOR_TRACK);
                        });
                    }

                    for (int i = 1; i < pseudo; i++) {
                        newTileBuilders.add(new Tile.Builder().angle(currStaticAngle));
                        newTileBuilders.add(new Tile.Builder().angle(ANGLE_MID_TILE));
                        currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, eachHitTravelAngle, lastTileMeta.isReversed());
                        currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, 0, lastTileMeta.isReversed());
                    }

                    return newTileBuilders;
                });
    }
}
