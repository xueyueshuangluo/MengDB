package com.mengcraft.db;

import com.mengcraft.db.local.DefaultMengRecord;

public class MengBuilder {
	public MengRecord getEmptyRecord() {
		return new DefaultMengRecord();
	}
}
