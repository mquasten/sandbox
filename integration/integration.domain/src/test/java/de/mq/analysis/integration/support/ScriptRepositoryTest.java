package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoOperations;

import de.mq.analysis.integration.Script;

class ScriptRepositoryTest {

	private MongoOperations mongoOperations = Mockito.mock(MongoOperations.class);
	private final ScriptRepository scriptRepository = new ScriptRepositoryImpl(mongoOperations);
	private final Script script = Mockito.mock(Script.class);

	@BeforeEach
	final void setup() {
		Mockito.when(scriptRepository.find()).thenReturn(Arrays.asList(script));
	}

	@Test
	final void find() {
		final Collection<Script> scripts = scriptRepository.find();
		assertEquals(1, scripts.size());
		assertEquals(script, scripts.iterator().next());
	}

	@Test
	final void save() {
		scriptRepository.save(script);
		Mockito.verify(mongoOperations).save(script);
	}

	@Test
	final void delete() {
		scriptRepository.delete(script);
		Mockito.verify(mongoOperations).remove(script);
	}

}
