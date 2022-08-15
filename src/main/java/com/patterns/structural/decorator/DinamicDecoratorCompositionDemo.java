package com.patterns.structural.decorator;


public class DinamicDecoratorCompositionDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Circle circle = new Circle(10);
		System.out.println(circle.info());

		ColorShaped squareBlue = new ColorShaped(new Square(20), "blue");

		System.out.println(squareBlue.info());

		TransparentShape tshape = new TransparentShape(new ColorShaped(new Circle(5), "green"), 5);
		System.out.println(tshape.info());
	}

}

interface Shape {
	String info();
}

class Circle implements Shape {
	private float radius;

	public Circle() {

	}

	public Circle(float radius) {
		this.radius = radius;
	}

	void resize(float factor) {
		this.radius *= factor;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return "A circle of radius " + radius;
	}

}

class Square implements Shape {
	private float side;

	public Square() {

	}

	public Square(float side) {
		this.side = side;
	}

	void resize(float factor) {
		this.side *= factor;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return "A square of length " + side;
	}

}

class ColorShaped implements Shape {
	private Shape shape;
	private String color;

	public ColorShaped(Shape shape, String color) {
		super();
		this.shape = shape;
		this.color = color;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return shape.info() + " has the color " + color;
	}
}

class TransparentShape implements Shape {
	private Shape shape;
	private int transparency;

	public TransparentShape(Shape shape, int transparency) {
		super();
		this.shape = shape;
		this.transparency = transparency;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return shape.info() + " has transparency %" + transparency;
	}

}