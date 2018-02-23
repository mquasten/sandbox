package de.mq.analysis.integration.support;





import javax.sql.DataSource;



import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class DatabaseConfiguration {

	@Bean( destroyMethod = "close")
	@Profile("jdbc")
	DataSource datasource() {
		
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("org.hsqldb.jdbcDriver");
		datasource.setUrl("jdbc:hsqldb:file:analysis");
		datasource.setUsername("sa" );
		datasource.setPassword("");
		return datasource;
		
	}
	
	@Bean
	@Profile("jdbc")
	JdbcOperations jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	@Bean
	@Profile("jdbc")
	ScriptRepository scriptRepositoryJDBC(final JdbcOperations jdbcOperations) {
		return new ScriptJDBCRepositoryImpl(jdbcOperations);
		
	}

}
