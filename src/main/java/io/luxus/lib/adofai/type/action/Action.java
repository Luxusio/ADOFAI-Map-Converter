package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Action {

    private final EventType eventType;
    private final Boolean active;

    @Getter
    @ToString
    public static abstract class Builder<T extends Builder<T>> {

        protected Boolean active = null;

        public abstract Action build();

        public abstract T self();

        /**
         * return eventType of Action Builder
         *
         * @return eventType
         */
        public abstract EventType getEventType();

        public T active(Boolean active) {
            this.active = active;
            return self();
        }

    }

}
