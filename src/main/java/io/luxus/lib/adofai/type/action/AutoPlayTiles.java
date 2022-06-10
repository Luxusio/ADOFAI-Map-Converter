package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class AutoPlayTiles extends Action {

    private final Toggle enabled;
    private final Toggle safetyTiles;

    private AutoPlayTiles(Boolean active, Toggle enabled, Toggle safetyTiles) {
        super(EventType.AUTO_PLAY_TILES, active);
        this.enabled = enabled;
        this.safetyTiles = safetyTiles;
    }

    @Getter
    @ToString
    public static final class Builder extends Action.Builder<Builder> {

        private Toggle enabled = Toggle.ENABLED;
        private Toggle safetyTiles = Toggle.DISABLED;

        public Builder from(AutoPlayTiles src) {
            return self()
                    .enabled(src.enabled)
                    .safetyTiles(src.safetyTiles);
        }

        @Override
        public AutoPlayTiles build() {
            return new AutoPlayTiles(active, enabled, safetyTiles);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public EventType getEventType() {
            return EventType.AUTO_PLAY_TILES;
        }

        public Builder enabled(Toggle enabled) {
            Objects.requireNonNull(enabled);
            this.enabled = enabled;
            return this;
        }

        public Builder safetyTiles(Toggle safetyTiles) {
            Objects.requireNonNull(safetyTiles);
            this.safetyTiles = safetyTiles;
            return this;
        }

    }

}




