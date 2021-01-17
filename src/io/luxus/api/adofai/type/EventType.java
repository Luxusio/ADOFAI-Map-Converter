package io.luxus.api.adofai.type;

public enum EventType {
	SET_SPEED("SetSpeed", true),
	TWIRL("Twirl", true),
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
	;
	
	private String type;
	private boolean singleOnly;
	
	private EventType(String type, boolean singleOnly) {
		this.type = type;
		this.singleOnly = singleOnly;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
	public boolean isSingleOnly() {
		return singleOnly;
	}
	
}
