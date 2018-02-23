package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

public class MongoDatabaseConfigurationTest {
	
	private final MongoDatabaseConfiguration mongoDatabaseConfiguration = new MongoDatabaseConfiguration();
	
	@Test
	public void  mongoTemplate() {
		assertEquals(MongoDatabaseConfiguration.DATABASENAME, ((MongoTemplate) mongoDatabaseConfiguration.mongoTemplate()).getDb().getName());
	}
	
	@Test
	public void  scriptRepository() {
		final MongoOperations mongoOperations = Mockito.mock(MongoOperations.class);
		final ScriptRepository scriptRepository = mongoDatabaseConfiguration.scriptRepositoryMongo(mongoOperations);
		final Object dependency=  DataAccessUtils.requiredSingleResult(Arrays.asList(ScriptRepositoryImpl.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(MongoOperations.class)).map(field -> ReflectionTestUtils.getField(scriptRepository, field.getName())).collect(Collectors.toList()));
		
	    assertEquals(mongoOperations, dependency);
	}

}
