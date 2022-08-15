
package com.patterns.structural.decorator;

import java.util.function.Supplier;

public class StaticDecoratorCompisitionDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ColoredShapedStaticDecorator<SquareStaticDecorator> blueSquare = new ColoredShapedStaticDecorator<>(
				() -> new SquareStaticDecorator(20), "blue");
		System.out.println(blueSquare.info());

		TransparentShapeStaticDecorator<ColoredShapedStaticDecorator<CircleStaticDecorator>> tshape = new TransparentShapeStaticDecorator<>(
				() -> new ColoredShapedStaticDecorator<>(() -> new CircleStaticDecorator(10), "red"), 50);

		System.out.println(tshape.info());
	}

}

interface ShapeStaticDecorator {
	String info();
}

class CircleStaticDecorator implements ShapeStaticDecorator {
	private float radius;

	public CircleStaticDecorator() {

	}

	public CircleStaticDecorator(float radius) {
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


class SquareStaticDecorator implements ShapeStaticDecorator {
	private float side;

	public SquareStaticDecorator() {

	}

	public SquareStaticDecorator(float side) {
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

class ColoredShapedStaticDecorator<T extends ShapeStaticDecorator> implements ShapeStaticDecorator {

	private ShapeStaticDecorator shape;
	private String color;

	public ColoredShapedStaticDecorator(Supplier<? extends T> ctor, String color) {
		shape = ctor.get();
		this.color = color;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return shape.info() + " has a color " + color;
	}

}

class TransparentShapeStaticDecorator<T extends ShapeStaticDecorator> implements ShapeStaticDecorator {

	private ShapeStaticDecorator shape;
	private int transparency;

	public TransparentShapeStaticDecorator(Supplier<? extends T> ctor, int transparency) {
		shape = ctor.get();
		this.transparency = transparency;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}

}