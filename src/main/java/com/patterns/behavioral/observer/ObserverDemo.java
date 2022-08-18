package com.patterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

//We need to be informed when certain things happen
//Object's field changes
//Object does something
//Some external event occurs
//We want to listen events and notified when they occur
///Typical pattern involves addXxxListener()
//Java now has functional objects
//Supplier<T>, Consumer<T>, Function<T>
//An observer is an object that wishes to be informed about events happening
//in the system. The entity generating the events is an observable
//Observer is an intrusive approach: an observable must provide an event to subscribe to
//Special care must be taken to prevent issues in multithreaded scenarios
public class ObserverDemo implements Observer<PersonObserver>{
    public static void main(String[] args) {
        new ObserverDemo();
    }

    public ObserverDemo(){
        PersonObserver person = new PersonObserver();
        person.suscribe(this);
        for (int i = 20; i < 24; ++i){
            person.setAge(i);
        }
    }

    @Override
    public void handle(PropertyChangeEventArgs<PersonObserver> args) {
        System.out.println("Person's " +  args.propertyName + " has changed to " + args.newValue);
    }
}

class PropertyChangeEventArgs<T>{
    public T source;
    public String propertyName;
    public Object newValue;

    public PropertyChangeEventArgs(T source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }
}

//Observer is the interface you expect to be implemented to observing an object of type T
interface Observer<T>{
    public void handle(PropertyChangeEventArgs<T> args);
}

class Observable<T>{
    private List<Observer<T>> observers = new ArrayList<>();
    public void suscribe(Observer<T> observer){
        observers.add(observer);
    }
    protected void propertyChanged(T source, String propertyName, Object value){
        for(Observer<T> o: observers){
            o.handle(new PropertyChangeEventArgs<>(source, propertyName,value));
        }
    }
}

class PersonObserver extends Observable<PersonObserver>{
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (this.age == age) return;
        this.age = age;
        propertyChanged(this,"age",age);
    }
}
