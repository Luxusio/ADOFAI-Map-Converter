package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.EventType;

import java.util.*;
import java.util.stream.Collectors;

public class NonEffectMapConverter implements MapConverter {

    @Override
    public Object[] prepareParameters(Scanner scanner) {
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

        return new Object[] { eventTypeSet };
    }

    @Override
    public boolean impossible(CustomLevel customLevel, Object... args) {
        @SuppressWarnings("unchecked")
        Set<EventType> eventTypeSet = (Set<EventType>) args[0];

        if (eventTypeSet.isEmpty()) {
            System.err.println("제거할 effect가 없습니다.");
            return true;
        }

        return false;
    }

    @Override
    public String getLevelPostfix(CustomLevel customLevel, Object[] args) {
        return "NonEffect";
    }

    @Override
    public CustomLevel convert(CustomLevel customLevel, Object... args) {
        if (impossible(customLevel, args)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Set<EventType> eventTypeSet = (Set<EventType>) args[0];

        return MapConverterBase.convert(customLevel, false,
                applyEach -> applyEach.getOneTimingTiles().stream()
                        .map(tile -> new Tile.Builder().from(tile))
                        .peek(newTileBuilder -> eventTypeSet.forEach(newTileBuilder::removeActions))
                        .collect(Collectors.toList()),
                customLevelBuilder -> {
                    Tile.Builder tileBuilder = customLevelBuilder.getTileBuilders().get(0);
                    eventTypeSet.forEach(tileBuilder::removeActions);
                });
    }

}
