package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class Hold extends Action {

    private final Long duration;            // additional hold rotation amount
    private final Long distanceMultiplier;
    private final Toggle landingAnimation;

    private Hold(Boolean active, Long duration, Long distanceMultiplier, Toggle landingAnimation) {
        super(EventType.HOLD, active);
        this.duration = duration;
        this.distanceMultiplier = distanceMultiplier;
        this.landingAnimation = landingAnimation;
    }

    @Getter
    @ToString
    public static final class Builder extends Action.Builder<Builder> {

        private Long duration = 0L;
        private Long distanceMultiplier = 100L;
        private Toggle landingAnimation = Toggle.DISABLED;


        public Builder from(Hold src) {
            return self()
                    .duration(src.duration)
                    .distanceMultiplier(src.distanceMultiplier)
                    .landingAnimation(src.landingAnimation);
        }

        @Override
        public Hold build() {
            return new Hold(active, duration, distanceMultiplier, landingAnimation);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public EventType getEventType() {
            return EventType.HOLD;
        }

        public Builder duration(Long duration) {
            Objects.requireNonNull(duration);
            this.duration = duration;
            return this;
        }

        public Builder distanceMultiplier(Long distanceMultiplier) {
            Objects.requireNonNull(distanceMultiplier);
            this.distanceMultiplier = distanceMultiplier;
            return this;
        }

        public Builder landingAnimation(Toggle landingAnimation) {
            Objects.requireNonNull(landingAnimation);
            this.landingAnimation = landingAnimation;
            return this;
        }

    }

}




