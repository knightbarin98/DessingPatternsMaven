package com.patterns.creational.factory;

public class FactoryMethod {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Point point = Point.newCartesianPoint(2, 3);
	}

}

class Point {
	private double x, y;

	private Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//FactoryMethod
	
//	public static Point newCartesianPoint(double x, double y) {
//		return new Point(x, y);
//	}
//
//	public static Point newPolarPoint(double rho, double theta) {
//		return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
//	}
	
	public static class Factory{
		public static Point newCartesianPoint(double x, double y) {
			return new Point(x, y);
		}

		public static Point newPolarPoint(double rho, double theta) {
			return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
		}
	}
	
}


//not optimal solution
//enum CoordinatesSystem{
//	CARTESIAN,
//	POLAR
//}
//
//class Point{
//	private double x,y;
//	
//	public Point(double a, double b, CoordinatesSystem cs) {
//		switch(cs) {
//		case CARTESIAN:
//			y = a;
//			x = b;
//			break;
//		case POLAR:
//			x = a * Math.cos(b);
//			y = a * Math.sin(b);
//			break;
//			
//		}
//	}
//	
//	public Point(double x, double y) {
//		this.x = x;
//		this.y = y;
//	}
//	
//	//you want to to initialize the point in another way
//	//which is illegal
//	//the way you can do it it by using enum or constants
//	public Point(double rho, double theta) {
//		x = rho * Math.cos(theta);
//		y = rho * Math.sin(theta);
//	}
//}
