package com.mengcraft.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.SafeJsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class DefaultMengRecord implements MengRecord {

	private final SafeJsonObject object;
	private final String uid;

	public DefaultMengRecord() {
		this(UUID.randomUUID().toString(), new SafeJsonObject());
	}

	public DefaultMengRecord(String json) {
		this(UUID.randomUUID().toString(), new JsonParser().parse(json).getAsJsonObject());
	}

	public DefaultMengRecord(String uid, SafeJsonObject object) {
		this.object = object;
		this.uid = uid;
	}

	@Override
	public void put(String key, String value) {
		getObject().addProperty(key, value);
	}

	@Override
	public void put(String key, Number value) {
		getObject().addProperty(key, value);
	}

	@Override
	public void put(String key, Boolean value) {
		getObject().addProperty(key, value);
	}

	@Override
	public void put(String key, List<String> value) {
		JsonArray array = new JsonArray();
		for (String string : value) {
			array.add(new JsonPrimitive(string));
		}
		getObject().add(key, array);
	}

	@Override
	public void remove(String key) {
		getObject().remove(key);
	}

	@Override
	public int getInteger(String key) {
		JsonElement element = getObject().get(key);
		return element != null ? element.getAsInt() : 0;
	}

	@Override
	public long getLong(String key) {
		JsonElement element = getObject().get(key);
		return element != null ? element.getAsLong() : 0;
	}

	@Override
	public double getDouble(String key) {
		JsonElement element = getObject().get(key);
		return element != null ? element.getAsDouble() : 0;
	}

	@Override
	public float getFloat(String key) {
		JsonElement element = getObject().get(key);
		return element != null ? element.getAsFloat() : 0;
	}

	@Override
	public boolean getBoolean(String key) {
		JsonElement element = getObject().get(key);
		return element != null ? element.getAsBoolean() : false;
	}

	@Override
	public String getString(String key) {
		JsonElement element = getObject().get(key);
		return element != null ? element.getAsString() : null;
	}

	@Override
	public List<String> getStringList(String key) {
		JsonElement element = getObject().get(key);
		if (element != null && element.isJsonArray()) {
			JsonArray array = element.getAsJsonArray();
			List<String> list = new ArrayList<String>();
			for (JsonElement jsonElement : array) {
				list.add(jsonElement.getAsString());
			}
			return list;
		} else {
			return new ArrayList<String>();
		}
	}

	@Override
	public SafeJsonObject getObject() {
		return object;
	}

	@Override
	public String toString() {
		return getObject().toString();
	}

	@Override
	public String getUid() {
		return uid;
	}

}
