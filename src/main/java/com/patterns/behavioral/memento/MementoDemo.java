package com.patterns.behavioral.memento;


//Keep some sort of token, which then allows you to return an object to a particular stage
//Motivation
//An object or system goes through changes: E.g a bank account
//There are different ways of navigating those changes
//One way is to record every change (Command) and teach a command to "undo" itself
//Another is to simple save snapshots of the system
//A token/handle representing the system state. Lets us roll back to the state when the token was generated. May
//or may not directly expose state information
//Memento are used to roll back states
//A memento is simply a token/handle class with (tipically) no function of its own
//A memento is not required to expose directly the state to which it reverts the system
//Can be used to implement undo/redo
public class MementoDemo {
    public static void main(String[] args) {
        BankAccountMemento ba = new BankAccountMemento(100);
        Memento m1 = ba.deposit(50); //150
        Memento m2 = ba.deposit(25); //175
        System.out.println(ba);

        //restore to m1
        ba.restore(m1);
        System.out.println(ba);

        ///restore to m2
        ba.restore(m2);
        System.out.println(ba);

    }
}

//Memento o token bank account
//The memento is a snapshot of the internal state of the bank account
class Memento{
    public int balance;

    public Memento(int balance) {
        this.balance = balance;
    }
}

class BankAccountMemento{
    private int balance;

    public BankAccountMemento(int balance) {
        this.balance = balance;
    }

    public Memento deposit(int amount){
        balance += amount;
        return new Memento(balance);
    }

    public void restore(Memento memento){
        balance = memento.balance;
    }

    @Override
    public String toString() {
        return "BankAccountMemento{" +
                "balance=" + balance +
                '}';
    }
}
