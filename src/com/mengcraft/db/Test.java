package com.mengcraft.db;

public class Test {
	public static void main(String[] args) {
		new TestTask("A").start();
		new TestTask("B").start();
		new TestTask("C").start();
		new TestTask("D").start();
		new TestTask("E").start();
		new ChectTask().start();
	}
}

class TestTask extends Thread {

	private final String value;

	public TestTask(String value) {
		this.value = value;
	}

	@Override
	public void run() {
		long before = System.currentTimeMillis();
		MengTable table = TableManager.getManager().getTable("Test");
		MengRecord record = new MengBuilder().getEmptyRecord();
		record.put("Test", value);
		table.insert(record);
		long after = System.currentTimeMillis();
		System.out.println("Time: " + (after - before));
	}
}

class ChectTask extends Thread {
	@Override
	public void run() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MengTable table = TableManager.getManager().getTable("Test");
		System.out.println("After: " + table.find().size());
	}
}