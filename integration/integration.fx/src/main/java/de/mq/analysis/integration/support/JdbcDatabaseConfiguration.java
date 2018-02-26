package de.mq.analysis.integration.support;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile("jdbc")
class JdbcDatabaseConfiguration {

	static final String PASSWORD = "";
	static final String USER = "sa";
	static final String URL = "jdbc:hsqldb:file:analysis";
	static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
	
	
	private @Value( "${jdbc.url:jdbc:hsqldb:file:analysis}" ) String url; 
	private @Value( "${jdbc.driver:org.hsqldb.jdbcDriver}" ) String driver; 
	private @Value( "${jdbc.user:sa}" ) String user; 
	private @Value( "${jdbc.password:}" ) String password; 

	@Bean(destroyMethod = "close")
	DataSource datasource() {
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(DRIVER_CLASS_NAME);
		datasource.setUrl(URL);
		datasource.setUsername(USER);
		datasource.setPassword(PASSWORD);
		return datasource;
	}

	@Bean
	JdbcOperations jdbcTemplate(final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	ScriptRepository scriptRepositoryJDBC(final JdbcOperations jdbcOperations) {
		return new ScriptJDBCRepositoryImpl(jdbcOperations);

	}

}
