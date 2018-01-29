package de.mq.analysis.integration.support;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;




public class JRubyIntegrationTest {
	

	private final  AbstractRealFunctionJRubyScriptEngineFactory scriptEngineFactory = Mockito.mock(AbstractRealFunctionJRubyScriptEngineFactory.class, Mockito.CALLS_REAL_METHODS);

	@Before
	public final void setup() {
		final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("jruby");
		Mockito.when(scriptEngineFactory.scriptEngine()).thenReturn(scriptEngine);
	}
	
	@Test
	public final void calculate() throws ScriptException {
		Assert.assertEquals(Double.valueOf(1/ Math.E), Double.valueOf(scriptEngineFactory.realFunction("Math.exp(- x**2)").f(1)));
	}

}
