package de.mq.analysis.integration.support;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.Script;



public class ScriptControllerTest {
	
	private final ScriptService scriptService = Mockito.mock(ScriptService.class);
	
	private final  ScriptController scriptController = new ScriptController(scriptService);
	
	private final Script script = Mockito.mock(Script.class);
	
	@Before
	public final void setup() {
		Mockito.when(scriptService.scripts()).thenReturn(Arrays.asList(script));
	}
	
	@Test
	public final void scripts() {
		Assert.assertEquals(Arrays.asList(script), scriptController.scripts());
	}
	
	

}
