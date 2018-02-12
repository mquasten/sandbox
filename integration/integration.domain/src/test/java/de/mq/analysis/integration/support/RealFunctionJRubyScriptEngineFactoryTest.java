package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RealFunctionJRubyScriptEngineFactoryTest {

	private final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("jruby");
	private final RealFunctionJRubyScriptEngineFactory scriptEngineFactory = new RealFunctionJRubyScriptEngineFactory(scriptEngine);

	@ParameterizedTest
	@ValueSource(strings = { "Math.exp(- x**2)", "**" })
	final void calculate(final String code) throws ScriptException {
		if (code.startsWith("Math")) {
			assertEquals(Double.valueOf(1 / Math.E), Double.valueOf(scriptEngineFactory.realFunction(code).f(1)));
		} else {
			assertThrows(IllegalArgumentException.class, () -> scriptEngineFactory.realFunction(code).f(Math.random()));
		}

	}

}
