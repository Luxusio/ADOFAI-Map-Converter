package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class EditorComment extends Action {

    private final String comment;

    private EditorComment(String comment) {
        super(EventType.EDITOR_COMMENT);
        this.comment = comment;
    }

    @Getter
    @ToString
    public static final class Builder extends Action.Builder<Builder> {

        private String comment = "";

        /**
         * set all parameter with given action
         *
         * @param src source action
         * @return self
         */
        public Builder from(EditorComment src) {
            return self()
                    .comment(src.comment);
        }

        /**
         * build AddText action with builder
         *
         * @return Built AddText action
         */
        @Override
        public EditorComment build() {
            return new EditorComment(comment);
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
            return EventType.EDITOR_COMMENT;
        }

        /**
         * setter of comment
         *
         * @param comment comment of EditorComment Event
         * @return self
         * @throws NullPointerException when comment is null
         */
        public Builder comment(String comment) {
            Objects.requireNonNull(comment);
            this.comment = comment;
            return self();
        }
    }

}
