package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoOperations;

import de.mq.analysis.integration.Script;


public class ScriptRepositoryTest {
	
	private MongoOperations mongoOperations = Mockito.mock(MongoOperations.class);
	private final ScriptRepository scriptRepository = new ScriptRepositoryImpl(mongoOperations);
	private final Script script = Mockito.mock(Script.class);
	
	@BeforeEach
	public final void setup(){
		Mockito.when(scriptRepository.find()).thenReturn(Arrays.asList(script));
	}
	
	@Test
	public final void find() {
		final Collection<Script> scripts = scriptRepository.find();
		assertEquals(1, scripts.size());
		assertEquals(script, scripts.iterator().next());
	}
	
	@Test
	public final void save() {
		scriptRepository.save(script);
		Mockito.verify(mongoOperations).save(script);
		
	}

}
