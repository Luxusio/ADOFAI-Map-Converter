package io.luxus.api.adofai.converter;

import java.util.ArrayList;
import java.util.List;

public class SafeDatatypeConverter {

	public static Double toDouble(Object value) {
		if (value == null || value instanceof Double) {
			return (Double) value;
		}
		return (double) (long) value;
	}

	public static List<Double> toDoubleList(Object valueListObject) {
		@SuppressWarnings("unchecked")
		List<Object> valueList = (List<Object>) valueListObject;
		List<Double> newList = new ArrayList<>();
		for (Object value : valueList) {
			newList.add(toDouble(value));
		}
		return newList;
	}

}
