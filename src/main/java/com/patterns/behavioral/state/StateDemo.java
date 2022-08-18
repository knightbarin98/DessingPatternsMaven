package com.patterns.behavioral.state;

import java.util.List;

//Consider an ordinary telephone
//What you do with it dependes on the state of the phone/line
//If it is ringing ot you want to make a call, you can pick it up
//Phone must be off the hook to talk/make a call
//If you try calling someone, and it's busy, you put the handset down
//Changes in state can be explicit or in reponse to event (Observer pattern)
//It is up to you how it operates
//A pattern in which the object's behavior is determined by its state. An object transitions from one state to another
//(something needs to trigger a transition)
//A formalized construct which manages state and transitions is called a state machine
//Given sufficient complexity, it plays to formally define possible states and event/triggers
//Can define:
//State entry/exit behaviors
//Action when a particular event causes a transition
//Guard conditions enabling/disabling a transition

public class StateDemo {
    public static void main(String[] args) {
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.on();
        lightSwitch.off();
        lightSwitch.off();
    }
}

class State{
    public void on(LightSwitch ls){
        System.out.println("Light is already on");
    }

    public void off(LightSwitch ls){
        System.out.println("Light is already off");
    }
}


class OnState extends State{

    public OnState(){
        System.out.println("Light turned on");
    }

    @Override
    public void off(LightSwitch ls) {
        System.out.println("Switching light off...");
        ls.setState(new OffSate());
    }
}

class OffSate extends State{
    public OffSate(){
        System.out.println("Light turned off");
    }

    @Override
    public void on(LightSwitch ls) {
        System.out.println("Switching light on...");
        ls.setState(new OnState());
    }
}
class LightSwitch{
    private State state; //OnState OffState

    public LightSwitch(){
        state = new OffSate();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void on(){
        state.on(this);
    }
    public void off(){
        state.off(this);
    }
}