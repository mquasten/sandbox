package de.mq.analysis.integration.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.data.annotation.Id;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import de.mq.analysis.integration.Script;

public class ScriptJDBCRepositoryImpl implements ScriptRepository {

	static final String SELECT_SCRIPTS_SQL = "SELECT %s, %s from SCRIPT";
	static final String CODE_COLUMN = "CODE";
	static final String ID_COLUMN = "ID";
	private final JdbcOperations jdbcOperations;

	static final String NEXT_VALUE_FROM_SEQUENCE_SQL = "CALL NEXT VALUE FOR IDGEN";
	static final String INSERT_SQL = "INSERT INTO SCRIPT (%s, %s) VALUES (?,?)";
	static final String UPDATE_SQL = "UPDATE SCRIPT SET %s = ? WHERE %s = ?";
	static final String DELETE_SQL = "DELETE FROM SCRIPT WHERE %s = ?";

	static final String CHECK_SCHEMA_SQL = "SELECT  TABLE_NAME NAME FROM  INFORMATION_SCHEMA.TABLES WHERE  TABLE_SCHEMA= 'PUBLIC' " + " UNION " + "SELECT SEQUENCE_NAME NAME FROM  INFORMATION_SCHEMA.SEQUENCES  WHERE  SEQUENCE_SCHEMA= 'PUBLIC' ";

	private final Map<String, String> createStements = new HashMap<>();

	ScriptJDBCRepositoryImpl(final JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
		createStements.put("SCRIPT", "CREATE TABLE SCRIPT( ID VARCHAR(50) NOT NULL, CODE VARCHAR(255) NOT NULL, PRIMARY KEY (ID))");
		createStements.put("IDGEN", "CREATE SEQUENCE IDGEN AS BIGINT START WITH 1");
		;
	}

	@PostConstruct
	void createsChema() {
		final List<String> schemaObjects = jdbcOperations.queryForList(CHECK_SCHEMA_SQL, String.class);
		createStements.entrySet().stream().filter(entry -> !schemaObjects.contains(entry.getKey())).map(Entry::getValue).forEach(sql -> jdbcOperations.update(sql));
	}

	@Override
	public final Collection<Script> find() {
		return jdbcOperations.query(String.format(SELECT_SCRIPTS_SQL, ID_COLUMN, CODE_COLUMN), newRowMapper());
	}

	RowMapper<Script> newRowMapper() {
		return new RowMapper<Script>() {

			@Override
			public Script mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {

				final String id = resultSet.getString(ID_COLUMN);
				final String code = resultSet.getString(CODE_COLUMN);

				Assert.hasText(id, "Id is mandatory in database.");
				Assert.hasText(id, "Code is mandatory in database.");
				final Script result = new ScriptImpl(code);
				Arrays.asList(ScriptImpl.class.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Id.class)).forEach(field -> {
					field.setAccessible(true);
					ReflectionUtils.setField(field, result, id);
				});
				return result;
			}
		};
	}

	@Override
	public void save(final Script script) {
		Assert.hasText(script.code(), "Code is mandatory.");
		if (!StringUtils.hasText(script.id())) {
			final Long id = jdbcOperations.queryForObject(NEXT_VALUE_FROM_SEQUENCE_SQL, Long.class);
			Assert.notNull(id, "Next value from sequence not found.");
			jdbcOperations.update(String.format(INSERT_SQL, ID_COLUMN, CODE_COLUMN), new UUID(id, id).toString(), script.code());
			return;
		}

		jdbcOperations.update(String.format(UPDATE_SQL, CODE_COLUMN, ID_COLUMN), script.code(), script.id());

	}

	@Override
	public void delete(final Script script) {
		Assert.hasText(script.id(), "Id is mandatory.");
		jdbcOperations.update(String.format(DELETE_SQL, ID_COLUMN), script.id());

	}

}
