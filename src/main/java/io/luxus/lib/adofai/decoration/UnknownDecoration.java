package io.luxus.lib.adofai.decoration;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class UnknownDecoration extends Decoration {

    private final JsonNode rawData;

    public UnknownDecoration(JsonNode rawData) {
        super(DecorationType.UNKNOWN,
                rawData.get("visible").asBoolean(),
                rawData.get("floor").asLong());
        this.rawData = rawData;
    }

}
