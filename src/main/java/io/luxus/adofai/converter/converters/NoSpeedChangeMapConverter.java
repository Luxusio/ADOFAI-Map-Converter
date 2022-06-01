package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.type.EventType;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Scanner;

public class NoSpeedChangeMapConverter implements MapConverter<NoSpeedChangeMapConverter.Parameters> {

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        System.out.print("목표 BPM:");
        double destBpm = scanner.nextDouble();
        scanner.nextLine();

        return new Parameters(destBpm);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {

        double possibleMaxBpm = getPossibleMaxBpm(customLevel.getTiles());
        if (parameters.destinationBpm > possibleMaxBpm) {
            System.err.println("destBpm이 너무 빠릅니다. " + possibleMaxBpm + "bpm 이하로 입력 해 주세요.");
            return true;
        }

        if (parameters.destinationBpm <= 0) {
            System.err.println("destBpm은 0보다 커야합니다.");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
        return  "NoSpeedChange " + parameters.destinationBpm + "BPM";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        return MapConverterBase.convertBasedOnTravelAngle(customLevel, false,
                tile -> tile.getTileMeta().getTravelAngle() * parameters.destinationBpm /  tile.getTileMeta().getBpm(),
                tileBuilder -> tileBuilder.removeActions(EventType.SET_SPEED),
                customLevelBuilder -> customLevelBuilder.getLevelSettingBuilder().bpm(parameters.destinationBpm));
    }

    public static double getPossibleMaxBpm(List<Tile> tiles) {
        double possibleMaxBpm = Double.POSITIVE_INFINITY;
        for (Tile tile : tiles) {
            TileMeta tileMeta = tile.getTileMeta();
            possibleMaxBpm = Math.min(tileMeta.getPossibleMaxBpm(), possibleMaxBpm);
        }
        return possibleMaxBpm;
    }

    @RequiredArgsConstructor
    public static class Parameters {
        private final double destinationBpm;
    }

}
