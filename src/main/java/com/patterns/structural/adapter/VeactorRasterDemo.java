package com.patterns.structural.adapter;

import java.util.*;
import java.util.function.Consumer;



//sometimes when you build a adapter it will create temporary objects
//in order to avoid you should cache the objects that the adapter create 
public class VeactorRasterDemo {

	private static List<VectorObject> vectorObjects = new ArrayList<>(
			List.of(new VectorRectangle(1, 1, 10, 10), new VectorRectangle(3, 3, 6, 6)));

	public static void drawPoint(Point p) {
		System.out.println(".");
	}
	
	
	private static void draw() {
		for(VectorObject vo : vectorObjects) {
			for(Line line: vo) {
				LineToPointAdapter adapter = new LineToPointAdapter(line);
				adapter.forEach(VeactorRasterDemo::drawPoint);
			}
		}
	}
	
	public static void main(String[] args) {
		
	}
}

class Point {
	public int x, y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

}

class Line {
	public Point start, end;

	public Line(Point start, Point end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public int hashCode() {
		int result = start.hashCode();
		result = 31 * result + end.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		return Objects.equals(end, other.end) && Objects.equals(start, other.start);
	}
	
	

}

class VectorObject extends ArrayList<Line> {

}

class VectorRectangle extends VectorObject {
	public VectorRectangle(int x, int y, int width, int height) {
		add(new Line(new Point(x, y), new Point(x + width, y)));
		add(new Line(new Point(x + width, y), new Point(x + width, y + height)));
		add(new Line(new Point(x, y), new Point(x, y + height)));
		add(new Line(new Point(x, y + height), new Point(x + width, y + height)));
	}
}

class LineToPointAdapter implements Iterable<Point>{

	private static int count = 0;
	private static Map<Integer, List<Point>> cache = new HashMap<>();
	private int hash;

	public LineToPointAdapter(Line line) {
		
		hash = line.hashCode();
		
		if(cache.get(hash) != null) return;
		
		System.out.println(String.format("%d: Generating points for line {%d, %d} - {%d,%d} {no caching}", ++count,
				line.start.x, line.start.y, line.end.x, line.end.y));
		
		List<Point> points = new ArrayList<>();
		
		int left = Math.min(line.start.x, line.end.x);
		int right = Math.max(line.start.x, line.end.x);
		int top = Math.min(line.start.y, line.end.y);
		int bottom = Math.max(line.start.y, line.end.y);
		int dx = right - left;
		int dy = line.end.y - line.start.y;

		if (dx == 0) {
			for (int y = top; y <= bottom; ++y) {
				points.add(new Point(left, y));
			}
		} else if (dy == 0) {
			for (int x = left; x <= right; ++x) {
				points.add(new Point(x, top));
			}
		}
		
		cache.put(hash, points);
	}

	@Override
	public Iterator<Point> iterator() {
		// TODO Auto-generated method stub
		return cache.get(hash).iterator();
	}

	@Override
	public void forEach(Consumer<? super Point> action) {
		// TODO Auto-generated method stub
		cache.get(hash).forEach(action);;
	}

	@Override
	public Spliterator<Point> spliterator() {
		// TODO Auto-generated method stub
		return cache.get(hash).spliterator();
	}
	
	
}