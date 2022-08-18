package com.patterns.behavioral.nullobject;


import java.lang.reflect.Proxy;

//MOtivation
//When component A uses component B, it typically asume that B is non-null
//You inject B, not some Optional<B> type
//You do not check for null on every call
//There is no option of telling A not to use an instance of B
//Thus, we build a no-op, non-functioning inheritor of B (or some interface that B implements) and pass it into A
//A no-op object that conforms to the required interface, satisfying a dependency requirement of some other object
//Implement the required interface
//Rewrite the methods with empty bodies
//If method is non-void, return default value for a given type
//If these values are ever used, you are in trouble
//Supply an instance of Null Object in place of actual object
public class NullObjectDemo {

    @SuppressWarnings("unchecked")
    public static <T> T noOp(Class<T> itf){
        return (T) Proxy.newProxyInstance(
                itf.getClassLoader(),
                new Class<?>[] {itf},
                (proxy, method, args)->{
                    if(method.getReturnType().equals(Void.TYPE)){
                        return null;
                    }else{
                        return method.getReturnType().getConstructor().newInstance();
                    }
                }
        );
    }

    public static void main(String[] args) {
        //ConsoleLog consoleLog = new ConsoleLog();
        //BankAccount account = new BankAccount(consoleLog);
        //Log log = new NullLog();
        Log log = noOp(Log.class);
        BankAccount account = new BankAccount(log);


        account.deposit(100);
    }
}

interface Log{
    public void info(String msg);
    public void warn(String msg);
}


//If a class is marked as final then no class can inherit any feature from the final class
final class NullLog implements Log{

    @Override
    public void info(String msg) {

    }

    @Override
    public void warn(String msg) {

    }
}

class ConsoleLog implements Log{

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg) {
        System.out.println("WARNING "+msg);
    }
}

class BankAccount{
    private Log log;
    private int balance;
    public BankAccount(Log log){
        this.log = log;
    }

    public void deposit(int amount){
        balance += amount;
        log.info("Deposited "+amount);
    }
}
