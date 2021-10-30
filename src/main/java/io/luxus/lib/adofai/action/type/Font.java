package io.luxus.lib.adofai.action.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Font {
    DEFAULT("Default"),
    ARIAL("Arial"),
    COMIC_SANS_MS("ComicSansMS"),
    COURIER_NEW("CourierNew"),
    GEORGIA("Georgia"),
    IMPACT("Impact"),
    TIMES_NEW_ROMAN("TimesNewRoman"),
    ;

    private final String jsonName;

}
