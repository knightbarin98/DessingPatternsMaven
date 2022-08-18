package com.patterns.behavioral.strategy;


import java.util.List;
import java.util.function.Supplier;

public class StaticStrategyDemo {
    public static void main(String[] args) {
        TextProcessorStatic<MarkdownListStrategyStatic> tp = new TextProcessorStatic<>(MarkdownListStrategyStatic::new);
        tp.appendList(List.of("alpha","beta","gamma"));
        System.out.println(tp);
        TextProcessorStatic<HtmlStrategyStatic> tp2 = new TextProcessorStatic<>(HtmlStrategyStatic::new);
        tp2.appendList(List.of("alpha","beta","gamma"));
        System.out.println(tp2);
    }
}

enum OutputFormatStatic{
    MARKDOWN,
    HTML
}

interface ListStrategyStatic{
    default void start(StringBuilder sb){}
    default void end(StringBuilder sb){}
    public void addListItem(StringBuilder sb, String item);

}

class MarkdownListStrategyStatic implements ListStrategyStatic{

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append("*")
                .append(item)
                .append(System.lineSeparator());
    }
}

class HtmlStrategyStatic implements ListStrategyStatic{

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

class TextProcessorStatic<LS extends ListStrategyStatic>{
    private StringBuilder sb = new StringBuilder();
    private LS listStrategy;

    public TextProcessorStatic(Supplier<? extends ListStrategyStatic> ctor){
        listStrategy = (LS) ctor.get();
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
