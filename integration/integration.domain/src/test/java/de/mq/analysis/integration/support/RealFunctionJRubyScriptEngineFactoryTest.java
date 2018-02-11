package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.jupiter.api.Test;





public class RealFunctionJRubyScriptEngineFactoryTest {
	
	private final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("jruby");
	private final  RealFunctionJRubyScriptEngineFactory scriptEngineFactory =  new RealFunctionJRubyScriptEngineFactory(scriptEngine);

	
	@Test
	public final void calculate() throws ScriptException {
		assertEquals(Double.valueOf(1/ Math.E), Double.valueOf(scriptEngineFactory.realFunction("Math.exp(- x**2)").f(1)));
	}

}
