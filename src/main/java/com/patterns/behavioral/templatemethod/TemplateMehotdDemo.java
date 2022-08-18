package com.patterns.behavioral.templatemethod;


//Provide high level blueprints to be completed by its inheritors
//Motivation
//Algorithms can be decomposed into common parts + specifics
//Strategy pattern does this through composition
//High-level algorithm uses an interface
//Concrete implementations implement the interface
//Template Method does the same thing through inheritance
//Overall algorithm make use of abstract member
//Inheritors override the abstract members
//Allow us to define the 'skeleton' of the algorithm, with concrete implementations defined in subclasses
//Define an algorithm at a high level
//Define constituent parts as abstract methods/properties
//Inherit the algorithm class, providing necessary overrides
public class TemplateMehotdDemo {
    public static void main(String[] args) {
        new Chess().run();
    }
}

abstract class Game{
    protected int currentPlayer;
    protected final int numberOfPlayer;

    public Game(int numberOfPlayer){
        this.numberOfPlayer = numberOfPlayer;
    }

    public void run(){
        start();
        while(!haveWinner()){
            turnTurn();
        }
        System.out.println("Player " + getWinningPlayer() + " wins");
    }

    protected abstract int getWinningPlayer();
    protected abstract void turnTurn();
    protected abstract boolean haveWinner();
    protected abstract void start();
}

class Chess extends Game{

    private int maxTurns = 10;
    private int currentTurn = 1;

    public Chess(){
        super(2);
    }
    @Override
    protected int getWinningPlayer() {
        return 0;
    }

    @Override
    protected void turnTurn() {
        System.out.println("Turn " + (currentTurn++) + " taken by player " + currentPlayer);
        currentPlayer = (currentPlayer + 1) % numberOfPlayer;
    }

    @Override
    protected boolean haveWinner() {
        return currentTurn == maxTurns;
    }

    @Override
    protected void start() {
        System.out.println("Starting a game of chess");
    }
}
