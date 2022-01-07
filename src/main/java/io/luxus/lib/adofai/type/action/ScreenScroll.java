package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.util.ListUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class ScreenScroll extends Action {

	private final List<Double> scroll;
	private final Double angleOffset;
	private final String eventTag;

	public ScreenScroll(List<Double> scroll, Double angleOffset, String eventTag) {
		super(EventType.SCREEN_SCROLL);
		this.scroll = scroll;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private List<Double> scroll = Arrays.asList(0.0, 0.0);
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
			return new ScreenScroll(scroll, angleOffset, eventTag);
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
		public Builder scroll(List<Double> scroll) {
			Objects.requireNonNull(scroll);
			if (scroll.size() != 2) {
				throw new IllegalArgumentException("size of scroll must be 2");
			}
			this.scroll = ListUtil.createNewUnmodifiableList(scroll);
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
