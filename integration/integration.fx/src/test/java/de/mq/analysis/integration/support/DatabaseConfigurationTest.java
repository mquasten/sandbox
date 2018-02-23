package de.mq.analysis.integration.support;



import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


import de.mq.analysis.integration.Script;



@ContextConfiguration(classes= {DatabaseConfiguration.class})
@ActiveProfiles("jdbc")
public class DatabaseConfigurationTest {

	@Autowired
	JdbcOperations jdbcOperations; 
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ScriptRepository scriptRepository;
	
	@Test
	@Ignore
	public final void test() throws SQLException {
		Connection con = dataSource.getConnection();
		Statement statement = con.createStatement();
		statement.execute("CREATE TABLE SCRIPT( ID VARCHAR(50) NOT NULL, CODE VARCHAR(250) NOT NULL, PRIMARY KEY (ID)); CREATE SEQUENCE IDGEN AS BIGINT START WITH 1;");
		
		statement.close();
		Statement statement2 = con.createStatement();
		statement2.close();
		
		Statement statement3 = con.createStatement();
		statement3.execute("shutdown;");
		statement3.close();
		con.close();
		
	
	}
	
	@Ignore
	@Test
	public final void findScripts() {
		Collection<Script> scripts = scriptRepository.find();
		
		assertEquals(1, scripts.size());
		
		System.out.println(scripts.stream().findAny().get().code());
		
		System.out.println(scripts.stream().findAny().get().id());
	}
	@Test
	@Ignore
	public final void callNextValue() {
		
		//call NEXT VALUE FOR IDGEN
		System.out.println(jdbcOperations.queryForObject("call NEXT VALUE FOR IDGEN", Long.class));
		
		System.out.println(jdbcOperations.queryForObject("call NEXT VALUE FOR IDGEN", Long.class));
		
	}
	
}
