package com.solid;

//ISP
//recommendation on how to split interfaces in smaller interfaces
//most atomic and minimum interfaces
//use what you need and do not implement what you won�t use 
public class InterfaceSegregationPrinciple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}


class Document {
	
}

interface Machine {
	void print(Document d);
	void fax(Document d);
	void scan(Document d);
}

class MultifunctionPrinter implements Machine{

	@Override
	public void print(Document d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fax(Document d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scan(Document d) {
		// TODO Auto-generated method stub
		
	}
	
}

class OldFashionPrinter implements Machine{

	@Override
	public void print(Document d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fax(Document d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scan(Document d) {
		// TODO Auto-generated method stub
		
	}
	
}

interface Printer{
	void print(Document d);
}

interface Scanner{
	void scan(Document d);
}

interface Fax{
	void fax(Document d);
}


//YAGNI = YOU AIN�T GOING TO NEED IT

class JustPrinter implements Printer {

	@Override
	public void print(Document d) {
		// TODO Auto-generated method stub
		
	}
	
}

class Photocopier implements Scanner,Printer{

	@Override
	public void print(Document d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scan(Document d) {
		// TODO Auto-generated method stub
		
	}
	
}