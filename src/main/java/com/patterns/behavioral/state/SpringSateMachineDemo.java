package com.patterns.behavioral.state;


import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class SpringSateMachineDemo {

    public static StateMachine<SpringState, EventStateMachine> buildMachine() throws Exception{
        StateMachineBuilder.Builder<SpringState, EventStateMachine> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(SpringState.OFF_HOOK)
                .states(EnumSet.allOf(SpringState.class));

        builder.configureTransitions()
                .withExternal()
                .source(SpringState.OFF_HOOK)
                .event(EventStateMachine.CALL_DIALED)
                .target(SpringState.CONNECTING)
                .and()
                .withExternal()
                .source(SpringState.OFF_HOOK)
                .event(EventStateMachine.STOP_USING_PHONE)
                .target(SpringState.ON_HOOK)
                .and()
                .withExternal()
                .source(SpringState.CONNECTING)
                .event(EventStateMachine.HUNG_UP)
                .target(SpringState.OFF_HOOK)
                .and()
                .withExternal()
                .source(SpringState.CONNECTING)
                .event(EventStateMachine.CALL_CONNECTED)
                .target(SpringState.CONNECTED)
                .and()
                .withExternal()
                .source(SpringState.CONNECTED)
                .event(EventStateMachine.LEFT_MESSAGE)
                .target(SpringState.OFF_HOOK)
                .and()
                .withExternal()
                .source(SpringState.CONNECTED)
                .event(EventStateMachine.HUNG_UP)
                .target(SpringState.OFF_HOOK)
                .and()
                .withExternal()
                .source(SpringState.CONNECTED)
                .event(EventStateMachine.PLACED_ON_HOLD)
                .target(SpringState.OFF_HOOK)
                .and()
                .withExternal()
                .source(SpringState.ON_HOLD)
                .event(EventStateMachine.TAKEN_OFF_HOLD)
                .target(SpringState.CONNECTED)
                .and()
                .withExternal()
                .source(SpringState.ON_HOLD)
                .event(EventStateMachine.HUNG_UP)
                .target(SpringState.OFF_HOOK);

        return builder.build();
    }

    public static void main(String[] args) {
        try {
            StateMachine<SpringState, EventStateMachine> machine = buildMachine();
            machine.start();

            SpringState exitState = SpringState.OFF_HOOK;

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            while(true){
                State<SpringState, EventStateMachine> state = machine.getState();
                System.out.println("The phone is currently " + state.getId());
                System.out.println("Select a trigger: ");

                List<Transition<SpringState,EventStateMachine>> ts = machine.getTransitions()
                        .stream()
                        .filter(t -> t.getSource() == state)
                        .collect(Collectors.toList());

                for(int i =0; i< ts.size(); ++i){
                    System.out.println(" " + i + ". " + ts.get(i).getTrigger().getEvent());
                }

                boolean parseOk;
                int choice = 0;
                do{
                    try{
                        System.out.println("Please enter your choice: ");
                        choice = Integer.parseInt(console.readLine());
                        parseOk = choice >= 0 && choice < ts.size();
                    }catch (Exception e){
                        parseOk = false;
                    }
                }while (!parseOk);

                //Perfom transition
                machine.sendEvent(ts.get(choice).getTrigger().getEvent());

                if(machine.getState().getId() == exitState)
                    break;
            }
            System.out.println("And we are done!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

enum SpringState{
    OFF_HOOK, //starting
    ON_HOOK,   //terminal
    CONNECTING,
    CONNECTED,
    ON_HOLD
}

enum EventStateMachine{
    CALL_DIALED,
    HUNG_UP,
    CALL_CONNECTED,
    PLACED_ON_HOLD,
    TAKEN_OFF_HOLD,
    LEFT_MESSAGE,
    STOP_USING_PHONE
}
