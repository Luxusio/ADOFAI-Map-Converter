package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;

@Getter
public abstract class Action {

    private final EventType eventType;

    protected Action(EventType eventType) {
        this.eventType = eventType;
    }

    public static abstract class Builder<T extends Builder<T>> {

        public abstract Action build();
        public abstract T self();

    }

}
