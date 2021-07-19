package io.luxus.api.adofai.type;

public enum SpeedType {
	BPM("Bpm"),
	MULTIPLIER("Multiplier"),
	;
	
	private String name;
	
	private SpeedType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
