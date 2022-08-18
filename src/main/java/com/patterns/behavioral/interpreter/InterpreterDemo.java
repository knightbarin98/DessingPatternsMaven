package com.patterns.behavioral.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Textual input needs to processed. E.g. turned into OOP strcutures
//Some examples: Porgramming languages, html, xml, numeric expressions (3+4/5), regular expressions
//Turning string into OOP strcutures in a complicated process
//A component which process structured text data. Does so by turning it into separate lexical tokens (lexing)
// and then interpretation sequences of said tokens (parsing)
public class InterpreterDemo {

    static ArrayList<Token> lex(String input){
        ArrayList<Token> result = new ArrayList<>();
        for (int i=0;i<input.length();i++){
            switch (input.charAt(i)){
                case'+':
                    result.add(new Token(Token.Type.PLUS,"+"));
                    break;
                case'-':
                    result.add(new Token(Token.Type.MINUS,"-"));
                    break;
                case'(':
                    result.add(new Token(Token.Type.LPAREN,"("));
                    break;
                case')':
                    result.add(new Token(Token.Type.RPAREN,")"));
                    break;
                default:
                    StringBuilder sb = new StringBuilder(""+ input.charAt(i));
                    for(int j = i+1;j  < input.length();j++){
                        if(Character.isDigit(input.charAt(j))){
                            sb.append(input.charAt(j));
                            ++i;
                        }else{
                            result.add(new Token(Token.Type.INTEGER,sb.toString()));
                            break;
                        }

                    }
                break;
            }
        }
        return result;
    }

    static Element parse(List<Token> tokens){
        BinaryOperation result = new BinaryOperation();
        boolean haveLHS = false;
        for(int i = 0; i < tokens.size();i++){
            Token token = tokens.get(i);
            switch (token.type){
                case PLUS:
                    result.type = BinaryOperation.Type.ADDITION;
                    break;
                case MINUS:
                    result.type = BinaryOperation.Type.SUBSTRACTION;
                    break;
                case LPAREN:
                    int j = i;
                    for(; j < tokens.size(); ++j){
                        if(tokens.get(j).type == Token.Type.RPAREN) break;
                    }
                    List<Token> subexpression = tokens.stream().skip(Long.valueOf(i + 1)).limit(Long.valueOf(j - i - 1)).collect(Collectors.toList());
                    Element element = parse(subexpression);
                    if(!haveLHS){
                        result.left = element;
                        haveLHS = true;
                    }else{
                        result.right = element;
                    }
                    i=j;
                    break;
                case RPAREN:
                    break;
                case INTEGER:
                    Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
                    if(!haveLHS){
                        result.left = integer;
                        haveLHS = true;
                    }else{
                        result.right = integer;
                    }
                    break;

            }
        }
        return result;
    }

    public static void main(String[] args) {
        String input = "(13+4)-(12+1)";
        List<Token> tokens = lex(input);
        System.out.println(tokens.stream().map(t -> t.toString()).collect(Collectors.joining("\t")));
        Element element = parse(tokens);
        System.out.println(input + "=" + element.eval());
    }
}

interface Element{
    public int eval();
}

class Integer implements Element{

    private int value;

    Integer(int value){
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class BinaryOperation implements Element{

    public enum Type{
        ADDITION,
        SUBSTRACTION
    }

    public Type type;
    public Element left, right;

    @Override
    public int eval() {
        switch (type){
            case ADDITION:
                return left.eval() + right.eval();
            case SUBSTRACTION:
                return left.eval() - right.eval();
            default:
                return 0;
        }
    }
}
class Token{
    public enum Type{
        INTEGER,
        PLUS,
        MINUS,
        LPAREN,
        RPAREN
    }
    public Type type;
    public String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "`"+text+"`";
    }
}
