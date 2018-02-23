package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.withSettings;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.analysis.integration.RealFunction;

public class DefiniteIntegralConfigurationTest {
	
	private static final String CODE = "Math.exp(-x**2)";
	private final DefiniteIntegralConfiguration definiteIntegralConfiguration = new DefiniteIntegralConfiguration();

	@Test
	public void trapezoidIntegration() {
		
		assertTrue(definiteIntegralConfiguration.trapezoidIntegration() instanceof TrapezoidIntegrationImpl); 
	}
	
	@Test
	public void  simpsonIntegrationService() {
		assertTrue(definiteIntegralConfiguration.simpsonIntegrationService() instanceof SimpsonIntegrationServiceImpl); 
	}
	
	@Test
	public void  scriptEngine() throws ScriptException {
		final ScriptEngine scriptEngine = definiteIntegralConfiguration.scriptEngine();
		assertEquals(1d/Math.E, scriptEngine.eval("Math.exp(-1)"));
	}
	
	@Test
	public void  realFunctionJRubyScriptEngineFactory() throws ScriptException {
		ScriptEngine scriptEngine = Mockito.mock(ScriptEngine.class, withSettings().extraInterfaces(Invocable.class));
		final Object receiver = Mockito.mock(Object.class);
		RealFunction realFunction = Mockito.mock(RealFunction.class);
		Mockito.when(  ((Invocable) scriptEngine).getInterface(receiver, RealFunction.class)).thenReturn(realFunction);
		Mockito.when(scriptEngine.eval(String.format(RealFunctionJRubyScriptEngineFactory.SCRIPT, RealFunction.class.getName(), CODE))).thenReturn(receiver);
		assertEquals(realFunction, definiteIntegralConfiguration.realFunctionJRubyScriptEngineFactory(scriptEngine).realFunction(CODE));
	}
	
	
	
	@Test
	public void  scriptService() {
		final ScriptRepository scriptRepository = Mockito.mock(ScriptRepository.class);
		final ScriptService scriptService = definiteIntegralConfiguration.scriptService(scriptRepository);
		final Object dependency=  DataAccessUtils.requiredSingleResult(Arrays.asList(ScriptServiceImpl.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(ScriptRepository.class)).map(field -> ReflectionTestUtils.getField(scriptService, field.getName())).collect(Collectors.toList()));
		
	    assertEquals(scriptRepository, dependency);
	}
	
	
}
