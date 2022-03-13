package io.luxus.lib.adofai.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DecorationRelativeTo implements JsonParsable {
    TILE("Tile"),
    GLOBAL("Global"),
    RED_PLANET("RedPlanet"),
    BLUE_PLANET("BluePlanet"),
    CAMERA("Camera"),
    CAMERA_ASPECT("CameraAspect"),
    ;

    private final String jsonName;

}
