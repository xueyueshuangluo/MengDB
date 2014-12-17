package com.mengcraft.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mengcraft.db.util.com.google.gson.JsonIOException;
import com.mengcraft.db.util.com.google.gson.JsonObject;
import com.mengcraft.db.util.com.google.gson.JsonParser;
import com.mengcraft.db.util.com.google.gson.JsonSyntaxException;

public class TableManager {

	private static final TableManager MANAGER = new TableManager();
	private final Map<String, MengTable> tables = new ConcurrentHashMap<String, MengTable>();

	private TableManager() {
		File dir = new File("mengdb");
		if (dir.exists() && dir.isDirectory()) {
			System.out.println("Workspace is exists!");
		} else if (dir.exists()) {
			System.out.println("Create Workspace!");
			dir.mkdir();
		} else {
			System.out.println("Create Workspace!");
			dir.mkdir();
		}
	}

	public static TableManager getManager() {
		return MANAGER;
	}

	public MengTable getTable(String name) {
		if (this.tables.containsKey(name)) {
			// System.out.println("TableManage.getTable.Exist");
		} else {
			// System.out.println("TableManage.getTable.NotExist");
			initTable(name);
		}
		return this.tables.get(name);
	}

	public synchronized void saveTable(String name) {
		if (this.tables.containsKey(name)) {
			new Thread(new SaveTask(name, this.tables.get(name).toString())).start();
		}
	}

	private synchronized void initTable(String name) {
		if (this.tables.containsKey(name)) {
			// System.out.println("TableManage.initTable.Exist");
		} else {
			// System.out.println("TableManage.initTable.NotExist");
			File file = new File(new File("mengdb"), name);
			if (file.exists()) {
				parseFile(file);
			} else {
				this.tables.put(name, new DefaultTable(new JsonObject()));
			}
		}
	}

	private void parseFile(File file) {
		try {
			FileReader in = new FileReader(file);
			BufferedReader reader = new BufferedReader(in);
			JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
			this.tables.put(file.getName(), new DefaultTable(object));
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
			File file = new File(new File("mengdb"), this.name);
			try {
				FileWriter out = new FileWriter(file);
				BufferedWriter writer = new BufferedWriter(out);
				writer.write(this.data);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
