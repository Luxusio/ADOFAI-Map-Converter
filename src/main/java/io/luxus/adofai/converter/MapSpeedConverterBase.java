package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.action.*;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.action.type.SpeedType;
import io.luxus.lib.adofai.action.type.Toggle;
import io.luxus.lib.adofai.util.NumberUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;
import static io.luxus.lib.adofai.Constants.EPSILON;


public class MapSpeedConverterBase {

	@Getter
	@RequiredArgsConstructor
	public static class ApplyEach {
		private final int floor;
		private final Tile tile;
		private final List<Tile> tileList;
		private final List<Tile> newTileList;
	}

	@Getter
	@RequiredArgsConstructor
	public static class ApplyEachReturnValue {
		private final Tile newTile;
		private final double nowTempBPM;
		private final double bpmMultiplier;
		private final double angleMultiplier;
	}

	public static CustomLevel convert(CustomLevel customLevel, boolean useCameraOptimization,
			Function<ApplyEach, ApplyEachReturnValue> applyEachFunction) throws IOException {

		LevelSetting newLevelSetting = customLevel.getLevelSetting()
				.toBuilder().build();
		List<Tile> tiles = customLevel.getTiles();

		List<Tile> newTiles = new ArrayList<>();
		newTiles.add(new Tile(0.0, new HashMap<>(tiles.get(0).getActionMap())));

		Tile tile;
		List<Action> actionList;
		double prevTempBPM = tiles.get(0).getTileMeta().getTempBPM();

		for (int i = 1; i < tiles.size(); i++) {
			tile = tiles.get(i);

			ApplyEachReturnValue applyEachReturnValue = applyEachFunction
					.apply(new ApplyEach(i, tile, tiles, newTiles));

			Tile newTile = applyEachReturnValue.getNewTile();
			double nowTempBPM = applyEachReturnValue.getNowTempBPM();
			double bpmMultiplier = applyEachReturnValue.getBpmMultiplier();
			double angleMultiplier = applyEachReturnValue.getAngleMultiplier();

			actionList = newTile.getActions(EventType.SET_SPEED);
			actionList.clear();

			// SetSpeed
			if (nowTempBPM != prevTempBPM) {
				if (!Double.isFinite(nowTempBPM) || NumberUtil.fuzzyEquals(nowTempBPM, 0.0)) {
					System.err.println("Wrong TempBPM value (" + nowTempBPM + ")");
				}
				actionList.add(new SetSpeed(SpeedType.BPM, nowTempBPM, 1.0));
			}

			// CustomBackground
			editAction(newTile, EventType.CUSTOM_BACKGROUND, (action) -> {
				CustomBackground a = (CustomBackground) action;
				return new CustomBackground(a.getColor(), a.getBgImage(), a.getImageColor(), a.getParallax(),
						a.getBgDisplayMode(), a.getLockRot(), a.getLoopBG(), a.getUnscaledSize(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// AnimateTrack
			editAction(newTile, EventType.ANIMATE_TRACK, (action) -> {
				AnimateTrack a = (AnimateTrack) action;
				return new AnimateTrack(a.getTrackAnimation(), a.getBeatsAhead() * bpmMultiplier,
						a.getTrackDisappearAnimation(), a.getBeatsBehind() * bpmMultiplier);
			});

			// Flash
			editAction(newTile, EventType.FLASH, (action) -> {
				Flash a = (Flash) action;
				return new Flash(a.getDuration() * bpmMultiplier, a.getPlane(), a.getStartColor(), a.getStartOpacity(),
						a.getEndColor(), a.getEndOpacity(), a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// MoveCamera
			editAction(newTile, EventType.MOVE_CAMERA, (action) -> {
				MoveCamera a = (MoveCamera) action;
				return new MoveCamera(a.getDuration() * bpmMultiplier, a.getRelativeTo(), a.getPosition(),
						a.getRotation(), a.getZoom(), a.getAngleOffset() * angleMultiplier, a.getEase(),
						a.getEventTag());
			});

			// RecolorTrack
			editAction(newTile, EventType.RECOLOR_TRACK, (action) -> {
				RecolorTrack a = (RecolorTrack) action;
				return new RecolorTrack(a.getStartTileNum(), a.getStartTilePosition(), a.getEndTileNum(), a.getEndTilePosition(), a.getTrackColorType(), a.getTrackColor(),
						a.getSecondaryTrackColor(), a.getTrackColorAnimDuration(), a.getTrackColorPulse(),
						a.getTrackPulseLength(), a.getTrackStyle(), a.getAngleOffset() * angleMultiplier,
						a.getEventTag());
			});

			// MoveTrack
			editAction(newTile, EventType.MOVE_TRACK, (action) -> {
				MoveTrack a = (MoveTrack) action;
				return new MoveTrack(a.getStartTileNum(), a.getStartTilePosition(), a.getEndTileNum(), a.getEndTilePosition(), a.getDuration() * bpmMultiplier,
						a.getPositionOffset(), a.getRotationOffset(), a.getScale(), a.getOpacity(),
						a.getAngleOffset() * angleMultiplier, a.getEase(), a.getEventTag());
			});

			// SetFilter
			editAction(newTile, EventType.SET_FILTER, (action) -> {
				SetFilter a = (SetFilter) action;
				return new SetFilter(a.getFilter(), a.getEnabled(), a.getIntensity(), a.getDisableOthers(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// HallOfMirrors
			editAction(newTile, EventType.HALL_OF_MIRRORS, (action) -> {
				HallOfMirrors a = (HallOfMirrors) action;
				return new HallOfMirrors(a.getEnabled(), a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// ShakeScreen
			editAction(newTile, EventType.SHAKE_SCREEN, (action) -> {
				ShakeScreen a = (ShakeScreen) action;
				return new ShakeScreen(a.getDuration() * bpmMultiplier, a.getStrength(), a.getIntensity(),
						a.getFadeOut(), a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			// MoveDecorations
			editAction(newTile, EventType.MOVE_DECORATIONS, (action) -> {
				MoveDecorations a = (MoveDecorations) action;
				return new MoveDecorations(a.getDuration() * bpmMultiplier, a.getTag(), a.getPositionOffset(),
						a.getRotationOffset(), a.getScale(), a.getColor(), a.getOpacity(), a.getAngleOffset() * angleMultiplier, a.getEase(),
						a.getEventTag());
			});

			// RepeatEvents
			editAction(newTile, EventType.REPEAT_EVENTS, (action) -> {
				RepeatEvents a = (RepeatEvents) action;
				return new RepeatEvents(a.getRepetitions(), a.getInterval() * bpmMultiplier, a.getTag());
			});

			// Bloom
			editAction(newTile, EventType.BLOOM, (action) -> {
				Bloom a = (Bloom) action;
				return new Bloom(a.getEnabled(), a.getThreshold(), a.getIntensity(), a.getColor(),
						a.getAngleOffset() * angleMultiplier, a.getEventTag());
			});

			prevTempBPM = nowTempBPM;
		}

		CustomLevel newCustomLevel = new CustomLevel(newLevelSetting, newTiles);
		fixFilterTiming(newCustomLevel);
		return newCustomLevel;
	}

	private static void editAction(Tile tile, EventType eventType, Function<Action, Action> function) {
		List<Action> actions = tile.getActions(eventType);
		List<Action> newActionList = new ArrayList<>();

		for (Action action : actions) {
			newActionList.add(function.apply(action));
		}

		actions.clear();
		actions.addAll(newActionList);

	}

	public static void removeNoneTile(CustomLevel customLevel) {

		List<Tile> tileList = customLevel.getTiles();
		Iterator<Tile> tileIt = tileList.iterator();

		Tile prevTile = tileIt.next();
		while (tileIt.hasNext()) {
			Tile nowTile = tileIt.next();

			if (nowTile.getAngle() == ANGLE_MID_TILE) {
				tileIt.remove();

				prevTile.getTileMeta().forceInit(nowTile.getTileMeta());
				for (Map.Entry<EventType, List<Action>> entry : nowTile.getActionMap().entrySet()) {
					EventType eventType = entry.getKey();

					if (eventType.isSingleOnly()) {
						List<Action> prevActions = prevTile.getActions(eventType);

						prevActions.addAll(entry.getValue());

						if (eventType == EventType.TWIRL) {
							if (prevActions.size() % 2 == 0) {
								prevActions.clear();
							}
							else {
								prevActions.clear();
								prevActions.add(new Twirl());
							}
						}
						else {
							while (prevActions.size() > 1) {
								prevActions.remove(0);
							}
						}

					}
					else {
						prevTile.getActions(eventType).addAll(entry.getValue());
					}
				}

			}

			prevTile = nowTile;
		}

	}

	public static void fixFilterTiming(CustomLevel customLevel) {

		for (Tile tile : customLevel.getTiles()) {
			TileMeta tileMeta = tile.getTileMeta();

			editAction(tile, EventType.SET_FILTER, (action) -> {
				SetFilter a = (SetFilter) action;

				double angleOffset = a.getAngleOffset();

				if (a.getDisableOthers() == Toggle.ENABLED &&
						NumberUtil.fuzzyEquals(tileMeta.getTravelAngle(), angleOffset)) {
					angleOffset = Math.max(angleOffset - 0.001, 0.0);
				}

				return new SetFilter(a.getFilter(), a.getEnabled(), a.getIntensity(), a.getDisableOthers(),
						angleOffset, a.getEventTag());
			});
		}


	}

}
