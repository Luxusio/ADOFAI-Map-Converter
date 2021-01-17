package io.luxus.api.adofai.type;

public enum TileAngle {
	_0('R', 0),
	_15('p', 15),
	_30('J', 30),
	_45('E', 45),
	_60('T', 60),
	_75('o', 75),
	_90('U', 90),
	_105('q', 105),
	_120('G', 120),
	_135('Q', 135),
	_150('H', 150),
	_165('W', 165),
	_180('L', 180),
	_195('x', 195),
	_210('N', 210),
	_225('Z', 225),
	_240('F', 240),
	_255('V', 255),
	_270('D', 270),
	_285('Y', 285),
	_300('B', 300),
	_315('C', 315),
	_330('M', 330),
	_345('A', 345),
	_5('5', 108),
	_6('6', 252),
	_7('7', 900.0 / 7.0),
	_8('8', 360 - 900.0 / 7.0),
	NONE('!', -1),
	;
	
	private char name;
	private double angle;
	
	private TileAngle(char name, double angle) {
		this.name = name;
		this.angle = angle;
	}
	
	public char getName() {
		return name;
	}
	
	public double getAngle() {
		return angle;
	}
	
}
