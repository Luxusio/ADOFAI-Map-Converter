package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.SpeedType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SetSpeed extends Action {

	private final SpeedType speedType;
	private final Double beatsPerMinute;
	private final Double bpmMultiplier;
	
	private SetSpeed(SpeedType speedType, Double beatsPerMinute, Double bpmMultiplier) {
		super(EventType.SET_SPEED);
		this.speedType = speedType;
		this.beatsPerMinute = beatsPerMinute;
		this.bpmMultiplier = bpmMultiplier;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private SpeedType speedType = SpeedType.BPM;
		private Double beatsPerMinute = 100.0;
		private Double bpmMultiplier = 1.0;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(SetSpeed src) {
			return self()
					.speedType(src.speedType)
					.beatsPerMinute(src.beatsPerMinute)
					.bpmMultiplier(src.bpmMultiplier);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public SetSpeed build() {
			return new SetSpeed(speedType, beatsPerMinute, bpmMultiplier);
		}

		/**
		 * return self
		 *
		 * @return self
		 */
		@Override
		public Builder self() {
			return this;
		}

		/**
		 * return eventType of Action Builder
		 *
		 * @return eventType
		 */
		@Override
		public EventType getEventType() {
			return EventType.SET_SPEED;
		}

		/**
		 * setter of speedType
		 *
		 * @param speedType speedType of SetSpeed Event
		 * @return self
		 * @throws NullPointerException when speedType is null
		 */
		public Builder speedType(SpeedType speedType) {
			this.speedType = speedType;
			return self();
		}

		/**
		 * setter of beatsPerMinute
		 *
		 * @param beatsPerMinute beatsPerMinute of SetSpeed Event
		 * @return self
		 * @throws NullPointerException when beatsPerMinute is null
		 */
		public Builder beatsPerMinute(Double beatsPerMinute) {
			this.beatsPerMinute = beatsPerMinute;
			return self();
		}

		/**
		 * setter of bpmMultiplier
		 *
		 * @param bpmMultiplier bpmMultiplier of SetSpeed Event
		 * @return self
		 * @throws NullPointerException when bpmMultiplier is null
		 */
		public Builder bpmMultiplier(Double bpmMultiplier) {
			this.bpmMultiplier = bpmMultiplier;
			return self();
		}

	}

}
