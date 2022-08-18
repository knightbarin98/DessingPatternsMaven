package com.patterns.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

//Motivation
//Components may go in and out of a system at any time
//Chat room participants
//Players in a MMORPG
//It makes sense for them to have direct references to one to another
//But it could be harmed
//Solution: have ten all refer to some central component that facilitates communication
//Definition
//A component that facilitates communications between other components without them be necessarily being aware
//of each other or having direct (reference) access to each other.
//the key is everyone has a reference to everyone, but not a specific reference to a person

//Create the mediator and have each object in the system refer to it
//Mediator engages bidirectional communication with its connected components
//Mediator has functions the components can call
//Components have functions the mediator can call
//Event processing (e.g. Rx) libraries make communication easier to implement
public class MediatorDemo {

    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();
        Person john = new Person("John");
        Person jane = new Person("Jane");
        room.join(john);
        room.join(jane);

        john.say("hi room");
        jane.say("hi john");

        Person simon = new Person("Simon");
        room.join(simon);

        simon.say("hi everyone");
        jane.privateMessage("Simon", "Glad you could join us");
    }
}

class Person{
    public String name;
    public ChatRoom room;
    private List<String> chatLog = new ArrayList<>();

    public Person(String name){
        this.name = name;
    }

    public void receive(String sender, String message){
        String s = sender + ": '" + message + "'";
        System.out.println("["+name+"'s chat session] " + s );
        chatLog.add(s);
    }

    public void say(String message){
        room.broadcast(name, message);
    }

    public void privateMessage(String who, String message){
        room.message(name, who, message);
    }
}

class ChatRoom{
    private List<Person> people = new ArrayList<>();

    public void join(Person p){
        String joinMessage = p.name + " joins the roooms";
        broadcast("room",joinMessage);
        p.room = this;
        people.add(p);
    }

    public void broadcast(String source, String message){
        for (Person person: people){
            if(!person.name.equals(source)){
                person.receive(source, message);
            }
        }
    }

    public void message(String source, String destination, String message){
        people.stream()
                .filter(p -> p.name.equals(destination))
                .findFirst()
                .ifPresent(person -> person.receive(source, message));
    }
}
