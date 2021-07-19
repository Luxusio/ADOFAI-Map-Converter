package io.luxus.api.adofai.action;

import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.luxus.api.adofai.type.EventType;

public abstract class Action {

	private EventType eventType;

	protected Action(EventType eventType) {
		this.eventType = eventType;
	}

	public EventType getType() {
		return eventType;
	}

	public abstract void load(JSONObject json) throws ParseException;

	public abstract void save(StringBuilder sb, int floor);

	protected void saveBefore(StringBuilder sb, int floor) {
		sb.append("\t\t{ \"floor\": ").append(floor).append(", \"eventType\": \"").append(eventType.toString()).append('"');
	}

	protected void save(StringBuilder sb, String name, String value) {
		if(value == null) return;
		sb.append(", \"").append(name).append("\": \"").append(value).append('"');
	}

	protected void save(StringBuilder sb, String name, Long value) {
		if(value == null) return;
		sb.append(", \"").append(name).append("\": ").append(value);
	}

	protected void save(StringBuilder sb, String name, Double value) {
		if(value == null) return;
		sb.append(", \"").append(name).append("\": ");
		appendDoubleString(sb, value);	
	}
	

	@SuppressWarnings("rawtypes")
	protected void save(StringBuilder sb, String name, List listValue) {
		if(listValue == null) return;
		sb.append(", \"").append(name).append("\": [");
		Iterator it = listValue.iterator();

		while (true) {
			Object value = it.next();
			if(value instanceof String) {
				sb.append('"').append(value).append('"');
			}
			else if (value instanceof Long || value instanceof Integer) {
				sb.append(value);
			} else {
				appendDoubleString(sb, (double) value);
			}
			
			if (it.hasNext()) {
				sb.append(", ");
			} else {
				break;
			}
		}

		sb.append("]");
	}
	
	private void appendDoubleString(StringBuilder sb, double value) {
		double doubleValue = (double) value;
		long longValue = (long) doubleValue;
		if(doubleValue == longValue) {
			sb.append(longValue);
		} else {
			sb.append(String.format("%.6f", value));
		}
	}

	protected void saveAfter(StringBuilder sb) {
		sb.append(" },\n");
	}

}
