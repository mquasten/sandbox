package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;


public class JdbcDatabaseConfigurationTest {
	
	static final String PASSWORD = "jdbc.password";
	static final String USER = "jdbc.user";
	static final String URL = "jdbc.url";
	static final String DRIVER ="jdbc.driver";
	
	private final JdbcDatabaseConfiguration jdbcDatabaseConfiguration = new JdbcDatabaseConfiguration();
	
	
	private final Map<String,String> jdbcProperties = new HashMap<>();
	
	@Before
	public final void setup() {
		Arrays.asList(JdbcDatabaseConfiguration.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Value.class)).forEach(field -> {
			final String[] values = field.getAnnotation(Value.class).value().split(":" , 2);
			assertEquals(2, values.length);
			final String value = values[1].replaceFirst("[}]", "").trim();
			jdbcProperties.put(values[0].replaceFirst("[$]", "").replaceFirst("[{]", "").trim(), value);
		    ReflectionTestUtils.setField(jdbcDatabaseConfiguration, field.getName(), value);
		});
	}
	
	
	@Test
	public final void datasource() {
		assertEquals(4, jdbcProperties.size());
	
		Arrays.asList(PASSWORD,USER,URL,DRIVER).forEach(key -> assertTrue(jdbcProperties.containsKey(key)));
		
		final BasicDataSource datasource  = (BasicDataSource) jdbcDatabaseConfiguration.datasource();
		assertEquals(jdbcProperties.get(DRIVER), datasource.getDriverClassName());
		assertEquals(jdbcProperties.get(URL), datasource.getUrl());
		assertEquals(jdbcProperties.get(USER), datasource.getUsername());
		assertEquals(jdbcProperties.get(PASSWORD), datasource.getPassword());
	}
	
	@Test
	public final void jdbcTemplate() {
		final DataSource dataSource = Mockito.mock(DataSource.class);
		assertEquals(dataSource, ((JdbcTemplate) jdbcDatabaseConfiguration.jdbcTemplate(dataSource)).getDataSource());
	}

	
	@Test
	public final void  scriptRepositoryJDBC() {
		final JdbcOperations jdbcOperations = Mockito.mock(JdbcOperations.class);
		final ScriptRepository scriptRepository = jdbcDatabaseConfiguration.scriptRepositoryJDBC(jdbcOperations);
		
		assertEquals(jdbcOperations, DataAccessUtils.requiredSingleResult(Arrays.asList(ScriptJDBCRepositoryImpl.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(JdbcOperations.class)).map(field -> ReflectionTestUtils.getField(scriptRepository, field.getName())).collect(Collectors.toList())));
	}

}
