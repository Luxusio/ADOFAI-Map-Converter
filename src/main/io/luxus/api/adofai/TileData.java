package io.luxus.api.adofai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.action.Action;
import io.luxus.api.adofai.action.AddDecoration;
import io.luxus.api.adofai.action.AnimateTrack;
import io.luxus.api.adofai.action.Bloom;
import io.luxus.api.adofai.action.ChangeTrack;
import io.luxus.api.adofai.action.Checkpoint;
import io.luxus.api.adofai.action.ColorTrack;
import io.luxus.api.adofai.action.CustomBackground;
import io.luxus.api.adofai.action.Flash;
import io.luxus.api.adofai.action.HallOfMirrors;
import io.luxus.api.adofai.action.MoveCamera;
import io.luxus.api.adofai.action.MoveDecorations;
import io.luxus.api.adofai.action.MoveTrack;
import io.luxus.api.adofai.action.PositionTrack;
import io.luxus.api.adofai.action.RecolorTrack;
import io.luxus.api.adofai.action.RepeatEvents;
import io.luxus.api.adofai.action.SetConditionalEvents;
import io.luxus.api.adofai.action.SetFilter;
import io.luxus.api.adofai.action.SetHitsound;
import io.luxus.api.adofai.action.SetPlanetRotation;
import io.luxus.api.adofai.action.SetSpeed;
import io.luxus.api.adofai.action.ShakeScreen;
import io.luxus.api.adofai.action.Twirl;
import io.luxus.api.adofai.module.MapModule;
import io.luxus.api.adofai.type.EventType;
import io.luxus.api.adofai.type.TileAngle;

public class TileData {

	private int floor;
	private TileAngle tileAngle;

	private Map<EventType, List<Action>> actionListMap;

	public TileData(int floor, TileAngle tileAngle) {
		this.floor = floor;
		this.tileAngle = tileAngle;
		this.actionListMap = new HashMap<>();
	}

	public TileData(int floor, TileAngle tileAngle, Map<EventType, List<Action>> actionListMap) {
		this.floor = floor;
		this.tileAngle = tileAngle;
		this.actionListMap = actionListMap;
	}

	public void addAction(JSONObject json) throws ParseException {
		String eventTypeString = (String) json.get("eventType");
		EventType eventType = MapModule.getStringEventTypeBiMap().get(eventTypeString);
		if (eventType == null) {
			System.out.println("E: eventType Null : " + eventTypeString);
			return;
		}
		Action action = null;
		switch (eventType) {
		case SET_SPEED:
			action = new SetSpeed();
			break;
		case TWIRL:
			action = new Twirl();
			break;
		case CHECK_POINT:
			action = new Checkpoint();
			break;
		case CUSTOM_BACKGROUND:
			action = new CustomBackground();
			break;
		case COLOR_TRACK:
			action = new ColorTrack();
			break;
		case ANIMATE_TRACK:
			action = new AnimateTrack();
			break;
		case ADD_DECORATION:
			action = new AddDecoration();
			break;
		case FLASH:
			action = new Flash();
			break;
		case MOVE_CAMERA:
			action = new MoveCamera();
			break;
		case SET_HITSOUND:
			action = new SetHitsound();
			break;
		case RECOLOR_TRACK:
			action = new RecolorTrack();
			break;
		case MOVE_TRACK:
			action = new MoveTrack();
			break;
		case SET_FILTER:
			action = new SetFilter();
			break;
		case HALL_OF_MIRRORS:
			action = new HallOfMirrors();
			break;
		case SHAKE_SCREEN:
			action = new ShakeScreen();
			break;
		case SET_PLANET_ROTATION:
			action = new SetPlanetRotation();
			break;
		case MOVE_DECORATIONS:
			action = new MoveDecorations();
			break;
		case POSITION_TRACK:
			action = new PositionTrack();
			break;
		case REPEAT_EVENTS:
			action = new RepeatEvents();
			break;
		case BLOOM:
			action = new Bloom();
			break;
		case SET_CONDITIONAL_EVENTS:
			action = new SetConditionalEvents();
			break;
		case CHANGE_TRACK:
			action = new ChangeTrack();
			break;
		}

		List<Action> actionList = actionListMap.get(eventType);
		if (actionList == null) {
			actionList = new ArrayList<>();
			actionListMap.put(eventType, actionList);
		}

		action.load(json);
		actionList.add(action);
	}

	public void save(StringBuilder sb) {
		save(sb, EventType.SET_SPEED);
		save(sb, EventType.TWIRL);
		save(sb, EventType.CHECK_POINT);
		save(sb, EventType.CUSTOM_BACKGROUND);
		save(sb, EventType.COLOR_TRACK);
		save(sb, EventType.ANIMATE_TRACK);
		save(sb, EventType.ADD_DECORATION);
		save(sb, EventType.FLASH);
		save(sb, EventType.MOVE_CAMERA);
		save(sb, EventType.SET_HITSOUND);
		save(sb, EventType.RECOLOR_TRACK);
		save(sb, EventType.MOVE_TRACK);
		save(sb, EventType.SET_FILTER);
		save(sb, EventType.HALL_OF_MIRRORS);
		save(sb, EventType.SHAKE_SCREEN);
		save(sb, EventType.SET_PLANET_ROTATION);
		save(sb, EventType.MOVE_DECORATIONS);
		save(sb, EventType.POSITION_TRACK);
		save(sb, EventType.REPEAT_EVENTS);
		save(sb, EventType.BLOOM);
		save(sb, EventType.SET_CONDITIONAL_EVENTS);
	}

	private void save(StringBuilder sb, EventType eventType) {
		List<Action> actionList = actionListMap.get(eventType);
		if (actionList == null)
			return;
		for (Action action : actionList) {
			action.save(sb, floor);
		}
	}

	public void addNextTileActionListMap(Map<EventType, List<Action>> actionListMap) {
		// TODO : Check event(setSpeed, twirl) and change logic
		Iterator<Entry<EventType, List<Action>>> it = actionListMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<EventType, List<Action>> entry = it.next();

			EventType eventType = entry.getKey();
			getActionList(eventType).addAll(entry.getValue());
		}
	}

	// getter

	@Override
	public String toString() {
		return "f:" + floor + ", ta:" + tileAngle.getAngle() + ", a(" + actionListMap + ")";
	}

	public int getFloor() {
		return this.floor;
	}

	public TileAngle getTileAngle() {
		return this.tileAngle;
	}

	public Map<EventType, List<Action>> getActionListMap() {
		return actionListMap;
	}

	public List<Action> getActionList(EventType eventType) {
		List<Action> actionList = this.actionListMap.get(eventType);
		if (actionList == null) {
			actionList = new ArrayList<>();
			this.actionListMap.put(eventType, actionList);
		}

		return actionList;
	}

	public List<Action> getActionListIfNotEmpty(EventType eventType) {
		List<Action> actionList = this.actionListMap.get(eventType);
		if (actionList == null || actionList.isEmpty()) {
			return null;
		}
		return actionList;
	}

	public boolean containsAction(EventType eventType) {
		List<Action> actionList = this.actionListMap.get(eventType);
		return actionList != null && !actionList.isEmpty();
	}

}
