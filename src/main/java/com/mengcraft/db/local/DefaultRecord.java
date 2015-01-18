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

	private final JsonObject handle;
	private final String uid;

	public DefaultRecord() {
		this(UUID.randomUUID().toString(), new JsonObject());
	}

	/**
	 * Create MengRecord from JSON String.
	 * 
	 * @param json
	 */
	public DefaultRecord(String json) {
		this(UUID.randomUUID().toString(), new JsonParser().parse(json).getAsJsonObject());
	}

	public DefaultRecord(String uid, JsonObject object) {
		this.handle = object;
		this.uid = uid;
	}

	@Override
	public boolean has(String key) {
		return getHandle().has(key);
	}

	@Override
	public void put(String key, String value) {
		getHandle().addProperty(key, value);
	}

	@Override
	public void put(String key, Number value) {
		getHandle().addProperty(key, value);
	}

	@Override
	public void put(String key, Boolean value) {
		getHandle().addProperty(key, value);
	}

	@Override
	public void put(String key, List<String> value) {
		JsonArray array = new JsonArray();
		for (String string : value) {
			array.add(new JsonPrimitive(string));
		}
		getHandle().add(key, array);
	}

	@Override
	public void remove(String key) {
		getHandle().remove(key);
	}

	@Override
	public int getInteger(String key) {
		JsonElement element = getHandle().get(key);
		return element != null ? element.getAsInt() : 0;
	}

	@Override
	public long getLong(String key) {
		JsonElement element = getHandle().get(key);
		return element != null ? element.getAsLong() : 0;
	}

	@Override
	public double getDouble(String key) {
		JsonElement element = getHandle().get(key);
		return element != null ? element.getAsDouble() : 0;
	}

	@Override
	public float getFloat(String key) {
		JsonElement element = getHandle().get(key);
		return element != null ? element.getAsFloat() : 0;
	}

	@Override
	public boolean getBoolean(String key) {
		JsonElement element = getHandle().get(key);
		return element != null ? element.getAsBoolean() : false;
	}

	@Override
	public String getString(String key) {
		JsonElement element = getHandle().get(key);
		return element != null ? element.getAsString() : null;
	}

	@Override
	public List<String> getStringList(String key) {
		List<String> out = new ArrayList<>();
		JsonArray array = this.handle.get(key).getAsJsonArray();
		for (JsonElement element : array) {
			out.add(element.getAsString());
		}
		return out;
	}

	@Override
	public JsonObject getHandle() {
		return handle;
	}

	@Override
	public String toString() {
		JsonObject record = new JsonObject();
		record.add(getUid(), getHandle());
		return record.toString();
	}

	@Override
	public String getUid() {
		return uid;
	}

}
