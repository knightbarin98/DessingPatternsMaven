package com.patterns.behavioral.strategy;


import java.util.List;

//Motivation
//Many algorithms can be decomposed into higher-and-lower-level parts
//Making tea can be decomposed into
//The process of making a hot beverage (boli water, pour into cup)
//Tea-specific things (put teabag into water)
//The high level algorithm can then be reused for making coffee or hot chocolate
//Enables the exact behavior of a system to be selected either at runtime (dynamic) or
//compile time (static)
//Also known as a policy
//Define an algorithm at a high level
//DEfine the interface you expect each straegy to follow
//Provide for either dynamic or static composition of strategy in the overall algorithm
public class StrategyDemo {
    public static void main(String[] args) {
        TextProcessor tp = new TextProcessor(OutputFormat.MARKDOWN);
        tp.appendList(List.of("liberte","egalite","fraternite"));
        System.out.println(tp);

        tp.clear();
        tp.setOutputFormat(OutputFormat.HTML);
        tp.appendList(List.of("inheritance","encapsulation","polymorphism"));
        System.out.println(tp);
    }
}

enum OutputFormat{
    MARKDOWN,
    HTML
}

interface ListStrategy{
    default void start(StringBuilder sb){}
    default void end(StringBuilder sb){}
    public void addListItem(StringBuilder sb, String item);

}

class MarkdownListStrategy implements ListStrategy{

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append("*")
                .append(item)
                .append(System.lineSeparator());
    }
}

class HtmlStrategy implements ListStrategy{

    @Override
    public void start(StringBuilder sb) {
        sb.append("<ul>").append(System.lineSeparator());

    }

    @Override
    public void end(StringBuilder sb) {
        sb.append("</ul>").append(System.lineSeparator());
    }

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append("<il>")
                .append(item)
                .append("</il>")
                .append(System.lineSeparator());
    }
}

class TextProcessor{
    private StringBuilder sb = new StringBuilder();
    private ListStrategy listStrategy;

    public TextProcessor(OutputFormat format){
        setOutputFormat(format);
    }

    public void setOutputFormat(OutputFormat format){
        switch (format){
            case MARKDOWN:
                listStrategy = new MarkdownListStrategy();
                break;
            case HTML:
                listStrategy = new HtmlStrategy();
                break;
        }
    }

    public void appendList(List<String> items){
        listStrategy.start(sb);
        for(String item: items){
            listStrategy.addListItem(sb,item);
        }
        listStrategy.end(sb);
    }

    public void clear(){
        sb.setLength(0);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
