package com.patterns.structural.bridge;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;


//a mechanism that decouples an interface (hierarchy) from an implementation (hierarchy)
//a stronger form of encapsulation
public class BridgeDemo {

	public static void main(String[] args) {
//		RasterRenderer raster = new RasterRenderer();
//		VectorRenderer vector = new VectorRenderer();
//		
//		Circle circle = new Circle(vector,5);
//		circle.draw();
//		
//		circle.resize(2);
//		
//		circle.draw();
		
		Injector injector = Guice.createInjector(new ShapeModule());
		Circle instance = injector.getInstance(Circle.class);
		instance.radius = 3;
		instance.draw();
		instance.resize(2);
		instance.draw();
	}

}


interface Renderer{
	void renderCircle(float radius);
}

class VectorRenderer implements Renderer{

	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing a circle of radius "+radius);
		
	}
	
	
}

class RasterRenderer implements Renderer{
	
	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing pixels for a circle of radius "+radius);
		
	}
	
}

abstract class Shape{
	protected Renderer renderer;
	
	public Shape(Renderer renderer) {
		this.renderer = renderer;
	}
	
	public abstract void draw();
	public abstract void resize(float factor);
}

class Circle extends Shape{
	
	
	public float radius; 
	
	@Inject
	public Circle(Renderer renderer) {
		super(renderer);
		// TODO Auto-generated constructor stub
	}
	
	public Circle(Renderer renderer, float radius) {
		 super(renderer);
		 this.radius = radius;
	}

	@Override
	public void draw() {
		renderer.renderCircle(radius);
		
	}

	@Override
	public void resize(float factor) {
		// TODO Auto-generated method stub
		radius *= factor;
	}
	
}

class ShapeModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(Renderer.class).to(VectorRenderer.class);
	}
	
}
