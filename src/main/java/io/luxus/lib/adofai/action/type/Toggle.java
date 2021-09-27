package io.luxus.lib.adofai.action.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Toggle {
    ENABLED("Enabled"),
    DISABLED("Disabled"),
    ;

    Toggle(String jsonName) {
        this.jsonName = jsonName;
    }

    private final String jsonName;

}
