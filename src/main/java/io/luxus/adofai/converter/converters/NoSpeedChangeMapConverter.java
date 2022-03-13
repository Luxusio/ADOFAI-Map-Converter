package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.type.EventType;

import java.util.List;
import java.util.Scanner;

public class NoSpeedChangeMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {
        System.out.print("목표 BPM:");
        double destBpm = scanner.nextDouble();
        scanner.nextLine();

        return new Object[] { destBpm };
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {
        double destBpm = (double) args[0];

        double possibleMaxBpm = getPossibleMaxBpm(customLevel.getTiles());
        if (destBpm > possibleMaxBpm) {
            System.err.println("destBpm이 너무 빠릅니다. " + possibleMaxBpm + "bpm 이하로 입력 해 주세요.");
            return true;
        }

        if (destBpm <= 0) {
            System.err.println("destBpm은 0보다 커야합니다.");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Object... args) {
        double destBpm = (double) args[0];
        return  "NoSpeedChange " + destBpm + "BPM";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        double destBpm = (double) args[0];

        return MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> tile.getTileMeta().getTravelAngle() * destBpm /  tile.getTileMeta().getBpm(),
                tileBuilder -> tileBuilder.removeActions(EventType.SET_SPEED),
                customLevelBuilder -> customLevelBuilder.getLevelSettingBuilder().bpm(destBpm));
    }

    public static double getPossibleMaxBpm(List<Tile> tiles) {
        double possibleMaxBpm = Double.POSITIVE_INFINITY;
        for (Tile tile : tiles) {
            TileMeta tileMeta = tile.getTileMeta();
            possibleMaxBpm = Math.min(tileMeta.getPossibleMaxBpm(), possibleMaxBpm);
        }
        return possibleMaxBpm;
    }

}