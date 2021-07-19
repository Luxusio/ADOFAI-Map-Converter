package io.luxus.api.adofai.type;

public enum CameraRelativeTo {
	PLAYER("Player"),
	TILE("Tile"),
	GLOBAL("Global"),
	LAST_POSITION("LastPosition"),
	;
	
	private String name;
	
	private CameraRelativeTo(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
