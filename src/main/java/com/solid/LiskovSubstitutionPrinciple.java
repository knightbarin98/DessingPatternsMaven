package com.solid;

//LSP
//should be able to substitute a subclass for a base class
public class LiskovSubstitutionPrinciple {
	
	public static void main(String [] args) {
		Rectangle rc = new Rectangle(2,3);
		Demo.userIt(rc);
		Rectangle sq = new Square();
		sq.setWidth(5);
		Demo.userIt(sq);
	}
	
}

class Rectangle {
	
	protected int width, height;
	
	public Rectangle() {
		
	}
	
	public Rectangle(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	 
	public int getArea() {
		return width*height;
	}

	@Override
	public String toString() {
		return "Rectangle [width=" + width + ", height=" + height + "]";
	}
	
}


class Square extends Rectangle{
	
	public Square() {
	}
	
	public Square(int size) {
		width = height = size;
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		super.setWidth(width);
		super.setHeight(width);
	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub
		super.setHeight(height);
		super.setWidth(height);
	}
	
	
}

class Demo {
	
	static void userIt(Rectangle r) {
		int width = r.getWidth();
		r.setHeight(10);
		//area = width * 10
		System.out.println("Expected area of " + (width * 10) + ", got " + r.getArea());
	}
}
