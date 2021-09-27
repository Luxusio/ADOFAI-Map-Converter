package io.luxus.lib.adofai.action.type;

import lombok.Getter;

@Getter
public enum Font {
    DEFAULT("Default"),
    ARIAL("Arial"),
    COMIC_SANS_MS("ComicSansMS"),
    COURIER_NEW("CourierNew"),
    GEORGIA("Georgia"),
    IMPACT("Impact"),
    TIMES_NEW_ROMAN("TimesNewRoman"),
    ;

    Font(String jsonName) {
        this.jsonName = jsonName;
    }

    private final String jsonName;

}
