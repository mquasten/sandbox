package de.mq.pattern.gof.strategy;

import bsh.EvalError;
import bsh.Interpreter;

public class BshRealFunctionImpl implements RealFunction {

	private final Interpreter interpreter = new Interpreter();
	
	private String function; 
	
	private String var;
	
	
	@SuppressWarnings("unused")
	private BshRealFunctionImpl(){
		
	}
	
	public BshRealFunctionImpl(final String function, final String var){
		this.function=function;
		this.var=var;
		
	}
	
	@Override
	public double result(double value) {
		
		try {
			interpreter.set(var, value);
			return (Double) interpreter.eval(function);
		} catch (EvalError ex) {
			throw new IllegalArgumentException("Unable to calculate result for function " + function   + ex);
		}
		
		
	}
	
	

}
