package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TrackAnimation;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class SetText extends Action {
	
	private final String decText;
	private final String tag;
	private final Double angleOffset;
	private final String eventTag;

	private SetText(String decText, String tag, Double angleOffset, String eventTag) {
		super(EventType.SET_TEXT);
		this.decText = decText;
		this.tag = tag;
		this.angleOffset = angleOffset;
		this.eventTag = eventTag;
	}


	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private String decText = "text";
		private String tag = "";
		private Double angleOffset = 0.0;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(SetText src) {
			return self()
					.decText(src.decText)
					.tag(src.tag)
					.angleOffset(src.angleOffset)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public SetText build() {
			return new SetText(decText, tag, angleOffset, eventTag);
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
		 * setter of decText
		 *
		 * @param decText decText of SetText Event
		 * @return self
		 * @throws NullPointerException when decText is null
		 */
		public Builder decText(String decText) {
			Objects.requireNonNull(decText);
			this.decText = decText;
			return this;
		}

		/**
		 * setter of tag
		 *
		 * @param tag tag of SetText Event
		 * @return self
		 * @throws NullPointerException when tag is null
		 */
		public Builder tag(String tag) {
			Objects.requireNonNull(tag);
			this.tag = tag;
			return this;
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of SetText Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return this;
		}

		/**
		 * setter of eventTag
		 *
		 * @param eventTag eventTag of SetText Event
		 * @return self
		 * @throws NullPointerException when eventTag is null
		 */
		public Builder eventTag(String eventTag) {
			Objects.requireNonNull(eventTag);
			this.eventTag = eventTag;
			return this;
		}
	}

}
