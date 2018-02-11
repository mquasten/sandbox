package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.Script;


public class ScriptServiceTest {
	
	private final Script script = Mockito.mock(Script.class);
	private final ScriptRepository scriptRepository = Mockito.mock(ScriptRepository.class);
	final ScriptService scriptService = new ScriptServiceImpl(scriptRepository);
	
	@BeforeEach
	public final void setup() {
		Mockito.when(scriptRepository.find()).thenReturn(Arrays.asList(script));
	}
	
	@Test
	public final void scripts() {
		assertEquals(Arrays.asList(script), scriptService.scripts());
	}

}
