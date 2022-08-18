package com.patterns.behavioral.visitor;

//Issue the code it is slow, because of the recursion and the verification of every class
public class ReflectiveVisitorDemo {
    public static void main(String[] args) {
        // 1 + (2+3)
        AddtionExpressionReflective e = new AddtionExpressionReflective(
                new DoubleExpresionReflective(1), new AddtionExpressionReflective(
                new DoubleExpresionReflective(2),
                new DoubleExpresionReflective(3)
        ));

        StringBuilder sb = new StringBuilder();
        ExpresionPrinter ep = new ExpresionPrinter();
        ep.print(e,sb);
        System.out.println(sb);
    }
}


abstract class ExpressionReflective{
}

class DoubleExpresionReflective extends ExpressionReflective{
    public double value;

    public DoubleExpresionReflective(double value) {
        this.value = value;
    }

}

class AddtionExpressionReflective extends ExpressionReflective{
    public ExpressionReflective left, right;

    public AddtionExpressionReflective(ExpressionReflective left, ExpressionReflective right) {
        this.left = left;
        this.right = right;
    }

}

class ExpresionPrinter{
    public static void print(ExpressionReflective e, StringBuilder sb){
        if(e.getClass() == DoubleExpresionReflective.class){
            sb.append(((DoubleExpresionReflective) e).value);
        }else if(e.getClass() == AddtionExpressionReflective.class){
            AddtionExpressionReflective ae = (AddtionExpressionReflective) e;
            sb.append("(");
            print(ae.left,sb);
            sb.append("+");
            print(ae.right,sb);
            sb.append(")");
        }
    }
}
