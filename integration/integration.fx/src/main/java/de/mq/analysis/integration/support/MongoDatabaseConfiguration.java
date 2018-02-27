package de.mq.analysis.integration.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
@Profile("mongo")
class MongoDatabaseConfiguration {
	
	private  @Value( "${mongo.database:analysis}" ) String database;
	private  @Value( "${mongo.host:127.0.0.1}" ) String host; 
	private  @Value( "${mongo.port:27017}" ) Integer port;
	
	@Bean
	MongoOperations mongoTemplate() {
		return new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(host, port), database));
	}
	
	@Bean()
	ScriptRepository scriptRepositoryMongo(final MongoOperations mongoOperations) {
	  return  new ScriptRepositoryImpl(mongoOperations);	
	}

}
