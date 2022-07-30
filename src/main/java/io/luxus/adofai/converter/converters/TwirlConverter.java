package io.luxus.adofai.converter.converters;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.adofai.converter.i18n.I18nCode;
import io.luxus.adofai.converter.i18n.I18n;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.action.Twirl;
import io.luxus.lib.adofai.util.NumberUtil;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class TwirlConverter implements MapConverter<TwirlConverter.Parameters> {

	private final I18n i18n;

	@Override
	public Parameters prepareParameters(Scanner scanner) {
		i18n.print(I18nCode.TWIRL_CONVERTER_ROTATION_RATE);
		double twirlRate = scanner.nextDouble();
		scanner.nextLine();

		return new Parameters(twirlRate);
	}

	@Override
	public boolean impossible(CustomLevel customLevel, Parameters parameters) {
		if (parameters.twirlRate < 0 || parameters.twirlRate > 1.0) {
			i18n.print(I18nCode.TWIRL_CONVERTER_ROTATION_RATE_ERROR);
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
