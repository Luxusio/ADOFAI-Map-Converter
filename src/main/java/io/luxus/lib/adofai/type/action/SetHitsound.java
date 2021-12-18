package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class SetHitsound extends Action {

	private final GameSound gameSound;
	private final HitSound hitsound;
	private final Long hitsoundVolume;

	private SetHitsound(GameSound gameSound, HitSound hitsound, Long hitsoundVolume) {
		super(EventType.SET_HITSOUND);
		this.gameSound = gameSound;
		this.hitsound = hitsound;
		this.hitsoundVolume = hitsoundVolume;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private GameSound gameSound = GameSound.HITSOUND;
		private HitSound hitsound = HitSound.KICK;
		private Long hitsoundVolume = 100L;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(SetHitsound src) {
			return self()
					.gameSound(src.gameSound)
					.hitsound(src.hitsound)
					.hitsoundVolume(src.hitsoundVolume);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public SetHitsound build() {
			return new SetHitsound(gameSound, hitsound, hitsoundVolume);
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
		 * setter of gameSound
		 *
		 * @param gameSound gameSound of SetHitsound Event
		 * @return self
		 * @throws NullPointerException when gameSound is null
		 */
		public Builder gameSound(GameSound gameSound) {
			Objects.requireNonNull(gameSound);
			this.gameSound = gameSound;
			return self();
		}

		/**
		 * setter of hitsound
		 *
		 * @param hitsound hitsound of SetHitsound Event
		 * @return self
		 * @throws NullPointerException when hitsound is null
		 */
		public Builder hitsound(HitSound hitsound) {
			Objects.requireNonNull(hitsound);
			this.hitsound = hitsound;
			return self();
		}

		/**
		 * setter of hitsoundVolume
		 *
		 * @param hitsoundVolume hitsoundVolume of SetHitsound Event
		 * @return self
		 * @throws NullPointerException when hitsoundVolume is null
		 */
		public Builder hitsoundVolume(Long hitsoundVolume) {
			Objects.requireNonNull(hitsoundVolume);
			this.hitsoundVolume = hitsoundVolume;
			return self();
		}

	}

}
