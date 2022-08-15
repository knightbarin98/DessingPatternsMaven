package com.patterns.creational.prototype;

import java.util.Arrays;

public class DontUseCloneable {

	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Person john = new Person(new String[] {"John","Smith"},new AddressCloneable("London Road",123));
		Person jane = (Person) john.clone();
		jane.names[0] = "Jane";
		jane.address.houseNumber = 124;
		
		
		System.out.println(john);
		System.out.println(jane);
	}

}

class AddressCloneable implements Cloneable{
	public String streetName;
	public int houseNumber;
	
	
	public AddressCloneable(String streetName, int houseNumber) {
		super();
		this.streetName = streetName;
		this.houseNumber = houseNumber;
	}


	@Override
	public String toString() {
		return "Address [streetName=" + streetName + ", houseNumber=" + houseNumber + "]";
	}


	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new AddressCloneable(streetName, houseNumber);
	}
	
}


class Person implements Cloneable{
	public String [] names;
	public 	AddressCloneable address;
	
	public Person(String[] names, AddressCloneable address) {
		super();
		this.names = names;
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Person [names=" + Arrays.toString(names) + ", address=" + address + "]";
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new Person(names.clone(), (AddressCloneable) address.clone());
	}
	
}


