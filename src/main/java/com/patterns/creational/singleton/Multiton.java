package com.patterns.creational.singleton;

import java.util.HashMap;

public class Multiton {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Printer main = Printer.get(Subsystem.PRIMARY);
		Printer aux = Printer.get(Subsystem.AUXILIARY);
		Printer aux2 = Printer.get(Subsystem.AUXILIARY);
	}

}

enum Subsystem {
	PRIMARY, AUXILIARY, FALLBACK
}

class Printer {

	private static int instanceCount = 0;

	private Printer() {
		instanceCount++;
		System.out.println("Instances created so far: " + instanceCount);
	}

	private static HashMap<Subsystem, Printer> instances = new HashMap<>();

	public static Printer get(Subsystem ss) {
		if (instances.containsKey(ss))
			return instances.get(ss);

		Printer instance = new Printer();
		instances.put(ss, instance);
		return instance;
	}
}