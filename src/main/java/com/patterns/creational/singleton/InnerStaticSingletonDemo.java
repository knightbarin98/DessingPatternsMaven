package com.patterns.creational.singleton;

public class InnerStaticSingletonDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}


class InnerStaticSingleton{
	private InnerStaticSingleton() {
		
	}
	
	private static class Impl{
		private static final InnerStaticSingleton instance = new InnerStaticSingleton();
	}
	
	
	public InnerStaticSingleton getInstance() {
		return Impl.instance;
	}
}