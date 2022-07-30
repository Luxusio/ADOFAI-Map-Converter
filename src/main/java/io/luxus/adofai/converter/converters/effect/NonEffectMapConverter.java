package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.EventType;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static io.luxus.adofai.converter.i18n.I18nCode.*;

@RequiredArgsConstructor
public class NonEffectMapConverter implements MapConverter<NonEffectMapConverter.Parameters> {

    private final I18n i18n;

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        i18n.println(NON_EFFECT_MAP_CONVERTER_LIST, Arrays.stream(EventType.values())
                .filter(eventType -> eventType != EventType.UNKNOWN)
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
        i18n.print(NON_EFFECT_MAP_CONVERTER_LIST_INPUT_MESSAGE);
        String eventTypes = scanner.nextLine();

        Set<EventType> eventTypeSet = Arrays.stream(eventTypes.split(","))
                .map(String::trim)
                .map(eventTypeName -> {
                    try {
                        return EventType.valueOf(eventTypeName);
                    } catch (IllegalArgumentException exception) {
                        i18n.printlnErr(NON_EFFECT_MAP_CONVERTER_ERROR_UNKNOWN_EVENT_TYPE, eventTypeName);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return new Parameters(eventTypeSet);
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Parameters parameters) {
        if (parameters.eventTypes.isEmpty()) {
            i18n.println(NON_EFFECT_MAP_CONVERTER_LIST_ERROR_EMPTY);
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
        return "NonEffect";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
        if (impossible(customLevel, parameters)) {
            return null;
        }

        return MapConverterBase.convert(customLevel, false,
                applyEach -> applyEach.getOneTimingTiles().stream()
                        .map(tile -> new Tile.Builder().from(tile))
                        .peek(newTileBuilder -> parameters.eventTypes.forEach(newTileBuilder::removeActions))
                        .collect(Collectors.toList()),
                customLevelBuilder -> {
                    Tile.Builder tileBuilder = customLevelBuilder.getTileBuilders().get(0);
                    parameters.eventTypes.forEach(tileBuilder::removeActions);
                });
    }

    @RequiredArgsConstructor
    public static class Parameters {
        private final Set<EventType> eventTypes;
    }

}
