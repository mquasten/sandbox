package de.mq.analysis.integration.support;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.Script;


public class ScriptServiceTest {
	
	private final Script script = Mockito.mock(Script.class);
	private final ScriptRepository scriptRepository = Mockito.mock(ScriptRepository.class);
	final ScriptService scriptService = new ScriptServiceImpl(scriptRepository);
	
	@Before
	public final void setup() {
		Mockito.when(scriptRepository.find()).thenReturn(Arrays.asList(script));
	}
	
	@Test
	public final void scripts() {
		Assert.assertEquals(Arrays.asList(script), scriptService.scripts());
	}

}
