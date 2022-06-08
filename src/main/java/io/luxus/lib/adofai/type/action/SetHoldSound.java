package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class SetHoldSound extends Action {

    private final HoldSoundType holdStartSound;
    private final HoldSoundType holdLoopSound;
    private final HoldSoundType holdEndSound;
    private final HoldMidSound holdMidSound;
    private final HoldMidSoundType holdMidSoundType;
    private final Double holdMidSoundDelay;
    private final HoldMidSoundTimingRelativeTo holdMidSoundTimingRelativeTo;
    private final Long holdSoundVolume;


    private SetHoldSound(Boolean active, HoldSoundType holdStartSound, HoldSoundType holdLoopSound, HoldSoundType holdEndSound, HoldMidSound holdMidSound, HoldMidSoundType holdMidSoundType, Double holdMidSoundDelay, HoldMidSoundTimingRelativeTo holdMidSoundTimingRelativeTo, Long holdSoundVolume) {
        super(EventType.SET_HOLD_SOUND, active);
        this.holdStartSound = holdStartSound;
        this.holdLoopSound = holdLoopSound;
        this.holdEndSound = holdEndSound;
        this.holdMidSound = holdMidSound;
        this.holdMidSoundType = holdMidSoundType;
        this.holdMidSoundDelay = holdMidSoundDelay;
        this.holdMidSoundTimingRelativeTo = holdMidSoundTimingRelativeTo;
        this.holdSoundVolume = holdSoundVolume;
    }

    @Getter
    @ToString
    public static final class Builder extends Action.Builder<Builder> {

        private HoldSoundType holdStartSound = HoldSoundType.FUSE;
        private HoldSoundType holdLoopSound = HoldSoundType.FUSE;
        private HoldSoundType holdEndSound = HoldSoundType.FUSE;
        private HoldMidSound holdMidSound = HoldMidSound.FUSE;
        private HoldMidSoundType holdMidSoundType = HoldMidSoundType.ONCE;
        private Double holdMidSoundDelay = 0.5;
        private HoldMidSoundTimingRelativeTo holdMidSoundTimingRelativeTo = HoldMidSoundTimingRelativeTo.END;
        private Long holdSoundVolume = 100L;


        public Builder from(SetHoldSound src) {
            return self()
                    .holdStartSound(src.holdStartSound)
                    .holdLoopSound(src.holdLoopSound)
                    .holdEndSound(src.holdEndSound)
                    .holdMidSound(src.holdMidSound)
                    .holdMidSoundType(src.holdMidSoundType)
                    .holdMidSoundDelay(src.holdMidSoundDelay)
                    .holdMidSoundTimingRelativeTo(src.holdMidSoundTimingRelativeTo)
                    .holdSoundVolume(src.holdSoundVolume);
        }

        @Override
        public SetHoldSound build() {
            return new SetHoldSound(active, holdStartSound, holdLoopSound, holdEndSound, holdMidSound, holdMidSoundType, holdMidSoundDelay, holdMidSoundTimingRelativeTo, holdSoundVolume);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public EventType getEventType() {
            return EventType.SET_HOLD_SOUND;
        }


        public Builder holdStartSound(HoldSoundType holdStartSound) {
            Objects.requireNonNull(holdStartSound);
            this.holdStartSound = holdStartSound;
            return this;
        }

        public Builder holdLoopSound(HoldSoundType holdLoopSound) {
            Objects.requireNonNull(holdLoopSound);
            this.holdLoopSound = holdLoopSound;
            return this;
        }

        public Builder holdEndSound(HoldSoundType holdEndSound) {
            Objects.requireNonNull(holdEndSound);
            this.holdEndSound = holdEndSound;
            return this;
        }

        public Builder holdMidSound(HoldMidSound holdMidSound) {
            Objects.requireNonNull(holdMidSound);
            this.holdMidSound = holdMidSound;
            return this;
        }

        public Builder holdMidSoundType(HoldMidSoundType holdMidSoundType) {
            Objects.requireNonNull(holdMidSoundType);
            this.holdMidSoundType = holdMidSoundType;
            return this;
        }

        public Builder holdMidSoundDelay(Double holdMidSoundDelay) {
            Objects.requireNonNull(holdMidSoundDelay);
            this.holdMidSoundDelay = holdMidSoundDelay;
            return this;
        }

        public Builder holdMidSoundTimingRelativeTo(HoldMidSoundTimingRelativeTo holdMidSoundTimingRelativeTo) {
            Objects.requireNonNull(holdMidSoundTimingRelativeTo);
            this.holdMidSoundTimingRelativeTo = holdMidSoundTimingRelativeTo;
            return this;
        }

        public Builder holdSoundVolume(Long holdSoundVolume) {
            Objects.requireNonNull(holdSoundVolume);
            this.holdSoundVolume = holdSoundVolume;
            return this;
        }

    }

}
