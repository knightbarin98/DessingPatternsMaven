package com.patterns.structural.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//mechanism for treating individual (scalar) objects and composition
//of objects in a uniform manner

//the object of composite pattern is an object which has the ability to be treated
//as singular object or as collection of objects
public class GeometricShapesDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphicObject drawing = new GraphicObject();
		drawing.setName("MyDrawing");
		drawing.children.add(new Circle("Red"));
		drawing.children.add(new Square("Yellow"));
		
		GraphicObject group = new GraphicObject();
		group.setName("BlueGroup");
		group.children.add(new Circle("Blue"));
		group.children.add(new Circle("Blue"));
		group.children.add(new Square("Blue"));
		group.children.add(new Square("Blue"));
		
		drawing.children.add(group);
		
		System.out.println(drawing);
	}

}

class GraphicObject {
	protected String name = "Group";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GraphicObject() {

	}

	public String color;
	public List<GraphicObject> children = new ArrayList<>();

	private void print(StringBuilder sb, int depth) {
		sb.append(String.join("", Collections.nCopies(depth, "*"))).append(depth > 0 ? " " : "")
				.append(color == null || color.isEmpty() ? "" : color + " ").append(getName())
				.append(System.lineSeparator());

		for (GraphicObject child : children)
			child.print(sb, depth + 1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		print(sb, 0);
		return sb.toString();
	}

}

class Circle extends GraphicObject{
	
	public Circle(String color) {
		name = "Circle";
		this.color = color;
	}
}

class Square extends GraphicObject{
	
	public Square (String color) {
		name = "Square";
		this.color = color;
	}
}