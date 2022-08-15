package com.patterns.creational.singleton;

public class MonostateDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChiefExecutiveOfficer ceo = new ChiefExecutiveOfficer();
		ceo.setAge(55);
		ceo.setName("Adam Smith");
		
		ChiefExecutiveOfficer ceo2 = new ChiefExecutiveOfficer();
		
		System.out.println(ceo2);
	}

}


//the idea of monostate implementation is that if you want
//that this class to become a singleton
//you leave everything as it is just change the data storage as static
class ChiefExecutiveOfficer{
	
	private static String name;
	private static int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		ChiefExecutiveOfficer.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		ChiefExecutiveOfficer.age = age;
	}
	
	@Override
	public String toString() {
		return "ChiefExecutiveOfficer [name=" + name + ", age=" + age + "]";
	}
	
	
}