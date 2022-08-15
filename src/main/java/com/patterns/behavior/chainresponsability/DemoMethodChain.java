package com.patterns.behavior.chainresponsability;


//A chain of components who all get a chance to
//process a command or a query, optionally having
//default processing implementation and an ability
//to terminate the processing chain.
public class DemoMethodChain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Creature goblin = new Creature("Goblin",2,2);
		System.out.println(goblin);
		
		
		CreatureModifier root = new CreatureModifier(goblin);
		
		root.add(new NoBonussesModifier(goblin));
		
		System.out.println("Let's double goblin attack");
		root.add(new DoubleAttackModifier(goblin));
		
		System.out.println("Increase goblin defense");
		root.add(new IncreaseDefenseModifier(goblin));
		
		root.handle();
		System.out.println(goblin);
	}

}


class Creature{
	public String name;
	public int attack, defense;
	
	public Creature(String name, int attack, int defense) {
		super();
		this.name = name;
		this.attack = attack;
		this.defense = defense;
	}

	@Override
	public String toString() {
		return "Creature [name=" + name + ", attack=" + attack + ", defense=" + defense + "]";
	}
	
}

class CreatureModifier{
	protected Creature creature;
	protected CreatureModifier next;
	
	public CreatureModifier(Creature creature) {
		super();
		this.creature = creature;
	}
	
	public void add(CreatureModifier cm) {
		if(next != null) {
			next.add(cm);
		}else {
			next = cm;
		}
	}
	
	public void handle() {
		if(next != null) {
			next.handle();
		}
	}
}

class DoubleAttackModifier extends CreatureModifier{

	public DoubleAttackModifier(Creature creature) {
		super(creature);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		System.out.println("Doubling " + creature + " attack to : " + creature.attack *2);
		creature.attack *= 2;
		super.handle();
	}
	
}

class IncreaseDefenseModifier extends CreatureModifier{

	public IncreaseDefenseModifier(Creature creature) {
		super(creature);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		System.out.println("Increasing " + creature.name + "'s defense");
		creature.defense += 3;
		super.handle();
	}
	
}

class NoBonussesModifier extends CreatureModifier{

	public NoBonussesModifier(Creature creature) {
		super(creature);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		System.out.println("No bonusses for you");
	}
	
	
}