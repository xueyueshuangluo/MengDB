package com.mengcraft.db;

import java.util.List;

public interface MengTable {
	public void insert(MengRecord record);

	public void update(MengRecord record);

	public List<MengRecord> find();

	public List<MengRecord> find(String key, String value);
	
	public List<MengRecord> find(String key, Number value);

	public MengRecord findOne(String key, String value);

	public void delete(MengRecord record);
	
	public void delete(List<MengRecord> records);
}
