package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TrackAnimation;
import io.luxus.lib.adofai.type.TrackDisappearAnimation;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class Bookmark extends Action {

	private Bookmark(Boolean active) {
		super(EventType.BOOKMARK, active);
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private TrackAnimation trackAnimation = TrackAnimation.NONE;
		private Double beatsAhead = 3.0;
		private TrackDisappearAnimation trackDisappearAnimation = TrackDisappearAnimation.NONE;
		private Double beatsBehind = 4.0;

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(Bookmark src) {
			return self();
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public Bookmark build() {
			return new Bookmark(active);
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
			return EventType.BOOKMARK;
		}

	}

}
