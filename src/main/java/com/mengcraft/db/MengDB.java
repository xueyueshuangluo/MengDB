package com.mengcraft.db;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.mengcraft.db.local.DefaultManager;

public class MengDB extends JavaPlugin {
	public static MengManager getManager() {
		return DefaultManager.getManager();
	}

	@Override
	public void onLoad() {
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
}
