package com.mengcraft.db;

import java.util.List;

import com.mengcraft.db.util.com.google.gson.JsonObject;

public interface MengRecord {
	
	public boolean has(String key);
	
	public void put(String key, String value);

	public void put(String key, Number value);

	public void put(String key, Boolean value);
	
	public void put(String key, List<String> value);

	public void remove(String key);

	public int getInteger(String key);

	public long getLong(String key);

	public double getDouble(String key);

	public float getFloat(String key);

	public boolean getBoolean(String key);

	public String getString(String key);

	public List<String> getStringList(String key);
	
	public String getUid();
	
	public JsonObject getHandle();
}
