package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.Objects;

@Getter
@ToString
public class ScreenScroll extends Action {

	private final Pair<Double, Double> scroll;
	private final Double angleOffset;
	private final String eventTag;

	public ScreenScroll(Boolean active, Pair<Double, Double> scroll, Double angleOffset, String eventTag) {
		super(EventType.SCREEN_SCROLL, active);
		this.scroll = scroll;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Pair<Double, Double> scroll = Pair.with(0.0, 0.0);
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(ScreenScroll src) {
			return self()
					.scroll(src.scroll)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public ScreenScroll build() {
			return new ScreenScroll(active, scroll, angleOffset, eventTag);
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
			return EventType.SCREEN_SCROLL;
		}

		/**
		 * setter of scroll
		 *
		 * @param scroll scroll of ScreenScroll Event
		 * @return self
		 * @throws NullPointerException when scroll is null
		 * @throws IllegalArgumentException when size of scroll is not 2
		 */
		public Builder scroll(Pair<Double, Double> scroll) {
			Objects.requireNonNull(scroll);
			this.scroll = scroll;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of ScreenScroll Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return self();
		}

		/**
		 * setter of eventTag
		 *
		 * @param eventTag eventTag of ScreenScroll Event
		 * @return self
		 * @throws NullPointerException when eventTag is null
		 */
		public Builder eventTag(String eventTag) {
			Objects.requireNonNull(eventTag);
			this.eventTag = eventTag;
			return self();
		}
	}

}
