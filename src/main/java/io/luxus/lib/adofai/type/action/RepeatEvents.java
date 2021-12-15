package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class RepeatEvents extends Action {

	private final Long repetitions;
	private final Double interval;
	private final String tag;

	public RepeatEvents(Long repetitions, Double interval, String tag) {
		super(EventType.REPEAT_EVENTS);
		this.repetitions = repetitions;
		this.interval = interval;
		this.tag = tag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Long repetitions = 1L;
		private Double interval = 1.0;
		private String tag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(RepeatEvents src) {
			return self()
					.repetitions(src.repetitions)
					.interval(src.interval)
					.tag(src.tag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public RepeatEvents build() {
			return new RepeatEvents(repetitions, interval, tag);
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
		 * setter of repetitions
		 *
		 * @param repetitions repetitions of RepeatEvents Event
		 * @return self
		 * @throws NullPointerException when repetitions is null
		 */
		public Builder repetitions(Long repetitions) {
			Objects.requireNonNull(repetitions);
			this.repetitions = repetitions;
			return self();
		}

		/**
		 * setter of interval
		 *
		 * @param interval interval of RepeatEvents Event
		 * @return self
		 * @throws NullPointerException when interval is null
		 */
		public Builder interval(Double interval) {
			Objects.requireNonNull(interval);
			this.interval = interval;
			return self();
		}

		/**
		 * setter of tag
		 *
		 * @param tag tag of RepeatEvents Event
		 * @return self
		 * @throws NullPointerException when tag is null
		 */
		public Builder tag(String tag) {
			Objects.requireNonNull(tag);
			this.tag = tag;
			return self();
		}

	}

}
