package com.patterns.creational.builder;

public class FluentBuilderInheritanceRecursiveGenerics {
	
	public static void main (String [] args) {
		EmployeeBuilder pb = new EmployeeBuilder();
		Person dmitri = pb
				.withName("Dmitri")
				.worksAt("Developer")
				.build();
	}
}


class Person{
	public String name;
	public String position;
	@Override
	public String toString() {
		return "Person [name=" + name + ", position=" + position + "]";
	}
	

}

//recursive generic builder
class PersonBuilder<SELF extends PersonBuilder<SELF>>{
	protected Person person = new Person();
	
	public SELF withName(String name) {
		person.name = name;
		
		//valid option, nevetheless, it will not fit well for the self class Builder
//		return (SELF)this;
		 //the right option would be making and inheritance method the return itself 
		//and applies for the parent class and the subclasses
		return self();
	}
	
	public Person build() {
		return person;
	}
	
	protected SELF self() {
		return (SELF) this;
	}
}

class EmployeeBuilder
	extends PersonBuilder<EmployeeBuilder>{
	
	public EmployeeBuilder worksAt(String position) {
		person.position = position;
		return self();
	}

	@Override
	protected EmployeeBuilder self() {
		// TODO Auto-generated method stub
		return this;
	}
	
	
}