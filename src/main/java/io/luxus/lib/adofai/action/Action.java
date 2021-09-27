package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;

@Getter
public abstract class Action {

    private final EventType eventType;

    protected Action(EventType eventType) {
        this.eventType = eventType;
    }

}
