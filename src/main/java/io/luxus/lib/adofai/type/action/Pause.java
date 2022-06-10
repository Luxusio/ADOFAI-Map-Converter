package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class Pause extends Action {

    private final Double duration;
    private final Long countdownTicks;
    private final Long angleCorrectionDir;

    private Pause(Boolean active, Double duration, Long countdownTicks, Long angleCorrectionDir) {
        super(EventType.PAUSE, active);
        this.duration = duration;
        this.countdownTicks = countdownTicks;
        this.angleCorrectionDir = angleCorrectionDir;
    }

    @Getter
    @ToString
    public static final class Builder extends Action.Builder<Builder> {

        private Double duration = 1.0;
        private Long countdownTicks = 0L;
        private Long angleCorrectionDir = -1L;

        public Builder from(Pause src) {
            return self()
                    .duration(src.duration)
                    .countdownTicks(src.countdownTicks)
                    .angleCorrectionDir(src.angleCorrectionDir);
        }

        @Override
        public Pause build() {
            return new Pause(active, duration, countdownTicks, angleCorrectionDir);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public EventType getEventType() {
            return EventType.PAUSE;
        }

        public Builder duration(Double duration) {
            Objects.requireNonNull(duration);
            this.duration = duration;
            return this;
        }

        public Builder countdownTicks(Long countdownTicks) {
            Objects.requireNonNull(countdownTicks);
            this.countdownTicks = countdownTicks;
            return this;
        }

        public Builder angleCorrectionDir(Long angleCorrectionDir) {
            Objects.requireNonNull(angleCorrectionDir);
            this.angleCorrectionDir = angleCorrectionDir;
            return this;
        }

    }

}




