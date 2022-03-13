package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.Ease;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TrackAnimation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class SetPlanetRotation extends Action {

	private final Ease ease;
	private final Long easeParts;

	private SetPlanetRotation(Boolean active, Ease ease, Long easeParts) {
		super(EventType.SET_PLANET_ROTATION, active);
		this.ease = ease;
		this.easeParts = easeParts;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Ease ease = Ease.LINEAR;
		private Long easeParts = 1L;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(SetPlanetRotation src) {
			return self()
					.ease(src.ease)
					.easeParts(src.easeParts);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public SetPlanetRotation build() {
			return new SetPlanetRotation(active, ease, easeParts);
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
			return EventType.SET_PLANET_ROTATION;
		}

		/**
		 * setter of ease
		 *
		 * @param ease ease of SetPlanetRotation Event
		 * @return self
		 * @throws NullPointerException when ease is null
		 */
		public Builder ease(Ease ease) {
			Objects.requireNonNull(ease);
			this.ease = ease;
			return self();
		}

		/**
		 * setter of trackAnimation
		 *
		 * @param easeParts easeParts of SetPlanetRotation Event
		 * @return self
		 * @throws NullPointerException when easeParts is null
		 */
		public Builder easeParts(Long easeParts) {
			Objects.requireNonNull(easeParts);
			this.easeParts = easeParts;
			return self();
		}

	}

}
