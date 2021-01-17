package test;

import io.luxus.adofai.converter.LinearMapConverter;
import io.luxus.adofai.converter.OuterMapConverter;
import io.luxus.adofai.converter.ShapedMapConverter;
import io.luxus.api.adofai.MapData;

public class Test {
	public static void main(String[] args) {
		test();
	}
	public static void test() {
		String basePath = System.getProperty("user.dir") + "\\map";

		String in = "360.adofai";
		String same = "same.adofai";
		String outer = "outer.adofai";
		String linear = "linear.adofai";
		String shaped = "shaped.adofai";
		
		try {
			MapData map = new MapData();
			System.out.println("load");
			map.load(basePath + "\\" + in);

			System.out.println("save");
			map.save(basePath + "\\" + same);
			OuterMapConverter.convert(map).save(basePath + "\\" + outer);
			LinearMapConverter.convert(map).save(basePath + "\\" + linear);
			ShapedMapConverter.convert(map, "RU").save(basePath + "\\" + shaped);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
