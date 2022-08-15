package com.patterns.creational.singleton;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestabilityIssuesDemo {

	public static void main(String[] args) {

	}

}

interface Database {
	int getPopulation(String name);
}

class SingletonDatabase implements Database {

	private Dictionary<String, Integer> capitals = new Hashtable<String, Integer>();
	private static int instanceCount = 0;

	private static int getCount() {
		return instanceCount;
	}

	private SingletonDatabase() {
		instanceCount++;
		System.out.println("Initialiazing database");

		try {
			File f = new File(SingletonDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			Path fullPath = Paths.get(f.getPath(), "capitals.txt");
			List<String> lines = Files.readAllLines(fullPath);

			Iterables.partition(lines, 2).forEach(kv -> capitals.put(kv.get(0).trim(), Integer.parseInt(kv.get(1))));
		} catch (Exception ex) {

		}
	}

	private static final SingletonDatabase INSTANCE = new SingletonDatabase();

	public static SingletonDatabase getInstance() {
		return INSTANCE;
	}

	public int getPopulation(String name) {
		return capitals.get(name);
	}
}

class SingletonRecordFinder {
	public int getTotalPopulation(List<String> names) {
		int result = 0;
		for (String name : names) {
			result += SingletonDatabase.getInstance().getPopulation(name);
		}
		return result;
	}
}

class ConfigurableRecordFinder {
	private Database database;

	public ConfigurableRecordFinder(Database database) {
		this.database = database;
	}

	public int getTotalPopulation(List<String> names) {
		int result = 0;
		for (String name : names) {
			result += database.getPopulation(name);
		}
		return result;
	}

}

class DummyDatabase implements Database {

	private Dictionary<String, Integer> data = new Hashtable<String, Integer>();

	public DummyDatabase() {
		data.put("alpha", 1);
		data.put("beta", 2);
		data.put("gamma", 3);
	}

	@Override
	public int getPopulation(String name) {
		// TODO Auto-generated method stub
		return data.get(name);
	}

}

class Tests {

	@Test // not unit test
	public void singletonTotalPopulationTest() {
		SingletonRecordFinder finder = new SingletonRecordFinder();
		List<String> names = List.of("Seoul", "Mexico City");
		int tp = finder.getTotalPopulation(names);
		assertEquals(17500000 + 17400000, finder);
	}

	@Test
	public void dependetPopulation() {
		DummyDatabase db = new DummyDatabase();
		ConfigurableRecordFinder config = new ConfigurableRecordFinder(db);
		assertEquals(4, config.getTotalPopulation(List.of("alpha", "gamma")));
	}
}