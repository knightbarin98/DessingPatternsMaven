package com.patterns.behavioral.iterator;

import org.javatuples.valueintf.IValue0;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class ArrayBackedPropertiesDemo {
    public static void main(String[] args) {
        Creature creature = new Creature();
        creature.setAgility(12);
        creature.setStrength(17);
        creature.setIntelligence(13);
        System.out.println(
                "Creature has a max stat of "+ creature.max()
                + " total stats = " + creature.sum()
                + " average stats = " + creature.average()
        );
    }
}

class SimpleCreature{
    private int strength, agility, intelligence;

    public int max(){
        return Math.max(strength, Math.max(agility, intelligence));
    }

    public int sum(){
        return strength+agility+intelligence;
    }

    public double average(){
        return sum()/3.0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intellegence) {
        this.intelligence = intellegence;
    }
}


class Creature implements Iterable<Integer>{

    private int [] stats = new int[3];

    public int getStrenth(){
        return stats[0];
    }

    public void setStrength(int value){
        stats[0] = value;
    }

    public void setAgility(int value){
        stats[1] = value;
    }

    public int getAgility(){
        return stats[1];
    }

    public void setIntelligence(int value){
        stats[2] = value;
    }

    public int getIntelligence(){
        return stats[2];
    }


    public int sum(){
        return IntStream.of(stats).sum();
    }

    public int max(){
        return IntStream.of(stats).max().getAsInt();
    }

    public double average(){
        return IntStream.of(stats).average().getAsDouble();
    }


    @Override
    public Iterator<Integer> iterator() {
        return IntStream.of(stats).iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        for(int n: stats)
            action.accept(n);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return IntStream.of(stats).spliterator();
    }
}
