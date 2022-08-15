package com.patterns.structural.proxy;


//A class that functions as an interface to a particular resource.
//That resource may be remote, expensive to construct, or may require
//logging or some other added functionality

//Sme interface, different behaivor
//Avoid to change code
//Comunication proxy

//Protection Proxy protects from access from a different class

//Proxy vs Decorator
//Proxy provides an identical interface, and a decorator provides an enhanced interface

public class ProtectionProxy {
	
	public static void main(String [] args) {
		Car car = new Car(new Driver(12));
		car.drive();
		
		Car car2 = new CarProxy(new Driver(12));
		car2.drive();
	}
}

interface Drivable{
	void drive();
}

class Car implements Drivable{
	protected Driver driver;
	
	public Car (Driver driver) {
		this.driver = driver;
	}

	@Override
	public void drive() {
		// TODO Auto-generated method stub
		System.out.println("This car has been driven.");
	}
}

class Driver{
	public int age;
	
	public Driver(int age) {
		this.age = age;
	}
}

class CarProxy extends Car{
	public CarProxy(Driver driver) {
		super(driver);
	}
	
	@Override
	public void drive() {
		if(driver.age >= 16) {
			super.drive();
		}else {
			System.out.println("Driver too young");
		}
	}
}
