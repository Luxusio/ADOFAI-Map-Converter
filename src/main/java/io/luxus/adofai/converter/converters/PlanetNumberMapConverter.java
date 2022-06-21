package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.action.MultiPlanet;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class PlanetNumberMapConverter implements MapConverter<PlanetNumberMapConverter.Parameters> {

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        System.out.print("행성 수 : ");
        long planetNumber = scanner.nextLong();
        scanner.nextLine();

        System.out.print("원본 맵 형태 유지 여부(true, false) : ");
        boolean keepOriginalShape = scanner.nextBoolean();
        scanner.nextLine();

        return new Parameters(planetNumber, keepOriginalShape);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        if (parameters.planetNumber < 2) {
            System.err.println("행성 수 값이 너무 낮습니다! 2와 같거나 큰 값으로 해주세요!");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
        return parameters.planetNumber + " Planets";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        if (impossible(customLevel, parameters)) {
            return null;
        }

        AtomicBoolean haveToAddEvent = new AtomicBoolean(true);

        if (parameters.keepShape) {
            return MapConverterBase.convert(
                    customLevel,
                    false,
                    applyEach -> applyEach.getOneTimingTiles().stream()
                            .map(tile -> new Tile.Builder().from(tile))
                            .peek(newTileBuilder -> {
                                if (haveToAddEvent.get()) {
                                    if (parameters.planetNumber > 2) {
                                        newTileBuilder.addAction(new MultiPlanet.Builder().planets(parameters.planetNumber).build());
                                    }
                                    haveToAddEvent.set(false);
                                }
                            })
                            .collect(Collectors.toList())
            );
        }
        else {
            return MapConverterBase.convertBasedOnTravelAngle(
                    customLevel,
                    false,
                    tile -> tile.getTileMeta().getTravelAngle(),
                    tileBuilder -> {
                        tileBuilder.removeActions(EventType.MULTI_PLANET);
                        if (haveToAddEvent.get()) {
                            if (parameters.planetNumber > 2) {
                                tileBuilder.addAction(new MultiPlanet.Builder().planets(parameters.planetNumber).build());
                            }
                            haveToAddEvent.set(false);
                        }
                    },
                    customLevelBuilder -> {
                    }
            );
        }
    }

    @RequiredArgsConstructor
    public static class Parameters {
        private final long planetNumber;
        private final boolean keepShape;
    }

}