package de.mq.analysis.integration.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
class MongoDatabaseConfiguration {
	
	static final String DATABASENAME = "analysis";
	
	@Bean
	@Profile("mongo")
	MongoOperations mongoTemplate() {
		return new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), DATABASENAME));
	}
	
	@Bean()
	@Profile("mongo")
	ScriptRepository scriptRepositoryMongo(final MongoOperations mongoOperations) {
	  return  new ScriptRepositoryImpl(mongoOperations);	
	}

}
