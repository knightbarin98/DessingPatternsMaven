package com.patterns.creational.builder;

import java.util.ArrayList;
import java.util.Collections;

public class Builder {
	
	public static void main(String[] args) {
//		String hello = "hello";
//		System.out.println("<p>" + hello + "</p>");
//		String[] words = { "hello", "world" };
//		StringBuilder sb = new StringBuilder();
//		sb.append("<ul>\n");
//		for (String word : words) {
//			sb.append(String.format("<li>%s</li>\n",word));
//		}
//		sb.append("</ul>");
//		System.out.println(sb);
		
		StringBuilder sb = new StringBuilder();
		sb.append("a").append("b");
		//The reason that string builder its allow to append to an append and continuing appending, it is because it returns
		//an StringBuilder object, the self object to continue building the desire object
		//THIS IS CALL FLUENT INTERFACE
		
		HtmlBuilder builder = new HtmlBuilder("ul");
		//NOT FLUENT
//		builder.addChild("li", "hello");
//		builder.addChild("li", "world");
		//FLUENT
		builder.addChild("li", "hello").addChild("li", "world");
		System.out.println(builder);
	}

}

class HtmlElement{
	public String name, text;
	public ArrayList<HtmlElement> elements = new ArrayList<>();
	private final int indentSize = 2;
	private final String newLine = System.lineSeparator();
	
	public HtmlElement() {
		
	}
	
	public HtmlElement(String name, String text) {
		this.name = name;
		this.text = text;
	}
	
	private String toStringImpl(int indent) {
		StringBuilder sb = new StringBuilder();
		String i = String.join("",Collections.nCopies(indent * indentSize, " "));
		sb.append(String.format("%s<%s>%s", i, name, newLine));
		if(text != null && !text.isEmpty()) {
			sb.append(String.join("",Collections.nCopies((indent+1) * indentSize, " ")))
			.append(text)
			.append(newLine);
		}
		
		for(HtmlElement e : elements) {
			sb.append(e.toStringImpl(indent+1));
		}
		
		sb.append(String.format("%s</%s>%s", i, name, newLine));
		return sb.toString();
	}

	@Override
	public String toString() {
		return toStringImpl(0);
	}
	
}

class HtmlBuilder{
	private String rootName;
	private HtmlElement  root = new HtmlElement();
	
	public HtmlBuilder(String rootName) {
		this.rootName = rootName;
		root.name = rootName;
	}
	
	//NOT FLUENT BUILDER
//	public void addChild(String childName, String childText) {
//		HtmlElement e = new HtmlElement(childName, childText);
//		root.elements.add(e);
//	}
	
	//FLUENT BUILDER
	public HtmlBuilder addChild(String childName, String childText) {
	HtmlElement e = new HtmlElement(childName, childText);
	root.elements.add(e);
	return this;
}

	
	public void clear() {
		root = new HtmlElement();
		root.name = rootName;
	}

	@Override
	public String toString() {
		return root.toString();
	}	
	
}
