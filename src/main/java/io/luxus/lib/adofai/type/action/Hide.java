package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.Toggle;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;


@Getter
@ToString
public class Hide extends Action {

	private final Toggle hideJudgment;
	private final Toggle hideTileIcon;

	private Hide(Boolean active, Toggle hideJudgment, Toggle hideTileIcon) {
		super(EventType.HIDE, active);
		this.hideJudgment = hideJudgment;
		this.hideTileIcon = hideTileIcon;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Toggle hideJudgment = Toggle.DISABLED;
		private Toggle hideTileIcon = Toggle.DISABLED;


		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(Hide src) {
			return self()
					.hideJudgment(src.hideJudgment)
					.hideTileIcon(src.hideTileIcon);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public Hide build() {
			return new Hide(active, hideJudgment, hideTileIcon);
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
			return EventType.HIDE;
		}


		public Builder hideJudgment(Toggle hideJudgment) {
			Objects.requireNonNull(hideJudgment);
			this.hideJudgment = hideJudgment;
			return this;
		}

		public Builder hideTileIcon(Toggle hideTileIcon) {
			Objects.requireNonNull(hideTileIcon);
			this.hideTileIcon = hideTileIcon;
			return this;
		}

	}

}
