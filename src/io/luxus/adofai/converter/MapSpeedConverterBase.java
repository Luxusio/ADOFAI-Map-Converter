package io.luxus.adofai.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import io.luxus.api.adofai.type.TileAngle;

public class MapSpeedConverterBase {
	
	public static final double mInfinity = -1.0 / 0.0;
	public static final double pInfinity = 1.0 / 0.0;
	
	public static ADOFAIMap getMap(String path) throws IOException, ParseException {
		MapData mapData = new MapData();
		mapData.load(path);
		return new ADOFAIMap(mapData);
	}

	public static class ApplyEach {
		private int floor;
		private double prevTempBPM;
		private Tile tile;
		private List<Tile> tileList;
		private List<TileData> newTileDataList;

		public ApplyEach(int floor, double prevTempBPM, Tile tile, List<Tile> tileList,
				List<TileData> newTileDataList) {
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

		public ApplyEachReturnValue(TileData tileData, double nowTempBPM, double bpmMultiplier,
				double angleMultiplier) {
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

	public static MapData convert(String path, List<Integer> removedTileList, ADOFAIMap adofaiMap, boolean useCameraOptimization, 
			Function<ApplyEach, ApplyEachReturnValue> applyEachFunction) throws ParseException, IOException {

		MapSetting newMapSetting = adofaiMap.getSetting();
		List<Tile> tileList = adofaiMap.getTileList();
		List<TileData> tileDataList = adofaiMap.getTileDataList();

		List<TileData> newTileDataList = new ArrayList<>();
		newTileDataList.add(tileDataList.get(0));

		Tile tile;
		List<Action> actionList;
		double prevTempBPM = newMapSetting.getBpm();

		for (int i = 1; i < tileList.size(); i++) {
			tile = tileList.get(i);

			ApplyEachReturnValue applyEachReturnValue = applyEachFunction
					.apply(new ApplyEach(i, prevTempBPM, tile, tileList, newTileDataList));

			TileData newTileData = applyEachReturnValue.getTileData();
			double nowTempBPM = applyEachReturnValue.getNowTempBPM();
			double bpmMultiplier = applyEachReturnValue.getBpmMultiplier();
			double angleMultiplier = applyEachReturnValue.getAngleMultiplier();

			actionList = newTileData.getActionList(EventType.SET_SPEED);
			actionList.clear();

			// SetSpeed
			if (nowTempBPM != prevTempBPM) {
				if (nowTempBPM == 0.0) {
					System.out.println("E> tempBPM 0");
				}
				actionList.add(new SetSpeed("Bpm", nowTempBPM, 1.0));
			}

			// CustomBackground
			editAction(newTileData, EventType.CUSTOM_BACKGROUND, (action) -> {
				CustomBackground a = (CustomBackground) action;
				return new CustomBackground(a.getColor(), a.getBgImage(), a.getImageColor(), a.getParallax(),
						a.getBgDisplayMode(), a.getLockRot(), a.getLoopBG(), a.getUnscaledSize(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// AnimateTrack
			editAction(newTileData, EventType.ANIMATE_TRACK, (action) -> {
				AnimateTrack a = (AnimateTrack) action;
				return new AnimateTrack(a.getTrackAnimation(), a.getBeatsAhead() * bpmMultiplier,
						a.getTrackDisappearAnimation(), a.getBeatsBehind() * bpmMultiplier);
			});

			// Flash
			editAction(newTileData, EventType.FLASH, (action) -> {
				Flash a = (Flash) action;
				return new Flash(a.getDuration() * bpmMultiplier, a.getPlane(), a.getStartColor(), a.getStartOpacity(),
						a.getEndColor(), a.getEndOpacity(), a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// MoveCamera
			editAction(newTileData, EventType.MOVE_CAMERA, (action) -> {
				MoveCamera a = (MoveCamera) action;
				return new MoveCamera(a.getDuration() * bpmMultiplier, a.getRelativeTo(), a.getPosition(),
						a.getRotation(), a.getZoom(), a.getAngleOffset() * angleMultiplier, a.getEase(),
						a.getEventTag());
			});

			// RecolorTrack
			editAction(newTileData, EventType.RECOLOR_TRACK, (action) -> {
				RecolorTrack a = (RecolorTrack) action;
				return new RecolorTrack(a.getStartTile(), a.getEndTile(), a.getTrackColorType(), a.getTrackColor(),
						a.getSecondaryTrackColor(), a.getTrackColorAnimDuration(), a.getTrackColorPulse(),
						a.getTrackPulseLength(), a.getTrackStyle(), a.getAngleOffset() * angleMultiplier,
						a.getEventTag());
			});

			// MoveTrack
			editAction(newTileData, EventType.MOVE_TRACK, (action) -> {
				MoveTrack a = (MoveTrack) action;
				return new MoveTrack(a.getStartTile(), a.getEndTile(), a.getDuration() * bpmMultiplier,
						a.getPositionOffset(), a.getRotationOffset(), a.getScale(), a.getOpacity(),
						a.getAngleOffset() * angleMultiplier, a.getEase(), a.getEventTag());
			});

			// SetFilter
			editAction(newTileData, EventType.SET_FILTER, (action) -> {
				SetFilter a = (SetFilter) action;
				return new SetFilter(a.getFilter(), a.getEnabled(), a.getIntensity(), a.getDisableOthers(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// HallOfMirrors
			editAction(newTileData, EventType.HALL_OF_MIRRORS, (action) -> {
				HallOfMirrors a = (HallOfMirrors) action;
				return new HallOfMirrors(a.getEnabled(), a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// ShakeScreen
			editAction(newTileData, EventType.SHAKE_SCREEN, (action) -> {
				ShakeScreen a = (ShakeScreen) action;
				return new ShakeScreen(a.getDuration() * bpmMultiplier, a.getStrength(), a.getIntensity(),
						a.getFadeOut(), a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// MoveDecorations
			editAction(newTileData, EventType.MOVE_DECORATIONS, (action) -> {
				MoveDecorations a = (MoveDecorations) action;
				return new MoveDecorations(a.getDuration() * bpmMultiplier, a.getTag(), a.getPositionOffset(),
						a.getRotationOffset(), a.getScale(), a.getAngleOffset() * angleMultiplier, a.getEase(),
						a.getEventTag());
			});

			// RepeatEvents
			editAction(newTileData, EventType.REPEAT_EVENTS, (action) -> {
				RepeatEvents a = (RepeatEvents) action;
				return new RepeatEvents(a.getRepetitions(), a.getInterval() * bpmMultiplier, a.getTag());
			});

			// Bloom
			editAction(newTileData, EventType.BLOOM, (action) -> {
				Bloom a = (Bloom) action;
				return new Bloom(a.getEnabled(), a.getThreshold(), a.getIntensity(), a.getColor(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			prevTempBPM = nowTempBPM;
		}

		// optimize camera movement / etc..

		MapData mapData = new MapData(newMapSetting, newTileDataList);
		
		if(true) return mapData;

		@SuppressWarnings("unused")
		ADOFAIMap newAdofaiMap = new ADOFAIMap(mapData);

		MapData oldMapData = new MapData();
		oldMapData.load(path);
		ADOFAIMap oldAdofaiMap = new ADOFAIMap(oldMapData);

		List<Tile> oldTileList = oldAdofaiMap.getTileList();
		List<Tile> newTileList = newAdofaiMap.getTileList();
		Set<Integer> removedTileSet = new HashSet<>();
		removedTileSet.addAll(removedTileList);

		int oldMaxFloor = tileList.size() - 1;
		int newMaxFloor = newTileList.size() - 1;

		newTileDataList = new ArrayList<>();
		for (Tile newTile : newTileList) {
			newTileDataList.add(new TileData(newTile.getFloor(), newTile.getTileAngle(), newTile.getActionListMap()));

			editAction(newTile, EventType.MOVE_CAMERA, (action) -> {
				MoveCamera a = (MoveCamera) action;

				int nowFloor = newTile.getFloor();
				Tile oldTile = tileList.get(nowFloor);

				double useFirstRatio = a.getAngleOffset() / newTile.getRelativeAngle();

				double durationMS = newTile.getTileDurationMS() * a.getDuration();
				// get oldTile A, B newTile A ,B

				Tile oldTileA = oldTileList.get(nowFloor);

				double oldDurationLeft = durationMS - oldTileA.getTileDurationMS() * useFirstRatio;
				if (oldDurationLeft > 0.0) {
					for (int i = nowFloor + 1; i < oldMaxFloor; i++) {
						oldTileA = oldTileList.get(i);
						oldDurationLeft -= oldTileA.getTileDurationMS();
						if (oldDurationLeft < 0.0) {
							break;
						}
					}
				}

				if (oldTileA.getTileAngle() == TileAngle.NONE) {
					oldTileA = oldTileList.get(oldMaxFloor);
				}

				Tile newTileA = newTileList.get(nowFloor);

				double newDurationLeft = durationMS - newTileA.getTileDurationMS() * useFirstRatio;
				if (newDurationLeft > 0.0) {
					for (int i = nowFloor + 1; i < newMaxFloor; i++) {
						newTileA = newTileList.get(i);
						newDurationLeft -= newTileA.getTileDurationMS();
						if (newDurationLeft < 0.0) {
							break;
						}
					}
				}

				if (newTileA.getTileAngle() == TileAngle.NONE) {
					newTileA = newTileList.get(newMaxFloor);
				}

				double oldX = -oldTile.getX();
				double oldY = -oldTile.getY();

				if (newDurationLeft <= 0.0) {
					oldX += oldTileA.getX();
					oldY += oldTileA.getY();
				} else {
					double multiple = oldDurationLeft / oldTileA.getTileDurationMS();

					double staticAngle = oldTileA.getStaticAngle();
					double rad = Math.toRadians(staticAngle);

					oldX += oldTileA.getX() + Math.cos(rad) * multiple;
					oldY += oldTileA.getY() + Math.sin(rad) * multiple;
				}

				double newX = -newTile.getX();
				double newY = -newTile.getY();

				if (newDurationLeft <= 0.0) {
					newX += newTileA.getX();
					newY += newTileA.getY();
				} else {
					double multiple = newDurationLeft / newTileA.getTileDurationMS();

					double staticAngle = newTileA.getStaticAngle();
					double rad = Math.toRadians(staticAngle);

					newX += newTileA.getX() + Math.cos(rad) * multiple;
					newY += newTileA.getY() + Math.sin(rad) * multiple;
				}

				double originalX = a.getPosition().get(0);
				double originalY = a.getPosition().get(1);
				double originalLength = Math.pow(Math.pow(originalX, 2) + Math.pow(originalY, 2), 0.5);
				//double originalRadAngle = Math.atan2(originalY, originalX);
				
				
				double oldLength = Math.pow(Math.pow(oldX, 2) + Math.pow(oldY, 2), 0.5);
				//double oldRadAngle = Math.atan2(oldY, oldX);
				double newLength = Math.pow(Math.pow(newX, 2) + Math.pow(newY, 2), 0.5);
				double newRadAngle = Math.atan2(newY, newX);
				
				double multiple = originalLength / oldLength * newLength;
				if(oldLength == 0) multiple = 0.0;
				
				if(checkWrongNumber(multiple)) {
					System.out.println("E> Wrong multiple Value:" + multiple + ". Set multiple as originalLength.");
					multiple = originalLength;
				}
				
				double finalX = Math.cos(newRadAngle) * multiple;
				double finalY = Math.sin(newRadAngle) * multiple;
				
				
				// original mechanism
//				double finalX;
//				double finalY;
//				
//				if (originalX == 0 || newX == 0) {
//					finalX = 0;
//				} else {
//					finalX = originalX / oldX * newX;
//				}
//
//				if (originalY == 0 || newY == 0) {
//					finalY = 0;
//				} else {
//					finalY = originalY / oldY * newY;
//				}

//				System.out.println("mc:(" + a.getPosition().get(0) + ", " + a.getPosition().get(1) + ") " + oldX + ", "
//						+ newX + ", " + oldY + ", " + newY + " - (" + finalX + ", " + finalY + ")");

				List<Double> positionList = new ArrayList<>();
				positionList.add(finalX);
				positionList.add(finalY);

				return new MoveCamera(a.getDuration(), a.getRelativeTo(), positionList, a.getRotation(), a.getZoom(),
						a.getAngleOffset(), a.getEase(), a.getEventTag());
			});

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

	public static List<Integer> removeNoneTile(ADOFAIMap adofaiMap) {
		List<Integer> removedTileList = new ArrayList<>();
		int floor = 0;

		List<Tile> tileList = adofaiMap.getTileList();
		List<TileData> tileDataList = adofaiMap.getTileDataList();

		Iterator<Tile> tileIt = tileList.iterator();
		Iterator<TileData> tileDataIt = tileDataList.iterator();

		tileIt.next();
		tileDataIt.next();

		Tile prevTile = null;
		TileData prevTileData = null;
		while (tileDataIt.hasNext()) {
			Tile nowTile = tileIt.next();
			TileData nowTileData = tileDataIt.next();
			floor++;

			if (nowTileData.getTileAngle() == TileAngle.NONE) {
				tileIt.remove();
				tileDataIt.remove();
				removedTileList.add(floor);

				prevTileData.addNextTileActionListMap(nowTileData.getActionListMap());
				prevTile.setRelativeAngle(nowTile.getRelativeAngle());
			}

			prevTile = nowTile;
			prevTileData = nowTileData;
		}

		return removedTileList;
	}
	
	public static boolean checkWrongNumber(double value) {
		return value <= mInfinity || value >= pInfinity;
	}

}
