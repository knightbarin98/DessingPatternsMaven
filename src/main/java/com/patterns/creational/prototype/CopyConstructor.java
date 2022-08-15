package com.patterns.creational.prototype;

public class CopyConstructor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmployeeCopy john = new EmployeeCopy("John",new AddressCopy("123 London Road","London","England"));
		
		//Employee chris = john
		EmployeeCopy chris = new EmployeeCopy(john);
		
		chris.name = "Chris";
		System.out.println(john);
		System.out.println(chris);
	}

}


class AddressCopy{
	public String streetAddress, city, country;

	public AddressCopy(String streetAddress, String city, String country) {
		super();
		this.streetAddress = streetAddress;
		this.city = city;
		this.country = country;
	}
	
	public AddressCopy(AddressCopy other) {
		this(other.streetAddress,other.city,other.country);
	}

	@Override
	public String toString() {
		return "Address [streetAddress=" + streetAddress + ", city=" + city + ", country=" + country + "]";
	}
	
	
}


class EmployeeCopy{
	public String name;
	public AddressCopy address;
	
	public EmployeeCopy(String name, AddressCopy address) {
		super();
		this.name = name;
		this.address = address;
	}
	
	public EmployeeCopy(EmployeeCopy other) {
		name = other.name;
		address = new AddressCopy(other.address);
	}

	@Override
	public String toString() {
		return "EmployeeCopy [name=" + name + ", address=" + address + "]";
	}
	
	
}