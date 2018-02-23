package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ReflectionUtils;

import de.mq.analysis.integration.Script;

public class ScriptJDBCRepositoryTest {
	
	
	private static final long NEXT_VALUE_SEQUENCE = 4711L;

	private static final String CODE = "x**2";

	private static final String ID = UUID.randomUUID().toString();

	private final JdbcOperations jdbcOperations = Mockito.mock(JdbcOperations.class);
	
	private final ScriptJDBCRepositoryImpl scriptJDBCRepository = Mockito.mock(ScriptJDBCRepositoryImpl.class, Mockito.CALLS_REAL_METHODS);
	
	private final Script script = Mockito.mock(Script.class) ;
	
	
	
	@SuppressWarnings("unchecked")
	private final RowMapper<Script> rowMapper = Mockito.mock(RowMapper.class);
	
	@Before
	public final void setup() {
		
		
		Mockito.doReturn(Arrays.asList(script)).when(jdbcOperations).query( String.format(ScriptJDBCRepositoryImpl.SELECT_SCRIPTS_SQL, ScriptJDBCRepositoryImpl.ID_COLUMN, ScriptJDBCRepositoryImpl.CODE_COLUMN), rowMapper );
		
		Mockito.doReturn(NEXT_VALUE_SEQUENCE).when(jdbcOperations).queryForObject(String.format(ScriptJDBCRepositoryImpl.NEXT_VALUE_FROM_SEQUENCE_SQL,  ScriptJDBCRepositoryImpl.ID_COLUMN, ScriptJDBCRepositoryImpl.CODE_COLUMN), Long.class);
		
		
		Mockito.doReturn (rowMapper).when(scriptJDBCRepository).newRowMapper();
		
		Arrays.asList(ScriptJDBCRepositoryImpl.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(JdbcOperations.class)).forEach(field -> {
			field.setAccessible(true);
			ReflectionUtils.setField(field, scriptJDBCRepository, jdbcOperations);
		});
	}
	
	@Test
	public final void find() {
		final Collection<Script> results = scriptJDBCRepository.find();
		
		assertEquals(1, results.size());
		assertEquals(script, results.stream().findAny().get());
	}
	
	@Test
	public final void newRowMapper() throws SQLException {
		Mockito.doCallRealMethod().when(scriptJDBCRepository).newRowMapper();
		final RowMapper<Script> rowMapper = scriptJDBCRepository.newRowMapper();
		final ResultSet resultSet = Mockito.mock(ResultSet.class);
		Mockito.doReturn(CODE).when(resultSet).getString(ScriptJDBCRepositoryImpl.CODE_COLUMN);
		Mockito.doReturn(ID).when(resultSet).getString(ScriptJDBCRepositoryImpl.ID_COLUMN);
		
		final Script script = rowMapper.mapRow(resultSet, Mockito.anyInt());
		
		assertEquals(CODE, script.code());
		assertEquals(ID, script.id());
	}
	
	@Test
	public final void saveInsert() {
		Mockito.doReturn(null).when(script).id();
		Mockito.doReturn(CODE).when(script).code();
		
		scriptJDBCRepository.save(script);
		
		final ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
		final ArgumentCaptor<Object[]> argumentCaptor = ArgumentCaptor.forClass(Object[].class);
		
		Mockito.verify(jdbcOperations).update(sqlCaptor.capture(), argumentCaptor.capture());
		
		assertEquals(String.format(ScriptJDBCRepositoryImpl.INSERT_SQL, ScriptJDBCRepositoryImpl.ID_COLUMN, ScriptJDBCRepositoryImpl.CODE_COLUMN ), sqlCaptor.getValue());
		assertEquals(2, argumentCaptor.getAllValues().size());
		assertEquals(new UUID(NEXT_VALUE_SEQUENCE, NEXT_VALUE_SEQUENCE).toString(), argumentCaptor.getAllValues().get(0));
		assertEquals(CODE, argumentCaptor.getAllValues().get(1));
	}
	
	@Test
	public final void saveUpdate() {
		Mockito.doReturn(ID).when(script).id();
		Mockito.doReturn(CODE).when(script).code();
		
		scriptJDBCRepository.save(script);
		
		final ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
		final ArgumentCaptor<Object[]> argumentCaptor = ArgumentCaptor.forClass(Object[].class);
		
		Mockito.verify(jdbcOperations).update(sqlCaptor.capture(), argumentCaptor.capture());
		
		assertEquals(String.format(ScriptJDBCRepositoryImpl.UPDATE_SQL, ScriptJDBCRepositoryImpl.CODE_COLUMN, ScriptJDBCRepositoryImpl.ID_COLUMN ),sqlCaptor.getValue());
		assertEquals(ID, argumentCaptor.getAllValues().get(1));
		assertEquals(CODE, argumentCaptor.getAllValues().get(0));
	}
	
	@Test
	public final void delete() {
		Mockito.doReturn(ID).when(script).id();
		
		scriptJDBCRepository.delete(script);
		
		final ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
		final ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
		
		
		Mockito.verify(jdbcOperations).update(sqlCaptor.capture(), idCaptor.capture());
		
		assertEquals(String.format(ScriptJDBCRepositoryImpl.DELETE_SQL, ScriptJDBCRepositoryImpl.ID_COLUMN ),sqlCaptor.getValue());
		assertEquals(ID, idCaptor.getValue());
	}
	
	@Test
	public final void dependencies() {
		final Object repository = new ScriptJDBCRepositoryImpl(jdbcOperations);
		assertEquals(jdbcOperations, DataAccessUtils.requiredSingleResult(Arrays.asList(ScriptJDBCRepositoryImpl.class.getDeclaredFields()).stream().filter(field -> field.getType().equals(JdbcOperations.class)).map(field -> {
			field.setAccessible(true);
			return ReflectionUtils.getField(field, repository);
		}).collect(Collectors.toList())));
	}
	

}
