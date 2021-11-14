package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.List;

public class TwirlConverter {
	public static CustomLevel convert(String path, double rate, boolean useCameraOptimization) throws IOException {
		CustomLevel customLevel = CustomLevelParser.readPath(path);

		return MapConverterBase.convertBasedOnTravelAngle(customLevel, useCameraOptimization, tile -> {
			List<Action> actionList = tile.getActions(EventType.TWIRL);
			actionList.clear();
			if (rate > Math.random()) {
				actionList.add(new Twirl());
			}

			return tile.getTileMeta().getTravelAngle();
		});
	}
	
}
