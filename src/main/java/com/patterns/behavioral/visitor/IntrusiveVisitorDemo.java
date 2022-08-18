package com.patterns.behavioral.visitor;

//Motivation
//Need to define a new operation on an entire class hierarchy
//make a document model printable to HTML/Markdown
//Do not want to keep modifying every class in the hierarchy
//Need acces to the non-common aspects of classes in the hierarchy
//Create an external component to handle rendering
//But avoid type checks
//A pattern where component (visitor) is allowed to traverse the entire inheritance hierarchy.
// Implemented by propagating a single visit() method throughout the entire hierarchy.
//Propagate an accept(Visitor v) method throughout the entire hierarchy
//Create a visitor with visit(Foo), visit(Bar), ... for each element in the hierarchy
//Each accept() simply calls visitor.visit(this)
//Acyclic visitor allows greater flexibility at a cost performance
public class IntrusiveVisitorDemo {
    public static void main(String[] args) {
        // 1 + (2+3)
        AddtionExpression e = new AddtionExpression(new DoubleExpresion(1), new AddtionExpression(
                new DoubleExpresion(2),
                new DoubleExpresion(3)
        ));

        StringBuilder sb = new StringBuilder();
        e.print(sb);
        System.out.println(sb);
    }
}

abstract class Expression{
    //It is intrusive because we add methods an action from here
    //When we add the method we had to implemented them in every where making more difficult to add more
    //It breaks the open close principle and the single responsibility principle
    public abstract void print(StringBuilder sb);
}

class DoubleExpresion extends Expression{
    private double value;

    public DoubleExpresion(double value) {
        this.value = value;
    }

    @Override
    public void print(StringBuilder sb) {
        sb.append(value);
    }
}

class AddtionExpression extends Expression{
    private Expression left, right;

    public AddtionExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(StringBuilder sb) {
        sb.append("(");
        left.print(sb);
        sb.append("+");
        right.print(sb);
        sb.append(")");
    }
}
