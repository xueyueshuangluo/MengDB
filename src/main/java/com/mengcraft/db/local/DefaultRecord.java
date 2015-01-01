package com.mengcraft.db.local;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mengcraft.db.MengRecord;
import com.mengcraft.db.util.com.google.gson.JsonArray;
import com.mengcraft.db.util.com.google.gson.JsonElement;
import com.mengcraft.db.util.com.google.gson.JsonObject;
import com.mengcraft.db.util.com.google.gson.JsonParser;
import com.mengcraft.db.util.com.google.gson.JsonPrimitive;

public class DefaultRecord implements MengRecord {

	private final JsonObject object;
	private final String uid;

	public DefaultRecord() {
		this(UUID.randomUUID().toString(), new JsonObject());
	}

	public DefaultRecord(String json) {
		this(UUID.randomUUID().toString(), new JsonParser().parse(json).getAsJsonObject());
	}

	public DefaultRecord(String uid, JsonObject object) {
		this.object = object;
		this.uid = uid;
	}
	
	@Override
	public boolean containsKey(String key) {
		return getObject().has(key);
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
	public JsonObject getObject() {
		return object;
	}

	@Override
	public String toString() {
		JsonObject record = new JsonObject();
		record.add(getUid(), getObject());
		return record.toString();
	}

	@Override
	public String getUid() {
		return uid;
	}

}
