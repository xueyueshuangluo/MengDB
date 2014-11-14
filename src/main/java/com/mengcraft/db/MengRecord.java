package com.mengcraft.db;

import java.util.List;

import com.google.gson.SafeJsonObject;

public interface MengRecord {
	
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
	
	public SafeJsonObject getObject();
}
