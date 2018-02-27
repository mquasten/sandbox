package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.test.util.ReflectionTestUtils;

public class MongoDatabaseConfigurationTest {
	
	private final MongoDatabaseConfiguration mongoDatabaseConfiguration = new MongoDatabaseConfiguration();
	
	private final Map<String,Object> mongoProperties = new HashMap<>();
	
	private final static String DATABSE = "mongo.database";
	private final static String HOST = "mongo.host";
	private final static String PORT = "mongo.port";
	
	@Before
	public final void setup() {
		Arrays.asList(MongoDatabaseConfiguration.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Value.class)).forEach(field -> {
			final String[] values = field.getAnnotation(Value.class).value().split(":" , 2);
			assertEquals(2, values.length);
			final Constructor<?> constructor = constructor(field);
			final Object value = BeanUtils.instantiateClass(constructor, values[1].replaceFirst("[}]", "").trim());
					
					
			mongoProperties.put(values[0].replaceFirst("[$]", "").replaceFirst("[{]", "").trim(), value);
		    ReflectionTestUtils.setField(mongoDatabaseConfiguration, field.getName(), value);
		});
	}


	private  Constructor<?> constructor(Field field)  {
		try {
			return field.getType().getConstructor(String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new  IllegalStateException(e);
		}
	}
	
	
	@Test
	public void  mongoTemplate() {
		assertEquals(3, mongoProperties.size());
		assertTrue(mongoProperties.containsKey(DATABSE));
		assertTrue(mongoProperties.containsKey(HOST));
		assertTrue(mongoProperties.containsKey(PORT));
		
		assertEquals(mongoProperties.get(DATABSE), ((MongoTemplate) mongoDatabaseConfiguration.mongoTemplate()).getDb().getName());
		final SimpleMongoDbFactory dbFactory = (SimpleMongoDbFactory) ((MongoTemplate) mongoDatabaseConfiguration.mongoTemplate()).getMongoDbFactory();
		assertEquals(mongoProperties.get(HOST), dbFactory.getLegacyDb().getMongo().getAddress().getHost());
		assertEquals(mongoProperties.get(PORT), dbFactory.getLegacyDb().getMongo().getAddress().getPort());
		
		
	}
	
	@Test
	public void  scriptRepository() {
		final MongoOperations mongoOperations = Mockito.mock(MongoOperations.class);
		final ScriptRepository scriptRepository = mongoDatabaseConfiguration.scriptRepositoryMongo(mongoOperations);
		final Object dependency=  DataAccessUtils.requiredSingleResult(Arrays.asList(ScriptRepositoryImpl.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(MongoOperations.class)).map(field -> ReflectionTestUtils.getField(scriptRepository, field.getName())).collect(Collectors.toList()));
		
	    assertEquals(mongoOperations, dependency);
	}

}
