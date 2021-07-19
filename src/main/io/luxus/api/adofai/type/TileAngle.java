package io.luxus.api.adofai.type;

public enum TileAngle {
	_0('R', 0, false),
	_15('p', 15, false),
	_30('J', 30, false),
	_45('E', 45, false),
	_60('T', 60, false),
	_75('o', 75, false),
	_90('U', 90, false),
	_105('q', 105, false),
	_120('G', 120, false),
	_135('Q', 135, false),
	_150('H', 150, false),
	_165('W', 165, false),
	_180('L', 180, false),
	_195('x', 195, false),
	_210('N', 210, false),
	_225('Z', 225, false),
	_240('F', 240, false),
	_255('V', 255, false),
	_270('D', 270, false),
	_285('Y', 285, false),
	_300('B', 300, false),
	_315('C', 315, false),
	_330('M', 330, false),
	_345('A', 345, false),
	_5('5', 108, true),
	_6('6', 252, true),
	_7('7', 900.0 / 7.0, true),
	_8('8', 360 - 900.0 / 7.0, true),
	NONE('!', 0, true),
	;
	
	private char name;
	private double angle;
	private boolean dynamic;
	
	private TileAngle(char name, double angle, boolean dynamic) {
		this.name = name;
		this.angle = angle;
		this.dynamic = dynamic;
	}
	
	public char getName() {
		return name;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public boolean isDynamic() {
		return dynamic;
	}
	
}
