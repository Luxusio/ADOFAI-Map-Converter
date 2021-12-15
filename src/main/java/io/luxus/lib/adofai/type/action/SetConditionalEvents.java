package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class SetConditionalEvents extends Action {

	private final String perfectTag;
	private final String hitTag;
	private final String barelyTag;
	private final String missTag;
	private final String lossTag;

	private SetConditionalEvents(String perfectTag, String hitTag, String barelyTag, String missTag,
			String lossTag) {
		super(EventType.SET_CONDITIONAL_EVENTS);
		this.perfectTag = perfectTag;
		this.hitTag = hitTag;
		this.barelyTag = barelyTag;
		this.missTag = missTag;
		this.lossTag = lossTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private String perfectTag = "NONE";
		private String hitTag = "NONE";
		private String barelyTag = "NONE";
		private String missTag = "NONE";
		private String lossTag = "NONE";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(SetConditionalEvents src) {
			return self()
					.perfectTag(src.perfectTag)
					.hitTag(src.hitTag)
					.barelyTag(src.barelyTag)
					.missTag(src.missTag)
					.lossTag(src.lossTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public SetConditionalEvents build() {
			return new SetConditionalEvents(perfectTag, hitTag, barelyTag, missTag, lossTag);
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
		 * setter of perfectTag
		 *
		 * @param perfectTag perfectTag of SetConditionalEvents Event
		 * @return self
		 * @throws NullPointerException when perfectTag is null
		 */
		public Builder perfectTag(String perfectTag) {
			Objects.requireNonNull(perfectTag);
			this.perfectTag = perfectTag;
			return self();
		}

		/**
		 * setter of hitTag
		 *
		 * @param hitTag hitTag of SetConditionalEvents Event
		 * @return self
		 * @throws NullPointerException when hitTag is null
		 */
		public Builder hitTag(String hitTag) {
			Objects.requireNonNull(hitTag);
			this.hitTag = hitTag;
			return self();
		}

		/**
		 * setter of barelyTag
		 *
		 * @param barelyTag barelyTag of SetConditionalEvents Event
		 * @return self
		 * @throws NullPointerException when barelyTag is null
		 */
		public Builder barelyTag(String barelyTag) {
			Objects.requireNonNull(barelyTag);
			this.barelyTag = barelyTag;
			return self();
		}

		/**
		 * setter of missTag
		 *
		 * @param missTag missTag of SetConditionalEvents Event
		 * @return self
		 * @throws NullPointerException when missTag is null
		 */
		public Builder missTag(String missTag) {
			Objects.requireNonNull(missTag);
			this.missTag = missTag;
			return self();
		}

		/**
		 * setter of lossTag
		 *
		 * @param lossTag lossTag of SetConditionalEvents Event
		 * @return self
		 * @throws NullPointerException when lossTag is null
		 */
		public Builder lossTag(String lossTag) {
			Objects.requireNonNull(lossTag);
			this.lossTag = lossTag;
			return self();
		}

	}

}
