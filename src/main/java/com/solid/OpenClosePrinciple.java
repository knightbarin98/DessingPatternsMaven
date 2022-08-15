package com.solid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//OCP + specification (design patterns)
//object from OCP is to be open for extension, but close for modification 
public class OpenClosePrinciple {

	public static void main(String[] args) {
		Product apple = new Product("APPLE", Color.GREEN, Size.SMALL);
		Product tree = new Product("TREE", Color.GREEN, Size.LARGE);
		Product house = new Product("HOUESE", Color.BLUE, Size.LARGE);

		List<Product> products = new ArrayList<>();

		products.add(apple);
		products.add(tree);
		products.add(house);

		ProductFilter filter = new ProductFilter();
		System.out.println("Green product(s) :");
		filter.filterByColor(products, Color.GREEN).forEach(p -> System.out.println(p.name + " is Green"));

		BetterFilter bfilter = new BetterFilter();
		System.out.println("Green product(s) new :");
		bfilter.filter(products, new ColorSpecification(Color.GREEN))
				.forEach(p -> System.out.println(p.name + " is Green"));

		System.out.println("Large blue items");
		bfilter.filter(products,
				new AndSpecification<Product>(new ColorSpecification(Color.BLUE), new SizeSpecification(Size.LARGE)))
				.forEach(p -> System.out.println(p.name + " is blue and large"));
		;
	}

}

enum Color {
	RED, BLUE, GREEN
}

enum Size {
	SMALL, MEDIUM, LARGE, YUGE
}

class Product {
	public String name;
	public Color color;
	public Size size;

	public Product(String name, Color color, Size size) {
		this.name = name;
		this.color = color;
		this.size = size;
	}
}

// VIOLATES DE OCP PRINCIPLE BY GOING TO THE CLASS TO MODIFY ITS PURPOUSE EVERY
// TIME IS NEED A CHANGE
class ProductFilter {
	public Stream<Product> filterByColor(List<Product> products, Color color) {
		return products.stream().filter(p -> p.color == color);
	}

	public Stream<Product> filterBySize(List<Product> products, Size size) {
		return products.stream().filter(p -> p.size == size);
	}

	public Stream<Product> filterByColorAndSize(List<Product> products, Size size, Color color) {
		return products.stream().filter(p -> p.size == size && p.color == color);
	}
}

interface Specification<T> {
	// Satisfied specify criteria
	boolean isSatisfied(T item);
}

interface Filter<T> {
	Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Product> {

	private Color color;

	public ColorSpecification(Color color) {
		this.color = color;
	}

	@Override
	public boolean isSatisfied(Product item) {
		// TODO Auto-generated method stub
		return item.color == color;
	}

}

class SizeSpecification implements Specification<Product> {

	private Size size;

	public SizeSpecification(Size size) {
		this.size = size;
	}

	@Override
	public boolean isSatisfied(Product item) {
		// TODO Auto-generated method stub
		return item.size == size;
	}

}

class AndSpecification<T> implements Specification<T> {

	private Specification<T> first, second;

	public AndSpecification(Specification<T> first, Specification<T> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean isSatisfied(T item) {
		// TODO Auto-generated method stub
		return first.isSatisfied(item) && second.isSatisfied(item);
	}

}

class BetterFilter implements Filter<Product> {

	@Override
	public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
		// TODO Auto-generated method stub
		return items.stream().filter(p -> spec.isSatisfied(p));
	}

}
