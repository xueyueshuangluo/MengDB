package com.mengcraft.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class TableManager {
	private static final TableManager MANAGER = new TableManager();
	private final Map<String, MengTable> tables;

	private TableManager() {
		this.tables = new HashMap<String, MengTable>();
	}

	public static TableManager getManager() {
		return MANAGER;
	}

	public MengTable getTable(String name) {
		synchronized (getTables()) {
			if (getTables().containsKey(name)) {
				System.out.println("TableManage.getTable.Exist");
				return getTables().get(name);
			} else {
				System.out.println("TableManage.getTable.NotExist");
				initTable(name);
				getTable(name);
			}
			return getTables().get(name);
		}
	}

	private void initTable(String name) {
		// File file = new File(MengDB.get().getDataFolder(), name);
		File file = new File(name);

		if (file.exists()) {
			try {
				FileInputStream stream = new FileInputStream(file);
				InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
				JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
				getTables().put(name, new DefaultMengTable(object));
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
			getTables().put(name, new DefaultMengTable(new JsonObject()));
		}
	}

	public void saveTable(String name) {
		if (getTables().containsKey(name)) {
			new Thread(new SaveTask(name, getTables().get(name).toString())).start();
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
			// File file = new File(MengDB.get().getDataFolder(), getName());
			File file = new File(getName());

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
