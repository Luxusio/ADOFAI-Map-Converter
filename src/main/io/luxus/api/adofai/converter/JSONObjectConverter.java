package io.luxus.api.adofai.converter;

import org.json.simple.JSONArray;

public class JSONObjectConverter {
	
	public static int[] toIntArray(JSONArray jsonArray) {
		int[] intArray = new int[jsonArray.size()];
		int i=0;
		for(Object obj : jsonArray) {
			intArray[i++] = (int) obj;
		}
		return intArray;
	}
	
}
