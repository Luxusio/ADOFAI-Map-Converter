package io.luxus.api.adofai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.module.MapModule;
import io.luxus.api.adofai.type.TileAngle;

public class MapData implements Cloneable {

	private MapSetting mapSetting;
	private List<TileData> tileDataList;

	private String path;

	public MapData() {
		this.mapSetting = new MapSetting();
		this.tileDataList = new ArrayList<>();
	}

	public MapData(MapSetting mapSetting, List<TileData> tileDataList) {
		this.mapSetting = mapSetting;
		this.tileDataList = tileDataList;
	}

	public void load() throws IOException, ParseException {
		this.load(this.path);
	}

	public void load(String path) throws IOException, ParseException {

		File f = new File(path);

		if (!f.exists()) {
			System.out.println("File not exists(" + this.path + ")");
			return;
		}

		// String buf = this.readString(f, "UTF8").substring(1);
		String buf = this.readString(f, "UTF8");
		if (buf.charAt(0) != '{') {
			buf = buf.substring(1);
		}

		try {
			JSONObject json = (JSONObject) new JSONParser().parse(buf);
			this.load(json);
		} catch (ParseException parseException) {
			//System.out.println(buf);
			throw parseException;
		}
	}

	public void load(JSONObject json) throws ParseException {

		String pathData = (String) json.get("pathData");
		JSONObject settings = (JSONObject) json.get("settings");

		// setting set
		this.mapSetting.load(settings);

		// tile data set
		@SuppressWarnings("unchecked")
		List<JSONObject> floorActions = (List<JSONObject>) json.get("actions");
		ListIterator<JSONObject> it = floorActions.listIterator();

		char[] chars = pathData.toCharArray();
		JSONObject obj;
		if (!it.hasNext())
			obj = null;
		else
			obj = it.next();

		TileData tileData = new TileData(0, TileAngle.NONE);
		tileDataList.add(tileData);
		if(obj != null) {
			while (0 == (Long) obj.get("floor")) {
				tileData.addAction(obj);
				if (it.hasNext()) {
					obj = it.next();
				} else {
					break;
				}
			}
		}
		for (int i = 1; i <= chars.length; i++) {
			char c = chars[i - 1];
			TileAngle tileAngle = MapModule.getCharTileAngleBiMap().get(c);
			if (tileAngle == null) {
				throw new NullPointerException("tileAngle convert failed:" + c);
			}
			tileData = new TileData(i, tileAngle);
			tileDataList.add(tileData);
			
			if(obj != null) {
				while (i == (Long) obj.get("floor")) {
					tileData.addAction(obj);
					if (it.hasNext()) {
						obj = it.next();
					} else {
						break;
					}
				}
			}
		}

	}

	private String readString(File f, String format) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), format));

		StringBuilder sb = new StringBuilder();
		String buf;

		while ((buf = reader.readLine()) != null) {
			sb.append(buf);
		}

		reader.close();

		return sb.toString();
	}

	public void save() throws IOException, IllegalArgumentException, IllegalAccessException {
		this.save(this.path);
	}

	public void save(String path) throws IOException, IllegalArgumentException, IllegalAccessException {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n\t\"pathData\": \"");

		Iterator<TileData> it = tileDataList.iterator();
		if (it.hasNext()) {
			it.next();
			while (it.hasNext()) {
				sb.append(it.next().getTileAngle().getName());
			}
		}

		sb.append("\", \n\t\"settings\":\n\t{\n");
		mapSetting.save(sb);
		sb.append("\t},\n\t\"actions\":\n\t[\n");
		for (TileData tileData : tileDataList) {
			tileData.save(sb);
		}
		sb.append("\t]\n}\n");

		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF8"));
			bufferedWriter.write(65279); // BOM write
			bufferedWriter.write(sb.toString());
		} finally {
			if(bufferedWriter != null) bufferedWriter.close();
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	// getter

	public String getPath() {
		return this.path;
	}
	
	public String getPathData() {
		StringBuilder sb = new StringBuilder();
		for(TileData tileData : tileDataList) {
			sb.append(tileData.getTileAngle().getName());
		}
		return sb.substring(1);
	}
	
	public MapSetting getSetting() {
		return this.mapSetting;
	}

	public List<TileData> getTileDataList() {
		return this.tileDataList;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


}
