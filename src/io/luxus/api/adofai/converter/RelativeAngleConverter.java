package io.luxus.api.adofai.converter;

import io.luxus.api.adofai.type.TileAngle;

public class RelativeAngleConverter {
	
	public static double convert(TileAngle now, TileAngle next, TileAngle moreNext, boolean reverse) {
		double relativeAngle;
		if(next == TileAngle.NONE) {
			if(moreNext == null) {
				relativeAngle = -360;
			}
			else {
				if(reverse) {
					relativeAngle = moreNext.getAngle() - now.getAngle();
				}
				else {
					relativeAngle = now.getAngle() - moreNext.getAngle();
				}
			}
		}
		else {
			if(reverse) {
				relativeAngle = 180.0 - now.getAngle() + next.getAngle();
			}
			else {
				relativeAngle = 180.0 - next.getAngle() + now.getAngle();
			}
		}
		
		if(relativeAngle <= 0) {
			relativeAngle += 360;
		}
		else if(relativeAngle > 360) {
			relativeAngle -= 360;
		}
		
		return relativeAngle;
	}
	
}
