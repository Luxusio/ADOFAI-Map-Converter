package io.luxus.lib.adofai;

import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.type.action.Action;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.action.Twirl;
import io.luxus.lib.adofai.util.NumberUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.helper.AngleHelper.isMidAngle;
import static io.luxus.lib.adofai.util.NumberUtil.generalizeAngle;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Tile {

    private final Double angle;
    private final Map<EventType, List<Action>> actionMap;
    private final TileMeta tileMeta;

    public List<Action> getActions(EventType eventType) {
        return Collections.unmodifiableList(
                actionMap.get(eventType));
    }

    @ToString
    public static class Builder {

        private Double angle = 0.0;
        private final Map<EventType, List<Action>> actionMap = new EnumMap<>(EventType.class);

        public Builder() {
        }

        public Builder self() {
            return this;
        }

        public Builder from(Tile tile) {
            return self()
                    .angle(tile.angle)
                    .actionMap(tile.actionMap);
        }

        public Builder angle(Double angle) {
            if (isMidAngle(angle)) {
                this.angle = ANGLE_MID_TILE;
            }
            else {
                Objects.requireNonNull(angle);
                this.angle = generalizeAngle(angle);
            }
            return self();
        }

        public Builder actionMap(Map<EventType, List<Action>> actionMap) {
            try {
                actionMap.values()
                        .forEach(this::assertCanAdd);
            } catch (RuntimeException e) {
                throw new RuntimeException("Cannot add actionMap", e);
            }

            this.actionMap.clear();
            actionMap.values().forEach(this::addActions);
            return self();
        }

        public void removeActions(EventType eventType) {
            Objects.requireNonNull(eventType);
            getActionsModifiable(eventType).clear();
        }

        public void addActions(List<Action> actions) {
            if (actions.isEmpty()) return;
            EventType eventType = actions.get(0).getEventType();

            assertCanAdd(actions);

            List<Action> addActions = getActionsModifiable(eventType);
            addActions.addAll(actions);
        }

        private void assertCanAdd(List<Action> actions) {
            if (actions.isEmpty()) return;
            EventType eventType = actions.get(0).getEventType();

            // there's different eventType action
            if (actions.stream().anyMatch(action -> action.getEventType() != eventType))
                throw new IllegalArgumentException("The event type of actions is not constant. (expect=" + eventType + ")");

            List<Action> addActions = getActionsModifiable(eventType);
            if (eventType.isSingleOnly() && addActions.size() + actions.size() > 1) {
                throw new IllegalArgumentException("size of actions is too big!");
            }
        }

        public boolean addAction(Action action) {
            List<Action> actions = getActionsModifiable(action.getEventType());
            if (action.getEventType().isSingleOnly() && !actions.isEmpty()) {
                return false;
            }
            return actions.add(action);
        }

        public void combineTile(Tile tile) {
            combineTile(new Tile.Builder().from(tile));
        }

        /**
         * Combines 2 action map of tiles.
         * Add builder's all action to builder
         *
         * @param builder source builder to extract actions
         */
        public void combineTile(Tile.Builder builder) {

            builder.actionMap.forEach((eventType, value) -> {
                List<Action> destinationList = getActionsModifiable(eventType);
                destinationList.addAll(value);

                if (eventType.isSingleOnly()) {
                    if (eventType == EventType.TWIRL) {
                        boolean hasTwirl = (destinationList.size() % 2) == 1;
                        destinationList.clear();
                        if (hasTwirl) {
                            destinationList.add(new Twirl.Builder().build());
                        }
                    } else {
                        while (destinationList.size() > 1) {
                            destinationList.remove(0);
                        }
                    }
                }
            });
        }

        /**
         * edit specific event type action with given function
         *
         * @param eventType the eventType to edit
         * @param function individual edit function
         */
        @SuppressWarnings("unchecked")
        public <T extends Action> Builder editActions(EventType eventType, Function<? super T, ? extends T> function) {
            List<Action> actions = getActionsModifiable(eventType);
            List<Action> newActionList = actions.stream()
                    .map(a -> function.apply((T) a))
                    .collect(Collectors.toList());

            actions.clear();
            actions.addAll(newActionList);
            return self();
        }

        public List<Action> getActions(EventType eventType) {
            return unmodifiableList(getActionsModifiable(eventType));
        }

        private List<Action> getActionsModifiable(EventType eventType) {
            return actionMap.computeIfAbsent(eventType, k -> new ArrayList<>());
        }

        public Tile buildFirst(LevelSetting levelSetting, Double nextAngle) {
            TileMeta tileMeta = TileMeta.createFirstTileMeta(actionMap, levelSetting, nextAngle);
            fillActionMapWithEmptyList();
            return new Tile(angle, Collections.unmodifiableMap(actionMap), tileMeta);
        }

        public Tile build(TileMeta prevTileMeta, Double nextAngle) {
            TileMeta tileMeta = TileMeta.createTileMeta(actionMap, prevTileMeta, angle, nextAngle);
            fillActionMapWithEmptyList();
            return new Tile(angle, Collections.unmodifiableMap(actionMap), tileMeta);
        }

        public Tile buildLast(TileMeta prevTileMeta) {
            TileMeta tileMeta = TileMeta.createTileMeta(actionMap, prevTileMeta, angle, AngleHelper.isMidAngle(angle) ? prevTileMeta.getStaticAngle() : angle);
            fillActionMapWithEmptyList();
            return new Tile(angle, Collections.unmodifiableMap(actionMap), tileMeta);
        }

        private void fillActionMapWithEmptyList() {
            for (EventType eventType : EventType.values()) {
                actionMap.putIfAbsent(eventType, new ArrayList<>(0));
            }
        }

        public Double getAngle() {
            return this.angle;
        }

    }

}
