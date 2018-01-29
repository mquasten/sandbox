package de.mq.analysis.integration.support;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Lookup;

import de.mq.analysis.integration.RealFunction;

abstract class AbstractRealFunctionJRubyScriptEngineFactory {
	private final String script = "class RealFunctionImpl\ninclude Java::%s\ndef f(x)\n%s\nend\nend\nRealFunctionImpl.new";
	
	
	RealFunction realFunction(final String code) {
		try {
			return newRealFunction(code);
		} catch (ScriptException ex) {
			throw new IllegalArgumentException("Unable to ctreated scripted function", ex);
		}
	}
	
	private RealFunction newRealFunction(final String code) throws ScriptException {
		final ScriptEngine engine = scriptEngine();
		
		System.out.println(engine);
		final Object receiver = engine.eval(String.format(script, RealFunction.class.getName(), code));
		return  (RealFunction) ((Invocable) engine).getInterface(receiver, RealFunction.class);
	}
	
	@Lookup
	abstract ScriptEngine scriptEngine() ;
	

}
