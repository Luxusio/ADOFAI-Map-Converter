package io.luxus.adofai.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.ADOFAIMap;
import io.luxus.api.adofai.MapData;
import io.luxus.api.adofai.MapSetting;
import io.luxus.api.adofai.Tile;
import io.luxus.api.adofai.TileData;
import io.luxus.api.adofai.action.Action;
import io.luxus.api.adofai.action.AnimateTrack;
import io.luxus.api.adofai.action.Bloom;
import io.luxus.api.adofai.action.CustomBackground;
import io.luxus.api.adofai.action.Flash;
import io.luxus.api.adofai.action.HallOfMirrors;
import io.luxus.api.adofai.action.MoveCamera;
import io.luxus.api.adofai.action.MoveDecorations;
import io.luxus.api.adofai.action.MoveTrack;
import io.luxus.api.adofai.action.RecolorTrack;
import io.luxus.api.adofai.action.RepeatEvents;
import io.luxus.api.adofai.action.SetFilter;
import io.luxus.api.adofai.action.SetSpeed;
import io.luxus.api.adofai.action.ShakeScreen;
import io.luxus.api.adofai.type.EventType;

public class MapSpeedConverterBase {
	
	public static class ApplyEach {
		private int floor;
		private double prevTempBPM;
		private Tile tile;
		private List<Tile> tileList;
		private List<TileData> newTileDataList;
		
		public ApplyEach(int floor, double prevTempBPM, Tile tile, List<Tile> tileList, List<TileData> newTileDataList) {
			this.floor = floor;
			this.prevTempBPM = prevTempBPM;
			this.tile = tile;
			this.tileList = tileList;
			this.newTileDataList = newTileDataList;
		}

		public int getFloor() {
			return floor;
		}
		
		public double getPrevTempBPM() {
			return prevTempBPM;
		}

		public Tile getTile() {
			return tile;
		}

		public List<Tile> getTileList() {
			return tileList;
		}
		
		public List<TileData> getNewTileDataList() {
			return newTileDataList;
		}
	}
	
	public static class ApplyEachReturnValue {
		private TileData tileData;
		private double nowTempBPM;
		private double bpmMultiplier;
		private double angleMultiplier;
		
		public ApplyEachReturnValue(TileData tileData, double nowTempBPM, double bpmMultiplier, double angleMultiplier) {
			this.tileData = tileData;
			this.nowTempBPM = nowTempBPM;
			this.bpmMultiplier = bpmMultiplier;
			this.angleMultiplier = angleMultiplier;
		}
		
		public TileData getTileData() {
			return tileData;
		}

		public double getNowTempBPM() {
			return nowTempBPM;
		}

		public double getBpmMultiplier() {
			return bpmMultiplier;
		}

		public double getAngleMultiplier() {
			return angleMultiplier;
		}
	}
	
	public static MapData convert(ADOFAIMap adofaiMap, Function<ApplyEach, ApplyEachReturnValue> applyEachFunction) throws ParseException {
		
		MapSetting newMapSetting = adofaiMap.getSetting();
		List<Tile> tileList = adofaiMap.getTileList();
		List<TileData> tileDataList = adofaiMap.getTileDataList();
		
		List<TileData> newTileDataList = new ArrayList<>();
		newTileDataList.add(tileDataList.get(0));
		

		Tile tile;
		List<Action> actionList;
		double prevTempBPM = 0.0;

		for (int i = 1; i < tileList.size(); i++) {
			tile = tileList.get(i);
			
			ApplyEachReturnValue applyEachReturnValue = applyEachFunction.apply(new ApplyEach(i, prevTempBPM, tile, tileList, newTileDataList));
			
			TileData newTileData = applyEachReturnValue.getTileData();
			double nowTempBPM = applyEachReturnValue.getNowTempBPM();
			double bpmMultiplier = applyEachReturnValue.getBpmMultiplier();
			double angleMultiplier = applyEachReturnValue.getAngleMultiplier();
			
			actionList = newTileData.getActionList(EventType.SET_SPEED);
			actionList.clear();
			// SetSpeed
			if (nowTempBPM != prevTempBPM) {
				SetSpeed setSpeed = new SetSpeed("Bpm", nowTempBPM, 1.0);
				actionList.add(setSpeed);
			}
			
			// CustomBackground
			editAction(newTileData, EventType.CUSTOM_BACKGROUND, (action)-> {
				CustomBackground a = (CustomBackground) action;
				return new CustomBackground(a.getColor(), a.getBgImage(), a.getImageColor(),
						a.getParallax(), a.getBgDisplayMode(), a.getLockRot(), a.getLoopBG(), a.getUnscaledSize(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});
			
			// AnimateTrack
			editAction(newTileData, EventType.ANIMATE_TRACK, (action)-> {
				AnimateTrack a = (AnimateTrack) action;
				return new AnimateTrack(a.getTrackAnimation(), a.getBeatsAhead() * bpmMultiplier, a.getTrackDisappearAnimation(),
						a.getBeatsBehind() * bpmMultiplier);
			});
			
			// Flash
			editAction(newTileData, EventType.FLASH, (action)-> {
				Flash a = (Flash) action;
				return new Flash(a.getDuration() * bpmMultiplier, a.getPlane(), a.getStartColor(),
						a.getStartOpacity(), a.getEndColor(), a.getEndOpacity(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// MoveCamera
			editAction(newTileData, EventType.MOVE_CAMERA, (action)-> {
				MoveCamera a = (MoveCamera) action;
				return new MoveCamera(a.getDuration() * bpmMultiplier, a.getRelativeTo(),
						a.getPosition(), a.getRotation(), a.getZoom(), a.getAngleOffset() * angleMultiplier,
						a.getEase(), a.getEventTag());
			});

			// RecolorTrack
			editAction(newTileData, EventType.RECOLOR_TRACK, (action)-> {
				RecolorTrack a = (RecolorTrack) action;
				return new RecolorTrack(a.getStartTile(), a.getEndTile(), a.getTrackColorType(),
						a.getTrackColor(), a.getSecondaryTrackColor(), a.getTrackColorAnimDuration(),
						a.getTrackColorPulse(), a.getTrackPulseLength(), a.getTrackStyle(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// MoveTrack
			editAction(newTileData, EventType.MOVE_TRACK, (action)-> {
				MoveTrack a = (MoveTrack) action;
				return new MoveTrack(a.getStartTile(), a.getEndTile(), a.getDuration() * bpmMultiplier,
						a.getPositionOffset(), a.getRotationOffset(), a.getScale(), a.getOpacity(),
						a.getAngleOffset() * angleMultiplier, a.getEase(), a.getEventTag());
			});

			// SetFilter
			editAction(newTileData, EventType.SET_FILTER, (action)-> {
				SetFilter a = (SetFilter) action;
				return new SetFilter(a.getFilter(), a.getEnabled(), a.getIntensity(),
						a.getDisableOthers(), a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// HallOfMirrors
			editAction(newTileData, EventType.HALL_OF_MIRRORS, (action)-> {
				HallOfMirrors a = (HallOfMirrors) action;
				return new HallOfMirrors(a.getEnabled(), a.getAngleOffset() * angleMultiplier,
						a.getEventTag());
			});

			// ShakeScreen
			editAction(newTileData, EventType.SHAKE_SCREEN, (action)-> {
				ShakeScreen a = (ShakeScreen) action;
				return new ShakeScreen(a.getDuration() * bpmMultiplier, a.getStrength(),
						a.getIntensity(), a.getFadeOut(), a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// MoveDecorations
			editAction(newTileData, EventType.MOVE_DECORATIONS, (action)-> {
				MoveDecorations a = (MoveDecorations) action;
				return new MoveDecorations(a.getDuration() * bpmMultiplier, a.getTag(),
						a.getPositionOffset(), a.getRotationOffset(), a.getScale(),
						a.getAngleOffset() * angleMultiplier, a.getEase(), a.getEventTag());
			});

			// RepeatEvents
			editAction(newTileData, EventType.REPEAT_EVENTS, (action)-> {
				RepeatEvents a = (RepeatEvents) action;
				return new RepeatEvents(a.getRepetitions(), a.getInterval() * bpmMultiplier,
						a.getTag());
			});

			// Bloom
			editAction(newTileData, EventType.BLOOM, (action)-> {
				Bloom a = (Bloom) action;
				return new Bloom(a.getEnabled(), a.getThreshold(), a.getIntensity(), a.getColor(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});
			
			prevTempBPM = nowTempBPM;
		}

		return new MapData(newMapSetting, newTileDataList);
	}
	
	private static void editAction(TileData tileData, EventType eventType, Function<Action, Action> function) {
		List<Action> actionList = tileData.getActionListIfNotEmpty(eventType);
		if (actionList != null) {
			List<Action> newActionList = new ArrayList<>();

			for (Action action : actionList) {
				newActionList.add(function.apply(action));
			}

			actionList.clear();
			actionList.addAll(newActionList);
		}
	}
	
}
