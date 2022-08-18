package com.patterns.behavioral.visitor;

//Double dispatch approach
//Cyclic dependency
//Tight composition
public class ClassicVisitorDemo {
    public static void main(String[] args) {
        // 1 + (2+3)
        AddtionExpressionClassic e = new AddtionExpressionClassic(
                new DoubleExpresionClassic(1), new AddtionExpressionClassic(
                new DoubleExpresionClassic(2),
                new DoubleExpresionClassic(3)
        ));

        ExpresionPrinterClassic ep = new ExpresionPrinterClassic();
        ep.visit(e);
        System.out.println(ep);

        ExpressionCalculator ec = new ExpressionCalculator();
        ec.visit(e);
        System.out.println(ep + " = " + ec.result);
    }
}

interface ExpressionVisitor{
    public void visit(DoubleExpresionClassic e);
    public void visit(AddtionExpressionClassic e );
}

abstract class ExpressionClassic{
    public abstract void accept(ExpressionVisitor visitor);
}

class DoubleExpresionClassic extends ExpressionClassic{
    public double value;

    public DoubleExpresionClassic(double value) {
        this.value = value;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

class AddtionExpressionClassic extends ExpressionClassic{
    public ExpressionClassic left, right;

    public AddtionExpressionClassic(ExpressionClassic left, ExpressionClassic right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

class ExpresionPrinterClassic implements ExpressionVisitor{

    private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpresionClassic e) {
        sb.append(e.value);
    }

    @Override
    public void visit(AddtionExpressionClassic e) {
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

class ExpressionCalculator implements ExpressionVisitor{

    public double result;

    @Override
    public void visit(DoubleExpresionClassic e) {
        result = e.value;
    }

    @Override
    public void visit(AddtionExpressionClassic e) {
        e.left.accept(this);
        double a = result;
        e.right.accept(this);
        double b = result;
        result = a + b;
    }
}

