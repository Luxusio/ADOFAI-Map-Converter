package io.luxus.lib.adofai.action;

import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class EditorComment extends Action {

    private String comment = "";

    public EditorComment() {
        super(EventType.EDITOR_COMMENT);
    }

    public EditorComment(String comment) {
        this();
        this.comment = comment;
    }

}
