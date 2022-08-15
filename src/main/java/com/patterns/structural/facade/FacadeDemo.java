package com.patterns.structural.facade;

import java.util.ArrayList;
import java.util.List;

public class FacadeDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Here we have everything we need to construct a console,
		//But the user it is not interesting in the process,
		//it will be more easier for them to just render the console
		//through an API
		Buffer buffer = new Buffer(30,20);
		Viewport vp = new Viewport(buffer,30,20,0,0);
		Console console = new Console(30,20);
		console.addViewport(vp);
		console.render();
		
		//Example of Facade
		Console.newConsole(30, 20).render();
	}

}

class Buffer{
	private char [] characters;
	private int lineWidth;
	
	public Buffer(int lineHeight, int lineWidth) {
		this.lineWidth = lineWidth;
		characters = new char[lineHeight * lineWidth];
	}
	
	public char charAt(int x, int y) {
		return characters[y*lineWidth+x];
	}
}

class Viewport{
	
	private final Buffer buffer;
	private int width;
	private int height;
	private int offSetX;
	private int offSetY;
	
	public Viewport(Buffer buffer, int width, int height, int offSetX, int offSetY) {
		this.buffer = buffer;
		this.width = width;
		this.height = height;
		this.offSetX = offSetX;
		this.offSetY = offSetY;
	}
	
	public char charAt	( int x, int y) {
		return this.buffer.charAt(x+offSetX, y+offSetY);
	}
}

class Console {
	private List<Viewport> viewports = new ArrayList<Viewport>();
	int width, height;
	
	public Console(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void addViewport(Viewport viewport) {
		viewports.add(viewport);
	}
	
	public static Console newConsole(int width, int height) {
		Buffer buffer = new Buffer(width, height);
		Viewport viewport = new Viewport(buffer, width,height, 0,0);
		Console console = new Console(width, height);
		console.addViewport(viewport);
		return console;
	}
	
	public void render() {
		for(int y=0; y < height; ++y) {
			for(int x = 0; x < width; ++x) {
				for(Viewport vp: viewports) {
					System.out.println(vp.charAt(x, y));
				}
				System.out.println();
			}
		}
	}
}