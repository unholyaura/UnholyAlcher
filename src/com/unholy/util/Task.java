package com.unholy.util;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;


public abstract class Task extends MethodProvider{
	public Task(MethodContext cxt) {
		super(cxt);
		// TODO Auto-generated constructor stub
	}
	public abstract boolean activate();
	public abstract void execute();
}
