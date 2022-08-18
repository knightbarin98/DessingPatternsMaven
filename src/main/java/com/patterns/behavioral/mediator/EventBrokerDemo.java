package com.patterns.behavioral.mediator;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import java.util.ArrayList;
import java.util.List;

public class EventBrokerDemo {

    public static void main(String[] args) {
        EventBroker broker = new EventBroker();
        FootballPlayer jones = new FootballPlayer(broker, "jones");
        FootbalCoach coach = new FootbalCoach(broker);

        jones.score();
        jones.score();
        jones.score();
    }
}

class EventBroker extends Observable<Integer>{

    private List<Observer<? super Integer>> observers = new ArrayList<>();

    @Override
    protected void subscribeActual(@NonNull Observer<? super Integer> observer) {
        observers.add(observer);
    }

    public void publish(int n){
        for(Observer<? super Integer> observer : observers){
            //It is actually how you put something on the reactor ascensions pipeline
            observer.onNext(n);
        }
    }
}

class FootballPlayer{
    private int goalScored = 0;
    private EventBroker broker;
    public String name;

    public FootballPlayer(EventBroker broker, String name) {
        this.broker = broker;
        this.name = name;
    }

    public void score(){
        broker.publish(++goalScored);
    }
}

class FootbalCoach{

    public FootbalCoach(EventBroker broker){
        broker.subscribe(i -> {
           System.out.println("Hey, you scored "+ i + " goals!");
        });
    }
}

