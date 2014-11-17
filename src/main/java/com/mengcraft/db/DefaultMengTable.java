package com.mengcraft.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.mengcraft.db.util.com.google.gson.JsonElement;
import com.mengcraft.db.util.com.google.gson.JsonObject;

public class DefaultMengTable implements MengTable {

	private final JsonObject object;

	public DefaultMengTable(JsonObject object) {
		this.object = object;
	}

	@Override
	public void insert(MengRecord record) {
		if (getObject().has(record.getUid())) {
			getObject().add(UUID.randomUUID().toString(), record.getObject());
		} else {
			getObject().add(record.getUid(), record.getObject());
		}
	}

	@Override
	public void update(MengRecord record) {
		getObject().add(record.getUid(), record.getObject());
	}

	@Override
	public List<MengRecord> find() {
		List<MengRecord> list = new ArrayList<MengRecord>();
		Set<Entry<String, JsonElement>> entrys = getObject().entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			list.add(new DefaultMengRecord(entry.getKey(), entry.getValue().getAsJsonObject()));
		}
		return list;
	}

	@Override
	public List<MengRecord> find(String key, String value) {
		List<MengRecord> list = new ArrayList<MengRecord>();
		Set<Entry<String, JsonElement>> entrys = getObject().entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			JsonObject o = entry.getValue().getAsJsonObject();
			if (o.has(key) && o.get(key).getAsString().equals(value)) {
				list.add(new DefaultMengRecord(entry.getKey(), o));
			}
		}
		return list;
	}

	@Override
	public List<MengRecord> find(String key, Number value) {
		List<MengRecord> list = new ArrayList<MengRecord>();
		Set<Entry<String, JsonElement>> entrys = getObject().entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			JsonObject o = entry.getValue().getAsJsonObject();
			JsonElement e = o.get(key);
			if (e != null && isElement(e, value)) {
				list.add(new DefaultMengRecord(entry.getKey(), o));
			}
		}
		return list;
	}

	@Override
	public MengRecord findOne(String key, String value) {
		Set<Entry<String, JsonElement>> entrys = getObject().entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			JsonObject o = entry.getValue().getAsJsonObject();
			if (o.has(key) && o.get(key).getAsString().equals(value)) {
				return new DefaultMengRecord(entry.getKey(), o);
			}
		}
		return null;
	}

	@Override
	public void delete(MengRecord record) {
		getObject().remove(record.getUid());
	}

	@Override
	public void delete(List<MengRecord> records) {
		for (MengRecord record : records) {
			delete(record);
		}
	}

	private boolean isElement(JsonElement e, Number value) {
		if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber()) {
			Number n = e.getAsJsonPrimitive().getAsNumber();
			if (n instanceof Integer) {
				if (value instanceof Integer && n.equals(value)) {
					return true;
				}
			} else if (n instanceof Long) {
				if (value instanceof Long && n.equals(value)) {
					return true;
				}
			} else if (n instanceof Double) {
				if (value instanceof Double && n.equals(value)) {
					return true;
				}
			} else if (n instanceof Float) {
				if (value instanceof Float && n.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	private JsonObject getObject() {
		return object;
	}

	@Override
	public String toString() {
		return getObject().toString();
	}

}
