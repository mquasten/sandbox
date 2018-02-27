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

	
	
	
	private @Value( "${jdbc.url:jdbc:hsqldb:file:analysis}" ) String url; 
	private @Value( "${jdbc.driver:org.hsqldb.jdbcDriver}" ) String driver; 
	private @Value( "${jdbc.user:sa}" ) String user; 
	private @Value( "${jdbc.password:}" ) String password; 

	@Bean(destroyMethod = "close")
	DataSource datasource() {
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(driver);
		datasource.setUrl(url);
		datasource.setUsername(user);
		datasource.setPassword(password);
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
