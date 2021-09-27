package io.luxus.lib.adofai.action;

import com.fasterxml.jackson.databind.JsonNode;
import io.luxus.lib.adofai.action.type.EventType;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class UnknownAction extends Action {

    private final JsonNode rawData;

    public UnknownAction(JsonNode rawData) {
        super(EventType.UNKNOWN);

        this.rawData = rawData;
    }

}
