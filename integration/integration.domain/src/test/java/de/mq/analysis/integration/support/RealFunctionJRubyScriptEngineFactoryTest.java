package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

public class RealFunctionJRubyScriptEngineFactoryTest {

	private final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("jruby");
	private final RealFunctionJRubyScriptEngineFactory scriptEngineFactory = new RealFunctionJRubyScriptEngineFactory(scriptEngine);

	@Test

	public final void calculate() {

		assertEquals(Double.valueOf(1 / Math.E), Double.valueOf(scriptEngineFactory.realFunction("Math.exp(- x**2)").f(1)));

	}

	@Test(expected = IllegalArgumentException.class)
	public final void calculateWrongExpression() {
		scriptEngineFactory.realFunction("**").f(Math.random());
	}

}
