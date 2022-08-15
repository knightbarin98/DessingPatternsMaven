package com.patterns.creational.singleton;

public class LazinessAndThreadSafety {
	
	public static void main(String [] args) {
		
	}
}


class LazySingleton{
	private static LazySingleton instance;
	
	private LazySingleton() {
		System.out.println("initializing lazzy singleton");
	}
	
	
	//it would not help if we are using multi threads that 
	//access to the method because they will instantiate the object more than once
	//one way to fix it is by add synchronized to the method
//	public static LazySingleton getInstance() {
//	public static synchronized LazySingleton getInstance() {
//		if(instance == null) {
//			instance = new LazySingleton();
//		}
//		
//		return instance;
//	}
	
	//double-check locking 
	//NOTE: outdated
	public static LazySingleton getInstance() {
		if(instance == null) {
			synchronized(LazySingleton.class) {
				if(instance == null) {
					instance = new LazySingleton();
				}
			}
		}
		
		return instance;
	}
}