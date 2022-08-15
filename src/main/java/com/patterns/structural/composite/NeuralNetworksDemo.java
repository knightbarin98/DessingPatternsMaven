package com.patterns.structural.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class NeuralNetworksDemo {
	public static void main (String [] args) {
		Neuron n1 = new Neuron();
		Neuron n2 = new Neuron();
		NeuronLayer l1 = new NeuronLayer();
		NeuronLayer l2 = new NeuronLayer();
		
		
		n1.connectTo(n2);
		n1.connectTo(l1);
		l1.connectTo(n1);
		l1.connectTo(l2);
	}
}

interface SomeNeurons extends Iterable<Neuron>{
	default void connectTo(SomeNeurons other) {
		if(this == other ) return;
		for(Neuron from : this ) {
			for(Neuron to : other) {
				from.out.add(to);
				to.in.add(from);
			}
		}
	}
}

class Neuron implements SomeNeurons{
	
	public ArrayList<Neuron> in, out;
	
	
	
	@Override
	public void forEach(Consumer<? super Neuron> action) {
		// TODO Auto-generated method stub
		action.accept(this);
	}


	@Override
	public Spliterator<Neuron> spliterator() {
		// TODO Auto-generated method stub
		return Collections.singleton(this).spliterator();
	}



	@Override
	public Iterator<Neuron> iterator() {
		return Collections.singleton(this).iterator();
	}
	
//	public void connectTo(Neuron other) {
//		out.add(other);
//		other.in.add(this);
//	}
}

class NeuronLayer extends ArrayList<Neuron> implements SomeNeurons{
	
}