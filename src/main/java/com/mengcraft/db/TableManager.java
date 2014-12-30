package com.mengcraft.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mengcraft.db.local.DefaultTable;
import com.mengcraft.db.util.com.google.gson.JsonIOException;
import com.mengcraft.db.util.com.google.gson.JsonObject;
import com.mengcraft.db.util.com.google.gson.JsonParser;
import com.mengcraft.db.util.com.google.gson.JsonSyntaxException;

public class TableManager {

	private static final TableManager MANAGER = new TableManager();
	private final ConcurrentMap<String, MengTable> tables;
	private final ExecutorService pool = Executors.newSingleThreadExecutor();

	private TableManager() {
		this.tables = new ConcurrentHashMap<String, MengTable>();
	}

	public static TableManager getManager() {
		return MANAGER;
	}

	public MengTable getTable(String name) {
		if (getTables().containsKey(name)) {
			// System.out.println("TableManage.getTable.Exist");
		} else {
			// System.out.println("TableManage.getTable.NotExist");
			initTable(name);
		}
		return getTables().get(name);
	}

	private synchronized void initTable(String name) {
		if (getTables().containsKey(name)) {
			// System.out.println("TableManage.initTable.Exist");
		} else {
			// System.out.println("TableManage.initTable.NotExist");
			File file = new File(MengDB.get().getDataFolder(), name + ".json");
			if (file.exists()) {
				try {
					FileInputStream stream = new FileInputStream(file);
					InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
					JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
					getTables().put(name, new DefaultTable(object));
				} catch (JsonIOException e) {
					e.printStackTrace();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				getTables().put(name, new DefaultTable(new JsonObject()));
			}
		}
	}

	public void saveTable(String name) {
		if (getTables().containsKey(name)) {
//			new Thread(new SaveTask(name, getTables().get(name).toString())).start();
			this.pool.execute(new SaveTask(name, getTables().get(name).toString()));
		}
	}

	private Map<String, MengTable> getTables() {
		return tables;
	}

	private class SaveTask implements Runnable {
		private final String name;
		private final String data;

		public SaveTask(String name, String data) {
			this.name = name;
			this.data = data;
		}

		@Override
		public void run() {
			File file = new File(MengDB.get().getDataFolder(), getName() + ".json");
			try {
				FileOutputStream out = new FileOutputStream(file);
				OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
				writer.write(getData());
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private String getName() {
			return name;
		}

		private String getData() {
			return data;
		}
	}
}
