package com.mengcraft.db;

public interface MengManager {
	public MengTable getTable(String name);

	public void saveTable(String name);
}
