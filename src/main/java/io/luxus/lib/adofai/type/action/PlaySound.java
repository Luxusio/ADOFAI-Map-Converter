package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.HitSound;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class PlaySound extends Action {

	private final HitSound hitsound;
	private final Long hitsoundVolume;
	private final Double angleOffset;
	private final String eventTag;

	private PlaySound(Boolean active, HitSound hitsound, Long hitsoundVolume, Double angleOffset, String eventTag) {
		super(EventType.PLAY_SOUND, active);
		this.hitsound = hitsound;
		this.hitsoundVolume = hitsoundVolume;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private HitSound hitsound = HitSound.KICK;
		private Long hitsoundVolume = 100L;
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(PlaySound src) {
			return self()
					.hitsound(src.hitsound)
					.hitsoundVolume(src.hitsoundVolume)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public PlaySound build() {
			return new PlaySound(active, hitsound, hitsoundVolume, angleOffset, eventTag);
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
			return EventType.PLAY_SOUND;
		}

		/**
		 * setter of hitsound
		 *
		 * @param hitsound hitsound of PlaySound Event
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
		 * @param hitsoundVolume hitsoundVolume of PlaySound Event
		 * @return self
		 * @throws NullPointerException when hitsoundVolume is null
		 */
		public Builder hitsoundVolume(Long hitsoundVolume) {
			Objects.requireNonNull(hitsoundVolume);
			this.hitsoundVolume = hitsoundVolume;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of PlaySound Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return self();
		}

		public Builder eventTag(String eventTag) {
			Objects.requireNonNull(eventTag);
			this.eventTag = eventTag;
			return self();
		}

	}

}
