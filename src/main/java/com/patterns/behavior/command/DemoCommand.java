package com.patterns.behavior.command;

import com.google.common.collect.Lists;

import java.util.List;

//An object or combination of objects which represents an
//instruction to perform a particular action.
//Contains all the information necessary for the action to be taken.
public class DemoCommand {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankAccount ba = new BankAccount();
		System.out.println(ba);
		
		List<BankAccountCommand> commands = List.of(
				new BankAccountCommand(ba,BankAccountCommand.Action.DEPOSIT,100),
				new BankAccountCommand(ba,BankAccountCommand.Action.WITHDRAW,1000)
				);
		
		for(Command command: commands) {
			command.call();
			System.out.println(ba);
		}
		
		Lists.reverse(commands);
		for(Command command : commands) {
			command.undo();
			System.out.println(ba);
		}
	}

}

class BankAccount {
	private int balance;
	private int overdraftLimit = -500;

	public void deposit(int amount) {
		balance += amount;
		System.out.println("Deposited " + amount + ", balance is now " + balance);
	}

	public boolean withDraw(int amount) {
		if (balance - amount >= overdraftLimit) {
			balance -= amount;
			System.out.println("Withdraw " + amount + ", balance is now " + balance);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "BankAccount [balance=" + balance + ", overdraftLimit=" + overdraftLimit + "]";
	}

}

interface Command {
	void call();
	void undo();
}

class BankAccountCommand implements Command {

	private BankAccount account;
	private boolean succeeded;
	
	public enum Action {
		DEPOSIT, WITHDRAW
	}

	private Action action;
	private int amount;

	public BankAccountCommand(BankAccount account, Action action, int amount) {
		super();
		this.account = account;
		this.action = action;
		this.amount = amount;
	}

	@Override
	public void call() {
		switch (action) {
		case DEPOSIT:
			succeeded = true;
			account.deposit(amount);
			break;
		case WITHDRAW:
			succeeded = account.withDraw(amount);
			break;
		}

	}

	@Override
	public void undo() {
		
		if(!succeeded) return;
		
		switch (action) {
		case DEPOSIT:
			account.withDraw(amount);
			break;
		case WITHDRAW:
			account.deposit(amount);
			break;
		}

	}

}
