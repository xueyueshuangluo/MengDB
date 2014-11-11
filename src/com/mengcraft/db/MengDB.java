package com.mengcraft.db;

import org.bukkit.plugin.java.JavaPlugin;

public class MengDB extends JavaPlugin {
	private static MengDB mengDB;

	@Override
	public void onLoad() {
		setMengDB(this);
		saveDefaultConfig();
	}

	public static MengDB get() {
		return mengDB;
	}

	private static void setMengDB(MengDB mengDB) {
		MengDB.mengDB = mengDB;
	}
}
