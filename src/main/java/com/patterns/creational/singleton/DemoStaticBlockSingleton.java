package com.patterns.creational.singleton;

import java.io.File;

//The reason of this type of singleton is to avoid constructor exceptions in common singletons
public class DemoStaticBlockSingleton {
	
	public static void main (String [] args) {
		
	}
}

class StaticBlockSingleton{
	
	private StaticBlockSingleton() throws Exception{
		System.out.println("singleton is initializing");
		File.createTempFile(".", ".");
	}
	
	private static StaticBlockSingleton instance;

	//review the use of static block+
	static {
		try {
			instance = new StaticBlockSingleton();
		}catch(Exception ex) {
			System.err.println("failed to create singleton");
		}
	}
	
	

	public static StaticBlockSingleton getInstance() {
		return instance;
	}
}
