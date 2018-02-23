package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

public class JdbcDatabaseConfigurationTest {
	
	private final JdbcDatabaseConfiguration jdbcDatabaseConfiguration = new JdbcDatabaseConfiguration();
	
	@Test
	public final void datasource() {
		final BasicDataSource datasource  = (BasicDataSource) jdbcDatabaseConfiguration.datasource();
		assertEquals(JdbcDatabaseConfiguration.DRIVER_CLASS_NAME, datasource.getDriverClassName());
		assertEquals(JdbcDatabaseConfiguration.URL, datasource.getUrl());
		assertEquals(JdbcDatabaseConfiguration.USER, datasource.getUsername());
		assertEquals(JdbcDatabaseConfiguration.PASSWORD, datasource.getPassword());
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
