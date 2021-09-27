package io.luxus.lib.adofai.action.type;

import lombok.Getter;

@Getter
public enum EventType {
    UNKNOWN(null, false),
    SET_SPEED("SetSpeed", true),
    TWIRL("Twirl", true),
    EDITOR_COMMENT("EditorComment", false),
    CHECK_POINT("Checkpoint", true),
    CUSTOM_BACKGROUND("CustomBackground", false), // angleOffset
    COLOR_TRACK("ColorTrack", true),
    ANIMATE_TRACK("AnimateTrack", true),
    ADD_DECORATION("AddDecoration", false),
    FLASH("Flash", false), // duration , angleOffset
    MOVE_CAMERA("MoveCamera", false), // complete
    SET_HITSOUND("SetHitsound", true),
    RECOLOR_TRACK("RecolorTrack", false), // angleoffset
    MOVE_TRACK("MoveTrack", false), // duration, angleOffset
    SET_FILTER("SetFilter", false), // angleOffset
    HALL_OF_MIRRORS("HallOfMirrors", false), // angleOffset
    SHAKE_SCREEN("ShakeScreen", false), // duration, angleOffset
    SET_PLANET_ROTATION("SetPlanetRotation", true),
    MOVE_DECORATIONS("MoveDecorations", false), // duration, angleOffset
    POSITION_TRACK("PositionTrack", true),
    REPEAT_EVENTS("RepeatEvents", true), // complete
    BLOOM("Bloom", false), // angleOffset
    SET_CONDITIONAL_EVENTS("SetConditionalEvents", true),
    CHANGE_TRACK("ChangeTrack", false), // maybe false
    SCREEN_TILE("ScreenTile", false),
    SCREEN_SCROLL("ScreenScroll", false),
    ADD_TEXT("AddText", false),
    SET_TEXT("SetText", false),
    ;

    EventType(String jsonName, boolean singleOnly) {
        this.jsonName = jsonName;
        this.singleOnly = singleOnly;
    }

    private final String jsonName;
    private final boolean singleOnly;

}
