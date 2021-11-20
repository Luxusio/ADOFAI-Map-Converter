package io.luxus.adofai.converter.converters.effect;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.action.type.EventType;

import java.util.*;
import java.util.stream.Collectors;

import static io.luxus.adofai.converter.MapConverterBase.copyTiles;

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

        removeActions(customLevel.getTiles().get(0), eventTypeSet);
        return MapConverterBase.convert(customLevel, false,
                applyEach -> {
                    List<Tile> newTiles = copyTiles(applyEach.getOneTimingTiles());
                    newTiles.forEach(newTile -> removeActions(newTile, eventTypeSet));
                    return newTiles;
                });
    }

    private void removeActions(Tile tile, Set<EventType> eventTypeSet) {
        eventTypeSet.forEach(eventType -> tile.getActions(eventType).clear());
    }

}
