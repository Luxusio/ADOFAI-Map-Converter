package test;

import io.luxus.adofai.converter.LinearMapConverter;
import io.luxus.adofai.converter.MapShapedMapConverter;
import io.luxus.adofai.converter.OuterMapConverter;
import io.luxus.adofai.converter.ShapedMapConverter;
import io.luxus.api.adofai.ADOFAIMap;
import io.luxus.api.adofai.MapData;

public class Test {
	public static void main(String[] args) {
		test();
	}
	public static void test() {
		String basePath = System.getProperty("user.dir") + "\\map";

		String in = "less_midspin_bug.adofai";
		String in_shape = "iL (fix).adofai";
		String same = "same.adofai";
		String outer = "outer.adofai";
		String linear = "linear.adofai";
		String shaped = "shaped.adofai";
		
		try {
			MapData map = new MapData();
			System.out.println("load");
			map.load(basePath + "\\" + in);
			
			MapData shapeMap = new MapData();
			shapeMap.load(basePath + "\\" + in_shape);
			
			//new ADOFAIMap(shapeMap);
			//shapeMap.save(basePath + "\\" + same);
			System.out.println("save");
			//map.save(basePath + "\\" + same);
			//OuterMapConverter.convert(map).save(basePath + "\\" + outer);
			MapShapedMapConverter.convert(map, shapeMap).save(basePath + "\\" + shaped);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
