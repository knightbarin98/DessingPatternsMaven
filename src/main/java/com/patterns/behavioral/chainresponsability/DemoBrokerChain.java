package com.patterns.behavioral.chainresponsability;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

//Command Query Separation
//Command = asking for an action or change (e.g please set your attack value to 2)
//Query = asking for information (e.g. please give me your attack value)
//CQS = having separate means of sending commands and queries to, e.g. direct field access

//chainres + observer + mediator +(-) memento
public class DemoBrokerChain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game = new Game();
		CreatureBroker goblin = new CreatureBroker(game, "Strong Goblin",2,2);
		
		System.out.println(goblin);
		
		IncreaseDefenseModifierBroker icm = new IncreaseDefenseModifierBroker(game,goblin);
		
		try(DoubleAttackModifierBroker dam = new DoubleAttackModifierBroker(game, goblin)){
			System.out.println(goblin);
		}
		
		System.out.println(goblin);
	}

}

//CQS
//Why do we have consumer ?
//We want to layer the query operation for creature attack or defense
//into an event that gets handled to whatever modifier actually wants to apply itself
//to a creature.
class Event<Args>{
	
	private int index = 0; //key to the map to find the consumer
	private Map<Integer, Consumer<Args>> handlers = new HashMap();
	
	public int subscribe(Consumer<Args> handler) {
		int i = index;
		handlers.put(index++, handler);
		return i;
	}
	
	public void unsuscribe(int key) {
		handlers.remove(key);
	}
	
	//this is how you fire an event in every single consumer
	public void fire(Args args) {
		for(Consumer<Args> handler : handlers.values()) {
			handler.accept(args);
		}
	}
}

class Query {
	public String creatureName;
	enum Argument{
		ATTACK,DEFENSE
	}
	public Argument argument;
	public int result;
	
	public Query(String creatureName, Argument argument, int result) {
		super();
		this.creatureName = creatureName;
		this.argument = argument;
		this.result = result;
	}
	
}

//Mediator
//It is what we want to make a central
//location with where the query event is actually kept
//We have a central location where any modifier can subscribe itself to queries
//on the creature and modify the creature's attack or defense value.
class Game {
	
	public Event<Query> queries = new Event();
	
}

class CreatureBroker{
	
	public Game game;
	public String name;
	public int baseAttack, baseDefense;
	
	public CreatureBroker(Game game, String name, int baseAttack, int baseDefense) {
		super();
		this.game = game;
		this.name = name;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
	}
	
	public int getAttack() {
		Query query = new Query(name,Query.Argument.ATTACK, baseAttack);
		game.queries.fire(query);
		return query.result;
	}
	
	public int getDefense() {
		Query query = new Query(name,Query.Argument.DEFENSE, baseDefense);
		game.queries.fire(query);
		return query.result;
	}

	@Override
	public String toString() {
		return "Creature [game=" + game + ", name=" + name + ", attack=" + getAttack() + ", defense="
				+ getDefense() + "]";
	}
	
}


class CreatureModifierBroker{
	protected Game game;
	protected CreatureBroker creature;
	
	public CreatureModifierBroker(Game game, CreatureBroker creature) {
		this.game = game;
		this.creature = creature;
	}
}


class DoubleAttackModifierBroker extends CreatureModifierBroker implements AutoCloseable{
	
	private int token;
	public DoubleAttackModifierBroker(Game game, CreatureBroker creature) {
		super(game, creature);
		// TODO Auto-generated constructor stub
		
		token = game.queries.subscribe(q ->{
			if(q.creatureName.equals(creature.name)
					&& q.argument == Query.Argument.ATTACK) {
				q.result *=2;
			}
		});
	}
	@Override
	public void close() {
		game.queries.unsuscribe(token);
	}
	
}

class IncreaseDefenseModifierBroker extends CreatureModifierBroker{
	
	private int token;
	public IncreaseDefenseModifierBroker(Game game, CreatureBroker creature) {
		super(game, creature);
		// TODO Auto-generated constructor stub
		token = game.queries.subscribe(q ->{
			if(q.creatureName.equals(creature.name)
					&& q.argument == Query.Argument.DEFENSE) {
				q.result +=3;
			}
		});
	}
	
}
