package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.adofai.converter.i18n.I18nCode;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.action.MultiPlanet;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static io.luxus.adofai.converter.i18n.I18nCode.*;

@RequiredArgsConstructor
public class PlanetNumberMapConverter implements MapConverter<PlanetNumberMapConverter.Parameters> {

    private final I18n i18n;

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        i18n.print(PLANET_NUMBER_MAP_CONVERTER_PLANET_NUMBER);
        long planetNumber = scanner.nextLong();
        scanner.nextLine();

        i18n.print(I18nCode.PLANET_NUMBER_MAP_CONVERTER_KEEP_ORIGINAL_SHAPE);
        boolean keepOriginalShape = scanner.nextBoolean();
        scanner.nextLine();

        return new Parameters(planetNumber, keepOriginalShape);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        if (parameters.planetNumber < 2) {
            i18n.printlnErr(PLANET_NUMBER_MAP_CONVERTER_PLANET_NUMBER_ERROR);
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
