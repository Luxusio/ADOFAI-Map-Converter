package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType implements JsonParsable {
    UNKNOWN(null, false),
    SET_SPEED("SetSpeed", true),
    TWIRL("Twirl", true),
    EDITOR_COMMENT("EditorComment", false),
    BOOKMARK("Bookmark", true),
    CHECK_POINT("Checkpoint", true),
    CUSTOM_BACKGROUND("CustomBackground", false), // angleOffset
    COLOR_TRACK("ColorTrack", true),
    ANIMATE_TRACK("AnimateTrack", true),
    ADD_DECORATION("AddDecoration", false),
    FLASH("Flash", false), // duration , angleOffset
    MOVE_CAMERA("MoveCamera", false), // complete
    SET_HITSOUND("SetHitsound", true),
    PLAY_SOUND("PlaySound", false),
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
    PAUSE("Pause", true),
    AUTO_PLAY_TILES("AutoPlayTiles", true),
    HOLD("Hold", true),
    SET_HOLD_SOUND("SetHoldSound", true),
    MULTI_PLANET("MultiPlanet", true),
    FREE_ROAM("FreeRoam", true),
    FREE_ROAM_TWIRL("FreeRoamTwirl", false),
    FREE_ROAM_REMOVE("FreeRoamRemove", false),
    HIDE("Hide", true),
    SCALE_MARGIN("ScaleMargin", true),
    SCALE_RADIUS("ScaleRadius", true),

    ;

    private final String jsonName;
    private final boolean singleOnly;

}
