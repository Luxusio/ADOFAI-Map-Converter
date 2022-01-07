package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class Action {

    private final EventType eventType;

    protected Action(EventType eventType) {
        this.eventType = eventType;
    }

    @Getter
    @ToString
    public static abstract class Builder<T extends Builder<T>> {

        public abstract Action build();

        public abstract T self();

        /**
         * return eventType of Action Builder
         *
         * @return eventType
         */
        public abstract EventType getEventType();

    }

}
