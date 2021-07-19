package io.luxus.api.adofai.converter;

import io.luxus.api.adofai.type.TileAngle;

public class AngleConverter {
	
	public static class Result {
		
		private double staticAngle;
		private double relativeAngle;
		
		public Result(double staticAngle, double relativeAngle) {
			super();
			this.staticAngle = staticAngle;
			this.relativeAngle = relativeAngle;
		}
		
		public double getStaticAngle() {
			return staticAngle;
		}
		
		public double getRelativeAngle() {
			return relativeAngle;
		}
		
	}
	
	public static AngleConverter.Result convert(double nowStaticAngle, TileAngle now, TileAngle next, boolean reverse, boolean notNone) {
		
		double staticAngle;
		double relativeAngle;
		
		if(next.isDynamic()) {
			staticAngle = nowStaticAngle + next.getAngle();
			relativeAngle = next.getAngle();

			if(staticAngle > 360) {
				staticAngle -= 360;
			}
		}
		else {
			staticAngle = next.getAngle();
			
			
			if(reverse) {
				relativeAngle = -nowStaticAngle + next.getAngle();
			}
			else {
				relativeAngle = -next.getAngle() + nowStaticAngle;
			}
			
			if(notNone) {
				relativeAngle += 180;
			}
			
			if(relativeAngle <= 0) {
				relativeAngle += 360;
			}
			else if(relativeAngle > 360) {
				relativeAngle -= 360;
			}
		}
		
		return new AngleConverter.Result(staticAngle, relativeAngle);
	}
	
	public static TileAngle getNextAngle(double staticAngle, double relativeAngle, boolean reverse) {
		
		if(reverse && relativeAngle != 0.0) {
			relativeAngle = 360 - relativeAngle;
			if(relativeAngle <= 0) {
				relativeAngle += 360;
			} else if(relativeAngle > 360) {
				relativeAngle -= 360;
			}
		}
		
		if(Double.compare(relativeAngle, 0.0) == 0) {
			return TileAngle.NONE;
		} else if(Double.compare(relativeAngle, 108.0) == 0) {
			return TileAngle._5;
		} else if(Double.compare(relativeAngle, 252.0) == 0) {
			return TileAngle._6;
		} else if(Double.compare(relativeAngle, 900.0 / 7.0) == 0) {
			return TileAngle._7;
		} else if(Double.compare(relativeAngle, 360 - 900.0 / 7.0) == 0) {
			return TileAngle._8;
		} else if(Double.compare(staticAngle, 0.0) == 0) {
			return TileAngle._0;
		} else if(Double.compare(staticAngle, 15.0) == 0) {
			return TileAngle._15;
		} else if(Double.compare(staticAngle, 30.0) == 0) {
			return TileAngle._30;
		} else if(Double.compare(staticAngle, 45.0) == 0) {
			return TileAngle._45;
		} else if(Double.compare(staticAngle, 60.0) == 0) {
			return TileAngle._60;
		} else if(Double.compare(staticAngle, 75.0) == 0) {
			return TileAngle._75;
		} else if(Double.compare(staticAngle, 90.0) == 0) {
			return TileAngle._90;
		} else if(Double.compare(staticAngle, 105.0) == 0) {
			return TileAngle._105;
		} else if(Double.compare(staticAngle, 120.0) == 0) {
			return TileAngle._120;
		} else if(Double.compare(staticAngle, 135.0) == 0) {
			return TileAngle._135;
		} else if(Double.compare(staticAngle, 150.0) == 0) {
			return TileAngle._150;
		} else if(Double.compare(staticAngle, 165.0) == 0) {
			return TileAngle._165;
		} else if(Double.compare(staticAngle, 180.0) == 0) {
			return TileAngle._180;
		} else if(Double.compare(staticAngle, 195.0) == 0) {
			return TileAngle._195;
		} else if(Double.compare(staticAngle, 210.0) == 0) {
			return TileAngle._210;
		} else if(Double.compare(staticAngle, 225.0) == 0) {
			return TileAngle._225;
		} else if(Double.compare(staticAngle, 240.0) == 0) {
			return TileAngle._240;
		} else if(Double.compare(staticAngle, 255.0) == 0) {
			return TileAngle._255;
		} else if(Double.compare(staticAngle, 270.0) == 0) {
			return TileAngle._270;
		} else if(Double.compare(staticAngle, 285.0) == 0) {
			return TileAngle._285;
		} else if(Double.compare(staticAngle, 300.0) == 0) {
			return TileAngle._300;
		} else if(Double.compare(staticAngle, 315.0) == 0) {
			return TileAngle._315;
		} else if(Double.compare(staticAngle, 330.0) == 0) {
			return TileAngle._330;
		} else if(Double.compare(staticAngle, 345.0) == 0) {
			return TileAngle._345;
		} else {
			System.out.println("E> wrong angle : " + staticAngle + ", " + relativeAngle);
			return null;
		}
	}
	
	public static double getNextStaticAngle(double staticAngle, double relativeAngle, boolean reverse) {
		if(reverse) {
			staticAngle = staticAngle + relativeAngle - 180;
		} else {
			staticAngle = staticAngle - relativeAngle + 180;
		}
		
		if(staticAngle < 0) {
			staticAngle += 360;
		} else if(staticAngle >= 360) {
			staticAngle -= 360;
		}
		
		return staticAngle;
	}
	
}
