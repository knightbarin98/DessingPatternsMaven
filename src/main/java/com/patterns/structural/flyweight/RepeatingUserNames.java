package com.patterns.structural.flyweight;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//A space optimization technique that let us use less
//memory by storing externally the data associated with
//similar objects.
public class RepeatingUserNames {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User("John Smith");
		User user2 = new User("John Smith");
	}

}

class User{
	private String fullName;
	
	public User(String fullName) {
		this.fullName = fullName;
	}
}

class User2{
	static List<String> strings = new ArrayList<String>();
	private int [] names;
	
	public User2(String fullName) {
		Function<String, Integer> getOrAdd = (String s) -> {
			int index = strings.indexOf(s);
			if(index != -1) {
				return index;
			}
			else {
				strings.add(s);
				return strings.size()-1;
			}
		};
		
		names = Arrays.stream(fullName.split(" "))
				.mapToInt(s -> getOrAdd.apply(s))
				.toArray();
	}
}