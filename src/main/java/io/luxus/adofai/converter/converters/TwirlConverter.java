package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.action.Action;
import io.luxus.lib.adofai.action.Twirl;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.util.NumberUtil;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TwirlConverter implements MapConverter {

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

	@Override
	public Object[] prepareParameters(Scanner scanner) {
		System.out.print("회전 넣을 비율(0.0~1.0):");
		double twirlRate = scanner.nextDouble();
		scanner.nextLine();

		return new Object[] { twirlRate };
	}

	@Override
	public boolean impossible(CustomLevel customLevel, Object... args) {
		double twirlRate = (double) args[0];

		if (twirlRate < 0 || twirlRate > 1.0) {
			System.err.println("회전 비율은 0 이상 1.0 이하여야 합니다.");
			return true;
		}

		return false;
	}

	@Override
	public String getLevelPostfix(CustomLevel customLevel, Object... args) {
		double twirlRate = (double) args[0];

		return NumberUtil.fuzzyEquals(twirlRate, 0.0) ? "No Twirl" :
				NumberUtil.fuzzyEquals(twirlRate, 1.0) ? "All Twirl" :
						"Twirl rate " + twirlRate;
	}

	@Override
	public CustomLevel convert(CustomLevel customLevel, Object... args) {
		double twirlRate = (double) args[0];

		return MapConverterBase.convertBasedOnTravelAngle(customLevel, false, tile -> {
			List<Action> actionList = tile.getActions(EventType.TWIRL);
			actionList.clear();

			if (twirlRate > Math.random()) {
				actionList.add(new Twirl());
			}

			return tile.getTileMeta().getTravelAngle();
		});
	}
}
