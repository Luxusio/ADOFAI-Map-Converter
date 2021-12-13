package io.luxus.lib.adofai.type;

import lombok.Getter;

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
