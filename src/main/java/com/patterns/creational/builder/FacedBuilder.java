package com.patterns.creational.builder;

public class FacedBuilder {

	public static void main(String[] args) {
		FacedPersonBuilder pb = new FacedPersonBuilder();
		FacedPerson person = pb
				.lives()
				.at("123 London road")
				.in("London")
				.withPoscode("00000")
				.works()
				.at("Fabrikam")
				.asA("Engineer")
				.earning(123000)
				.build();
		
		System.out.println(person);

	}

}

class FacedPerson {
	// address
	public String streetAddress, postcode, city;

	// employment
	public String companyName, position;
	public int annualIncome;

	@Override
	public String toString() {
		return "Person [streetAddress=" + streetAddress + ", postcode=" + postcode + ", city=" + city + ", companyName="
				+ companyName + ", position=" + position + ", annualIncome=" + annualIncome + "]";
	}

}

//builder facade
class FacedPersonBuilder {
	protected FacedPerson person = new FacedPerson();
	
	public PersonAddressBuilder lives() {
		return new PersonAddressBuilder(person);
	}
	
	public PersonJobBuilder works() {
		return new PersonJobBuilder(person);
	}
	
	public FacedPerson build() {
		return person;
	}
}

class PersonAddressBuilder extends FacedPersonBuilder {
	public PersonAddressBuilder(FacedPerson person) {
		this.person = person;
	}

	public PersonAddressBuilder at(String streetAddress) {
		person.streetAddress = streetAddress;
		return this;
	}
	
	public PersonAddressBuilder withPoscode(String postcode) {
		person.postcode = postcode;
		return this;
	}
	
	public PersonAddressBuilder in(String city) {
		person.city = city;
		return this;
	}
}

class PersonJobBuilder extends FacedPersonBuilder {
	public PersonJobBuilder(FacedPerson person) {
		this.person = person;
	}

	public PersonJobBuilder at(String companyName) {
		person.companyName = companyName;
		return this;
	}
	
	public PersonJobBuilder asA(String position) {
		person.position = position;
		return this;
	}
	
	public PersonJobBuilder earning(int annualIncome) {
		person.annualIncome = annualIncome;
		return this;
	}
}
