package de.mq.analysis.integration.support;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import de.mq.analysis.integration.RealFunction;

class RealFunctionJRubyScriptEngineFactory {
	static final String SCRIPT = "class RealFunctionImpl\ninclude Java::%s\ndef f(x)\n%s\nend\nend\nRealFunctionImpl.new";
	private final ScriptEngine scriptEngine;
	RealFunctionJRubyScriptEngineFactory(final ScriptEngine scriptEngine){
		this.scriptEngine=scriptEngine;
	}
	
	RealFunction realFunction(final String code) {
		try {
			return newRealFunction(code);
		} catch (ScriptException ex) {
			throw new IllegalArgumentException("Unable to ctreated scripted function", ex);
		}
	}
	
	private RealFunction newRealFunction(final String code) throws ScriptException {
		final Object receiver = scriptEngine.eval(String.format(SCRIPT, RealFunction.class.getName(), code));
		return  (RealFunction) ((Invocable) scriptEngine).getInterface(receiver, RealFunction.class);
	}
	
	
	

}
