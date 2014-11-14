package com.mengcraft.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.SafeJsonObject;

public class DefaultMengTable implements MengTable {

	private final SafeJsonObject object;

	public DefaultMengTable(SafeJsonObject object) {
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
			SafeJsonObject o = entry.getValue().getAsJsonObject();
			if (o.has(key) && o.get(key).getAsString().equals(value)) {
				list.add(new DefaultMengRecord(entry.getKey(), o));
			}
		}
		return list;
	}

	@Override
	public MengRecord findOne(String key, String value) {
		Set<Entry<String, JsonElement>> entrys = getObject().entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			SafeJsonObject o = entry.getValue().getAsJsonObject();
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

	private SafeJsonObject getObject() {
		return object;
	}

	@Override
	public String toString() {
		return getObject().toString();
	}

}
