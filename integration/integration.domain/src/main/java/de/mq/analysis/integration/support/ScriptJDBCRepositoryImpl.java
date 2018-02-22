package de.mq.analysis.integration.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;


import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.data.annotation.Id;

import de.mq.analysis.integration.Script;

public class ScriptJDBCRepositoryImpl  implements ScriptRepository {
	
	
	private static final String SELECT_SCRIPTS_SQL = "SELECT %s, %s from SCRIPT";
	private static final String CODE_COLUMN = "CODE";
	private static final String ID_COLUMN = "ID";
	private final JdbcOperations jdbcOperations;
	
	//call NEXT VALUE FOR IDGEN

	ScriptJDBCRepositoryImpl(final JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	public Collection<Script> find() {
		
		return jdbcOperations.query(String.format(SELECT_SCRIPTS_SQL, ID_COLUMN, CODE_COLUMN) , new RowMapper<Script>() {

			@Override
			public Script mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
				
				final String id = resultSet.getString(ID_COLUMN);
				final String code = resultSet.getString(CODE_COLUMN);
				
				Assert.hasText(id, "Id is mandatory in database.");
				Assert.hasText(id, "Code is mandatory in database.");
				final Script result =  new ScriptImpl(code);
				Arrays.asList(ScriptImpl.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Id.class)).forEach(field -> {
					field.setAccessible(true);
					ReflectionUtils.setField(field, result, id);
				});;
				return result;
			}});
	
		
	}

	@Override
	public void save(Script script) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Script script) {
		// TODO Auto-generated method stub
		
	}

}
