package com.patterns.behavioral.state;

import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HanmadeStateMachineDemo {

    private static Map<HandmadeState, List<Pair<Trigger,HandmadeState>>> rules = new HashMap<>();

    static{
        rules.put(HandmadeState.OFF_HOOK, List.of(
           new Pair<>(Trigger.CALL_DIALED, HandmadeState.CONNECTING),
           new Pair<>(Trigger.STOP_USING_PHONE, HandmadeState.ON_HOOK)
        ));
        rules.put(HandmadeState.CONNECTING, List.of(
                new Pair<>(Trigger.HUNG_UP, HandmadeState.OFF_HOOK),
                new Pair<>(Trigger.CALL_CONNECTED, HandmadeState.CONNECTED)
        ));
        rules.put(HandmadeState.CONNECTED, List.of(
                new Pair<>(Trigger.LEFT_MESSAGE, HandmadeState.OFF_HOOK),
                new Pair<>(Trigger.HUNG_UP, HandmadeState.OFF_HOOK),
                new Pair<>(Trigger.PLACED_ON_HOLD, HandmadeState.ON_HOLD)
        ));
        rules.put(HandmadeState.OFF_HOOK, List.of(
                new Pair<>(Trigger.TAKEN_OFF_HOLD, HandmadeState.CONNECTED),
                new Pair<>(Trigger.HUNG_UP, HandmadeState.OFF_HOOK)
        ));
    }

    private static HandmadeState currentState = HandmadeState.OFF_HOOK;
    private static HandmadeState exit = HandmadeState.ON_HOOK;


    public static void main(String[] args) {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            System.out.println("The phone is currently " + currentState);
            System.out.println("Select triggerr");

            for (int i = 0; i < rules.get(currentState).size(); ++i){
                Trigger trigger = rules.get(currentState).get(i).getValue0();
                System.out.println(""+i+". "+trigger);
            }

            boolean parseOK;
            int choice = 0;
            do{
                try{
                    System.out.println("Please enter your choice: ");
                    choice = Integer.parseInt(console.readLine());
                    parseOK = choice >= 0 && choice < rules.get(currentState).size();
                } catch (IOException e) {
                    parseOK = false;
                }
            }while (!parseOK);

            currentState = rules.get(currentState).get(choice).getValue1();

            if(currentState == exit) break;
        }

        System.out.println("And we are done");
    }
}

enum HandmadeState{
    OFF_HOOK, //starting
    ON_HOOK,   //terminal
    CONNECTING,
    CONNECTED,
    ON_HOLD
}

enum Trigger{
    CALL_DIALED,
    HUNG_UP,
    CALL_CONNECTED,
    PLACED_ON_HOLD,
    TAKEN_OFF_HOLD,
    LEFT_MESSAGE,
    STOP_USING_PHONE
}
