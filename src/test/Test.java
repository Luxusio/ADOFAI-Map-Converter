package test;

import io.luxus.adofai.converter.TwirlConverter;
import io.luxus.adofai.converter.LinearMapConverter;
import io.luxus.adofai.converter.MapShapedMapConverter;
import io.luxus.adofai.converter.OuterMapConverter;
import io.luxus.adofai.converter.ShapedMapConverter;
import io.luxus.api.adofai.ADOFAIMap;
import io.luxus.api.adofai.MapData;

@SuppressWarnings("unused")
public class Test {
	public static void main(String[] args) {
		System.out.println(Math.atan2(1.0, 1.0) / 3.141592653589793238 * 180);
		//test();
	}
	public static void test() {
		String basePath = System.getProperty("user.dir") + "\\map";

		String in = "Insane.adofai";
		String in_shape = "Crystallized.adofai";
		String same = "same.adofai";
		String outer = "outer.adofai";
		String linear = "linear.adofai";
		String shaped = "shaped.adofai";
		String allTwirl = "allTwirl.adofai";
		String noTwirl = "noTwirl.adofai";
		
		try {
			String path = basePath + "\\" + in;
			MapData map = new MapData();
			System.out.println("load");
			map.load(basePath + "\\" + in);
			
			MapData shapeMap = new MapData();
			shapeMap.load(basePath + "\\" + in_shape);
			
			//new ADOFAIMap(shapeMap);
			//shapeMap.save(basePath + "\\" + same);
			System.out.println("save");
			//map.save(basePath + "\\" + same);
			//OuterMapConverter.convert((MapData) map.clone()).save(basePath + "\\" + outer);
			//MapShapedMapConverter.convert((MapData) map.clone(), shapeMap).save(basePath + "\\" + shaped);
			LinearMapConverter.convert(path, true).save(basePath + "\\" + linear);
			//TwirlConverter.convert((MapData) map.clone(), true).save(basePath + "\\" + allTwirl);
			//TwirlConverter.convert(path, false).save(basePath + "\\" + noTwirl);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
