package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.EventType;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class NonEffectMapConverter implements MapConverter<NonEffectMapConverter.Parameters> {

    @Override
    public Parameters prepareParameters(Scanner scanner) {
        System.out.println("목록 : " + Arrays.stream(EventType.values())
                .filter(eventType -> eventType != EventType.UNKNOWN)
                .collect(Collectors.toList()));
        System.out.print("제거하고 싶은 effect를 콤마(,)를 통해 구분해서 입력해주세요 : ");
        String eventTypes = scanner.nextLine();

        Set<EventType> eventTypeSet = Arrays.stream(eventTypes.split(","))
                .map(String::trim)
                .map(eventTypeName -> {
                    try {
                        return EventType.valueOf(eventTypeName);
                    } catch (IllegalArgumentException exception) {
                        System.err.println("알 수 없는 event type 입니다!(" + eventTypeName + ")");
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
            System.err.println("제거할 effect가 없습니다.");
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
