package io.luxus.api.adofai.module;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import io.luxus.api.adofai.type.EventType;
import io.luxus.api.adofai.type.TileAngle;

public class MapModule {

	private static BiMap<String, EventType> stringEventTypeBiMap;
	private static BiMap<EventType, String> stringEventTypeBiMapInv;
	private static BiMap<Character, TileAngle> charTileAngleBiMap;
	//private static BiMap<TileAngle, Character> charTileAngleBiMapInv;
	
	public static BiMap<String, EventType> getStringEventTypeBiMap() {
		if(stringEventTypeBiMap == null) {
			stringEventTypeBiMap = HashBiMap.create();
			stringEventTypeBiMap.put(EventType.SET_SPEED.toString(), EventType.SET_SPEED);
			stringEventTypeBiMap.put(EventType.TWIRL.toString(), EventType.TWIRL);
			stringEventTypeBiMap.put(EventType.CHECK_POINT.toString(), EventType.CHECK_POINT);
			stringEventTypeBiMap.put(EventType.CUSTOM_BACKGROUND.toString(), EventType.CUSTOM_BACKGROUND);
			stringEventTypeBiMap.put(EventType.COLOR_TRACK.toString(), EventType.COLOR_TRACK);
			stringEventTypeBiMap.put(EventType.ANIMATE_TRACK.toString(), EventType.ANIMATE_TRACK);
			stringEventTypeBiMap.put(EventType.ADD_DECORATION.toString(), EventType.ADD_DECORATION);
			stringEventTypeBiMap.put(EventType.FLASH.toString(), EventType.FLASH);
			stringEventTypeBiMap.put(EventType.MOVE_CAMERA.toString(), EventType.MOVE_CAMERA);
			stringEventTypeBiMap.put(EventType.SET_HITSOUND.toString(), EventType.SET_HITSOUND);
			stringEventTypeBiMap.put(EventType.RECOLOR_TRACK.toString(), EventType.RECOLOR_TRACK);
			stringEventTypeBiMap.put(EventType.MOVE_TRACK.toString(), EventType.MOVE_TRACK);
			stringEventTypeBiMap.put(EventType.SET_FILTER.toString(), EventType.SET_FILTER);
			stringEventTypeBiMap.put(EventType.HALL_OF_MIRRORS.toString(), EventType.HALL_OF_MIRRORS);
			stringEventTypeBiMap.put(EventType.SHAKE_SCREEN.toString(), EventType.SHAKE_SCREEN);
			stringEventTypeBiMap.put(EventType.SET_PLANET_ROTATION.toString(), EventType.SET_PLANET_ROTATION);
			stringEventTypeBiMap.put(EventType.MOVE_DECORATIONS.toString(), EventType.MOVE_DECORATIONS);
			stringEventTypeBiMap.put(EventType.POSITION_TRACK.toString(), EventType.POSITION_TRACK);
			stringEventTypeBiMap.put(EventType.REPEAT_EVENTS.toString(), EventType.REPEAT_EVENTS);
			stringEventTypeBiMap.put(EventType.BLOOM.toString(), EventType.BLOOM);
			stringEventTypeBiMap.put(EventType.SET_CONDITIONAL_EVENTS.toString(), EventType.SET_CONDITIONAL_EVENTS);
			stringEventTypeBiMap.put(EventType.CHANGE_TRACK.toString(), EventType.CHANGE_TRACK);
		}
		
		return stringEventTypeBiMap;
	}
	
	public static BiMap<EventType, String> getStringEventTypeBiMapInv() {
		if(stringEventTypeBiMapInv == null) {
			stringEventTypeBiMapInv = MapModule.getStringEventTypeBiMap().inverse();
		}
		
		return stringEventTypeBiMapInv;
	}
	
	public static BiMap<Character, TileAngle> getCharTileAngleBiMap() {
		if(charTileAngleBiMap == null) {
			charTileAngleBiMap = HashBiMap.create();
			charTileAngleBiMap.put(TileAngle._0.getName(), TileAngle._0);
			charTileAngleBiMap.put(TileAngle._15.getName(), TileAngle._15);
			charTileAngleBiMap.put(TileAngle._30.getName(), TileAngle._30);
			charTileAngleBiMap.put(TileAngle._45.getName(), TileAngle._45);
			charTileAngleBiMap.put(TileAngle._60.getName(), TileAngle._60);
			charTileAngleBiMap.put(TileAngle._75.getName(), TileAngle._75);
			charTileAngleBiMap.put(TileAngle._90.getName(), TileAngle._90);
			charTileAngleBiMap.put(TileAngle._105.getName(), TileAngle._105);
			charTileAngleBiMap.put(TileAngle._120.getName(), TileAngle._120);
			charTileAngleBiMap.put(TileAngle._135.getName(), TileAngle._135);
			charTileAngleBiMap.put(TileAngle._150.getName(), TileAngle._150);
			charTileAngleBiMap.put(TileAngle._165.getName(), TileAngle._165);
			charTileAngleBiMap.put(TileAngle._180.getName(), TileAngle._180);
			charTileAngleBiMap.put(TileAngle._195.getName(), TileAngle._195);
			charTileAngleBiMap.put(TileAngle._210.getName(), TileAngle._210);
			charTileAngleBiMap.put(TileAngle._225.getName(), TileAngle._225);
			charTileAngleBiMap.put(TileAngle._240.getName(), TileAngle._240);
			charTileAngleBiMap.put(TileAngle._255.getName(), TileAngle._255);
			charTileAngleBiMap.put(TileAngle._270.getName(), TileAngle._270);
			charTileAngleBiMap.put(TileAngle._285.getName(), TileAngle._285);
			charTileAngleBiMap.put(TileAngle._300.getName(), TileAngle._300);
			charTileAngleBiMap.put(TileAngle._315.getName(), TileAngle._315);
			charTileAngleBiMap.put(TileAngle._330.getName(), TileAngle._330);
			charTileAngleBiMap.put(TileAngle._345.getName(), TileAngle._345);
			charTileAngleBiMap.put(TileAngle._5.getName(), TileAngle._5);
			charTileAngleBiMap.put(TileAngle._6.getName(), TileAngle._6);
			charTileAngleBiMap.put(TileAngle._7.getName(), TileAngle._7);
			charTileAngleBiMap.put(TileAngle._8.getName(), TileAngle._8);
			charTileAngleBiMap.put(TileAngle.NONE.getName(), TileAngle.NONE);
		}
		
		return charTileAngleBiMap;
	}
	
}
