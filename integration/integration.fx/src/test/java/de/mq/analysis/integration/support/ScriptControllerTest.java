package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import de.mq.analysis.integration.Script;



public class ScriptControllerTest {
	
	private final ScriptService scriptService = Mockito.mock(ScriptService.class);
	
	private final  ScriptController scriptController = new ScriptController(scriptService);
	
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


}
