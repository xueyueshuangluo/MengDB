package com.mengcraft.db.local;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.mengcraft.db.MengRecord;
import com.mengcraft.db.MengTable;
import com.mengcraft.db.util.com.google.gson.JsonElement;
import com.mengcraft.db.util.com.google.gson.JsonObject;

public class DefaultTable implements MengTable {

	private final JsonObject handle;

	public DefaultTable(JsonObject object) {
		this.handle = object;
	}

	@Override
	public void insert(MengRecord record) {
		if (this.handle.has(record.getUid())) {
			this.handle.add(UUID.randomUUID().toString(), record.getHandle());
		} else {
			this.handle.add(record.getUid(), record.getHandle());
		}
	}

	@Override
	public void update(MengRecord record) {
		this.handle.add(record.getUid(), record.getHandle());
	}

	@Override
	public List<MengRecord> find() {
		List<MengRecord> list = new ArrayList<MengRecord>();
		Set<Entry<String, JsonElement>> entrys = this.handle.entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			list.add(new DefaultRecord(entry.getKey(), entry.getValue().getAsJsonObject()));
		}
		return list;
	}

	@Override
	public List<MengRecord> find(String key) {
		List<MengRecord> list = new ArrayList<MengRecord>();
		Set<Entry<String, JsonElement>> entrys = this.handle.entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			if (entry.getValue().getAsJsonObject().has(key)) {
				list.add(new DefaultRecord(entry.getKey(), entry.getValue().getAsJsonObject()));
			}
		}
		return list;
	}

	@Override
	public List<MengRecord> find(String key, String value) {
		List<MengRecord> list = new ArrayList<MengRecord>();
		Set<Entry<String, JsonElement>> entrys = this.handle.entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			JsonObject o = entry.getValue().getAsJsonObject();
			if (o.has(key) && o.get(key).getAsString().equals(value)) {
				list.add(new DefaultRecord(entry.getKey(), o));
			}
		}
		return list;
	}

	@Override
	public List<MengRecord> find(String key, Number value) {
		List<MengRecord> out = new ArrayList<MengRecord>();
		for (Entry<String, JsonElement> entry : this.handle.entrySet()) {
			JsonObject o = entry.getValue().getAsJsonObject();
			JsonElement e = o.get(key);
			if (e != null && compareElement(e, value) == 0) {
				out.add(new DefaultRecord(entry.getKey(), o));
			}
		}
		return out;
	}

	@Override
	public MengRecord findOne(String key, Number value) {
		Set<Entry<String, JsonElement>> entrys = this.handle.entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			JsonObject o = entry.getValue().getAsJsonObject();
			JsonElement e = o.get(key);
			if (e != null && compareElement(e, value) == 0) {
				return new DefaultRecord(entry.getKey(), o);
			}
		}
		return null;
	}

	@Override
	public MengRecord findOne(String key, String value) {
		Set<Entry<String, JsonElement>> entrys = this.handle.entrySet();
		for (Entry<String, JsonElement> entry : entrys) {
			JsonObject o = entry.getValue().getAsJsonObject();
			if (o.has(key) && o.get(key).getAsString().equals(value)) {
				return new DefaultRecord(entry.getKey(), o);
			}
		}
		return null;
	}

	@Override
	public void delete(MengRecord record) {
		this.handle.remove(record.getUid());
	}

	@Override
	public void delete(List<MengRecord> records) {
		for (MengRecord record : records) {
			delete(record);
		}
	}

	private int compareElement(JsonElement e, Number v) {
		if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber()) {
			BigDecimal one = new BigDecimal(e.toString());
			BigDecimal two = new BigDecimal(v.toString());
			return one.compareTo(two);
		}
		return 2;
	}

	@Override
	public String toString() {
		return this.handle.toString();
	}

	@Override
	public void delete() {
		this.handle.clear();
	}

}
