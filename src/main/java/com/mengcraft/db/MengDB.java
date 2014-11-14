package com.mengcraft.db;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class MengDB extends JavaPlugin {
	private static MengDB mengDB;

	@Override
	public void onLoad() {
		setMengDB(this);
		saveDefaultConfig();
	}
	
	@Override
	public void onEnable() {
		try {
			new Metrics(this).start();
		} catch (IOException e) {
			getLogger().log(Level.WARNING, "Can not link to mcstats.org!");
		}
	}

	public static MengDB get() {
		return mengDB;
	}

	private static void setMengDB(MengDB mengDB) {
		MengDB.mengDB = mengDB;
	}
}
