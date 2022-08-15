package com.patterns.creational.singleton;

import java.io.*;

class DemoBasicSingleton {

	static void saveToFile(BasicSingleton singleton, String filename) throws Exception {
		try (FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(singleton);
		}
	}

	static BasicSingleton readFromFile(String filename) throws Exception {
		try (FileInputStream fileIn = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return (BasicSingleton) in.readObject();
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// Problems
		// 1. reflection
		// 2.serialization
//		BasicSingleton bs = BasicSingleton.getInstance();
//		bs.setValue(3);
		BasicSingleton singleton = BasicSingleton.getInstance();
		singleton.setValue(111);

		String filename = "src/singleton.bin";
		saveToFile(singleton, filename);

		singleton.setValue(222);

		BasicSingleton singleton2 = readFromFile(filename);

		System.out.println(singleton == singleton2);
		System.out.println(singleton.getValue());
		System.out.println(singleton2.getValue());

	}

}


//serialization check
class BasicSingleton implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private BasicSingleton() {

	}

	private static final BasicSingleton INSTANCE = new BasicSingleton();

	public static BasicSingleton getInstance() {
		return INSTANCE;
	}

	private int value = 0;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	protected Object readResolve() {
		return INSTANCE;
	}
}
