package com.patterns.structural.proxy;


//Log for every statement
public class PropertyProxy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Creature creature = new Creature();
		creature.setAgility(10);
	}

}

class Creature{
	
	private Property<Integer> agility = new Property<>(10);
	
	public void setAgility(int value) {
		agility.setValue(value);
	}
	
	public int getAgility() {
		return agility.getValue();
	}
	
	/*private int agility;
	
	public Creature() {
		
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}*/
	
	
}

class Property<T>{
	private T value;
	
	public Property (T value) {
		this.value = value;
	}
	public T getValue() {
		//logging statement
		return value;
	}
	public void setValue(T value) {
		//logging statement
		this.value = value;
	}
	
	public boolean equals(Object obj) {
		
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		
		Property<?> property = (Property<?>) obj;
		
		return value != null ? value.equals(property.value) : false;
	}
	
	
}
