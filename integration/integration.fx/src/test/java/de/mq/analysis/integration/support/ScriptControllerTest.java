package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.Script;



class ScriptControllerTest {
	
	private final ScriptService scriptService = Mockito.mock(ScriptService.class);
	
	private final  ScriptController scriptController = new ScriptController(scriptService);
	
	private final Script script = Mockito.mock(Script.class);
	
	private final ScriptAO scriptAO = Mockito.mock(ScriptAO.class);
	
	@BeforeEach
	final void setup() {
		Mockito.when(scriptAO.getCurrentScript()).thenReturn(script);
		Mockito.when(scriptService.scripts()).thenReturn(Arrays.asList(script));
	}
	
	@Test
	final void scripts() {
		assertEquals(Arrays.asList(script), scriptController.scripts());
	}
	
	@Test
	final void save() {	
		assertEquals(script, scriptController.save(scriptAO));
		
		Mockito.verify(scriptService).save(script);
	}
	
	@Test
	final void delete() {	
		scriptController.delete(scriptAO);
		
		Mockito.verify(scriptService).delete(script);
	}


}
