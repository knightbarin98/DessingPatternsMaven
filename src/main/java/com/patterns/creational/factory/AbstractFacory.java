package com.patterns.creational.factory;

import org.javatuples.Pair;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AbstractFacory {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HotDrinkMachine machine = new HotDrinkMachine();
		HotDrink drink = machine.makeDrink();
		drink.consume();
	}

}

interface HotDrink {
	void consume();
}

class Tea implements HotDrink {

	@Override
	public void consume() {
		// TODO Auto-generated method stub
		System.out.println("This tea is delicious");
	}

}

class Coffe implements HotDrink {

	@Override
	public void consume() {
		// TODO Auto-generated method stub
		System.out.println("This coffee is delicious");
	}

}

interface HotDrinkFactory {
	HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory {

	@Override
	public HotDrink prepare(int amount) {
		// TODO Auto-generated method stub
		System.out.println("Put in the tea, boil wate, pour," + amount + " ml, add lemon and enjoy !");
		return new Tea();
	}

}

class CoffeFactory implements HotDrinkFactory {

	@Override
	public HotDrink prepare(int amount) {
		// TODO Auto-generated method stub
		System.out.println("Grind some beans, boil water, pour," + amount + " ml, add cream and sugar and enjoy !");
		return new Coffe();
	}

}

class HotDrinkMachine {
	List<Pair<String, HotDrinkFactory>> namedFactories = new ArrayList<>();

	public HotDrinkMachine() throws Exception {
		Set<Class<? extends HotDrinkFactory>> types = new Reflections("").getSubTypesOf(HotDrinkFactory.class);
		for (Class<? extends HotDrinkFactory> type : types) {
			namedFactories.add(new Pair<>(type.getSimpleName().replace("Factory", ""),
					type.getDeclaredConstructor().newInstance()));
		}
	}

	public HotDrink makeDrink() throws Exception {
		System.out.println("Available drinks");
		for (int index = 0; index < namedFactories.size(); index++) {
			Pair<String, HotDrinkFactory> item = namedFactories.get(index);
			System.out.println("" + index + ": " + item.getValue0());
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String s;
			int i, amount;
			if ((s = reader.readLine()) != null && (i = Integer.parseInt(s)) >= 0 && i < namedFactories.size()) {
				System.out.println("Specify amount");
				s = reader.readLine();
				if(s != null && (amount = Integer.parseInt(s)) > 0) {
					return namedFactories.get(i).getValue1().prepare(amount);
				}
			}
			
			System.out.println("Incorrect input, try again!");
		}
	}
}