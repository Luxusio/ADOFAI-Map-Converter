package io.luxus.lib.adofai.decoration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Decoration {

    private final DecorationType eventType;
    private final Boolean visible;
    private final Long floor;

    @Getter
    @ToString
    public static abstract class Builder<T extends Builder<T>> {

        protected Boolean visible = null;
        protected Long floor = null;

        public abstract Decoration build();

        public abstract T self();

        /**
         * return eventType of Decoration Builder
         *
         * @return eventType
         */
        public abstract DecorationType getEventType();

        public T visible(Boolean visible) {
            this.visible = visible;
            return self();
        }

        public T floor(Long floor) {
            this.floor = floor;
            return self();
        }

    }

}
