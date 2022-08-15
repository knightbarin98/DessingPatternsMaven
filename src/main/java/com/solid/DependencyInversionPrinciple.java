package com.solid;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//A. High-level modules shot not depends on low level modules. Both should depend on abstraction
//B. Abstraction should not depend on details. Details should depend on abstraction.
public class DependencyInversionPrinciple {

	public static void main(String[] args) {
		Person parent = new Person("John");
		Person child1 = new Person("Matt");
		Person child2 = new Person("Chris");

		Relationships relationships = new Relationships();
		relationships.addParentAndChild(parent, child1);
		relationships.addParentAndChild(parent, child2);

		new Research(relationships);
	}

}

enum Relationship {
	PARENT, CHILD, SIBLING
}

class Person {
	public String name;

	public Person(String name) {
		this.name = name;
	}

}

class Relationships implements RelationshipBrowser { // low-level module because is related to storage
	private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

	public List<Triplet<Person, Relationship, Person>> getRelations() {
		return relations;
	}

	public void setRelations(List<Triplet<Person, Relationship, Person>> relations) {
		this.relations = relations;
	}

	public void addParentAndChild(Person parent, Person child) {
		relations.add(new Triplet<>(parent, Relationship.PARENT, child));
		relations.add(new Triplet<>(child, Relationship.CHILD, parent));
	}

	@Override
	public List<Person> findAllChildren(String name) {
		return relations.stream()
				.filter(x -> Objects.equals(x.getValue0().name, name) && x.getValue1() == Relationship.PARENT)
				.map(Triplet::getValue2).collect(Collectors.toList());
	}
}

//SOLUTION
//SHOULD DEPEND ON ABSTRACTION
interface RelationshipBrowser {
	List<Person> findAllChildren(String name);
}

//HIGH LEVEL SHOULD NOT DEPEND ON LOW-LEVEL MODULES
class Research { // high-level because allows to do some actions with the low-level

//	public Research(Relationships relationships) {
//
//		List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
//		relations.stream().filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
//				.forEach(ch -> System.out.println("John has a child called " + ch.getValue2().name));
//	}

	public Research(RelationshipBrowser browser) {
		List<Person> children = browser.findAllChildren("John");

		for (Person child : children) {
			System.out.println("John has a child called " + child.name);
		}
	}
}