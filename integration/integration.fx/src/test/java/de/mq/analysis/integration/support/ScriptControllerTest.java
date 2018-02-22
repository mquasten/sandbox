package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.RealFunction;
import de.mq.analysis.integration.Script;



public class ScriptControllerTest {
	
	private static final String CODE = "x**2";
	private final ScriptService scriptService = Mockito.mock(ScriptService.class);
	private final RealFunctionJRubyScriptEngineFactory realFunctionJRubyScriptEngineFactory = Mockito.mock(RealFunctionJRubyScriptEngineFactory.class);
	
	private final  ScriptController scriptController = new ScriptController(scriptService, realFunctionJRubyScriptEngineFactory);
	
	private final RealFunction realFunction = Mockito.mock(RealFunction.class);
	
	private final Script script = Mockito.mock(Script.class);
	
	private final ScriptAO scriptAO = Mockito.mock(ScriptAO.class);
	
	@Before
	public final void setup() {
		Mockito.when(scriptAO.getCurrentScript()).thenReturn(script);
		Mockito.when(scriptService.scripts()).thenReturn(Arrays.asList(script));
	}
	
	@Test
	public final void scripts() {
		assertEquals(Arrays.asList(script), scriptController.scripts());
	}
	
	@Test
	public final void save() {	
		assertEquals(script, scriptController.save(scriptAO));
		
		Mockito.verify(scriptService).save(script);
	}
	
	@Test
	public final void delete() {	
		scriptController.delete(scriptAO);
		
		Mockito.verify(scriptService).delete(script);
	}
	
	@Test
	public final void check() {
		
		Mockito.doReturn(script).when(scriptAO).getCurrentScript();
		Mockito.doReturn(CODE).when(script).code();
		Mockito.doReturn(realFunction).when(realFunctionJRubyScriptEngineFactory).realFunction(CODE);
		
		assertTrue(scriptController.check(scriptAO));
	}

	
	@Test
	public final void checkScriptMissing() {
		Mockito.doReturn(null).when(scriptAO).getCurrentScript();
		assertFalse(scriptController.check(scriptAO));
	}
	
	@Test
	public final void checkScriptCodeMissing() {
		Mockito.doReturn(script).when(scriptAO).getCurrentScript();
		assertFalse(scriptController.check(scriptAO));
	}
	
	@Test
	public final void checkCodeInvalid() {
		Mockito.doReturn(script).when(scriptAO).getCurrentScript();
		Mockito.doReturn(CODE).when(script).code();
		Mockito.doThrow(new IllegalArgumentException("Code is invalid")).when(realFunctionJRubyScriptEngineFactory).realFunction(CODE);
		
		assertFalse(scriptController.check(scriptAO));
	}


}
