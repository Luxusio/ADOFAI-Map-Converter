package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.type.action.Action;
import io.luxus.lib.adofai.type.action.Twirl;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.util.NumberUtil;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TwirlConverter implements MapConverter<TwirlConverter.Parameters> {

	@Override
	public Parameters prepareParameters(Scanner scanner) {
		System.out.print("회전 넣을 비율(0.0~1.0):");
		double twirlRate = scanner.nextDouble();
		scanner.nextLine();

		return new Parameters(twirlRate);
	}

	@Override
	public boolean impossible(CustomLevel customLevel, Parameters parameters) {
		if (parameters.twirlRate < 0 || parameters.twirlRate > 1.0) {
			System.err.println("회전 비율은 0 이상 1.0 이하여야 합니다.");
			return true;
		}

		return false;
	}

	@Override
	public String getLevelPostfix(CustomLevel customLevel, Parameters parameters) {
		return NumberUtil.fuzzyEquals(parameters.twirlRate, 0.0) ? "No Twirl" :
				NumberUtil.fuzzyEquals(parameters.twirlRate, 1.0) ? "All Twirl" :
						"Twirl rate " + parameters.twirlRate;
	}

	@Override
	public CustomLevel convert(CustomLevel customLevel, Parameters parameters) {
		return MapConverterBase
				.convertBasedOnTravelAngle(customLevel, false, tile -> tile.getTileMeta().getTravelAngle(), tileBuilder -> {
					tileBuilder.removeActions(EventType.TWIRL);
					if (parameters.twirlRate > Math.random()) {
						tileBuilder.addAction(new Twirl.Builder().build());
					}
				});
	}

	@RequiredArgsConstructor
	public static class Parameters {
		private final double twirlRate;
	}

}
