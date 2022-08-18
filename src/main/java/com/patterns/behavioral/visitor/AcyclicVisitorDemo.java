package com.patterns.behavioral.visitor;

public class AcyclicVisitorDemo {
    public static void main(String[] args) {
        // 1 + (2+3)
        AddtionExpressionAcyclic e = new AddtionExpressionAcyclic(
                new DoubleExpresionAcyclic(1), new AddtionExpressionAcyclic(
                new DoubleExpresionAcyclic(2),
                new DoubleExpresionAcyclic(3)
        ));

        ExpresionPrinterAcyclic ep = new ExpresionPrinterAcyclic();
        ep.visit(e);
        System.out.println(ep);

        ExpressionCalculatorAcyclic ec = new ExpressionCalculatorAcyclic();
        ec.visit(e);
        System.out.println(ep + " = " + ec.result);
    }
}

interface VisitorAcyclic {} //marker interface

interface ExpressionVisitorAcyclic extends VisitorAcyclic{
    public void visit(ExpressionAcyclic obj);
}

interface DoubleExpressionVisitorAcyclic extends VisitorAcyclic{
    public void visit(DoubleExpresionAcyclic obj);
}
interface AdditionExpressionVisitorAcyclic extends VisitorAcyclic{
    public void visit(AddtionExpressionAcyclic obj);
}

abstract class ExpressionAcyclic{
    public void accept(VisitorAcyclic visitor){
        if(visitor instanceof ExpressionVisitorAcyclic){
            ((ExpressionVisitorAcyclic)visitor).visit(this);
        }
    }
}

class DoubleExpresionAcyclic extends ExpressionAcyclic{
    public double value;

    public DoubleExpresionAcyclic(double value) {
        this.value = value;
    }

    public void accept(VisitorAcyclic visitor){
        if(visitor instanceof DoubleExpressionVisitorAcyclic){
            ((DoubleExpressionVisitorAcyclic)visitor).visit(this);
        }
    }
}

class AddtionExpressionAcyclic extends ExpressionAcyclic{
    public ExpressionAcyclic left, right;

    public AddtionExpressionAcyclic(ExpressionAcyclic left, ExpressionAcyclic right) {
        this.left = left;
        this.right = right;
    }

    public void accept(VisitorAcyclic visitor){
        if(visitor instanceof AdditionExpressionVisitorAcyclic){
            ((AdditionExpressionVisitorAcyclic)visitor).visit(this);
        }
    }
}

class ExpresionPrinterAcyclic implements DoubleExpressionVisitorAcyclic, AdditionExpressionVisitorAcyclic{

    private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpresionAcyclic e) {
        sb.append(e.value);
    }

    @Override
    public void visit(AddtionExpressionAcyclic e) {
        sb.append("(");
        e.left.accept(this);
        sb.append("+");
        e.right.accept(this);
        sb.append(")");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class ExpressionCalculatorAcyclic implements DoubleExpressionVisitorAcyclic, AdditionExpressionVisitorAcyclic{

    public double result;

    @Override
    public void visit(DoubleExpresionAcyclic e) {
        result = e.value;
    }

    @Override
    public void visit(AddtionExpressionAcyclic e) {
        e.left.accept(this);
        double a = result;
        e.right.accept(this);
        double b = result;
        result = a + b;
    }
}
