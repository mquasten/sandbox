package de.mq.analysis.integration.support;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

import de.mq.analysis.integration.IntegrationService;

@ComponentScan(basePackages = { "de.mq.analysis.integration.support" })
@Configuration
class DefiniteIntegralConfiguration {

	static final String DATABASENAME = "analysis";

	@Bean
	IntegrationService trapezoidIntegration() {
		return new TrapezoidIntegrationImpl();
	}

	@Bean
	IntegrationService simpsonIntegrationService() {
		return new SimpsonIntegrationServiceImpl();
	}

	@Bean
	ScriptEngine scriptEngine() throws ScriptException {
		final ScriptEngine result = new ScriptEngineManager().getEngineByName("jruby");
		result.eval("true");
		return result;
	}

	@Bean
	RealFunctionJRubyScriptEngineFactory realFunctionJRubyScriptEngineFactory(final ScriptEngine scriptEngine) {
		return new RealFunctionJRubyScriptEngineFactory(scriptEngine);
	}

	
	
	@Bean
	MongoOperations mongoTemplate() {
		return new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), DATABASENAME));
	}
	
	@Bean
	ScriptRepository scriptRepository(final MongoOperations mongoOperations) {
	  return  new ScriptRepositoryImpl(mongoOperations);	
	}
	
	@Bean
	ScriptService scriptService(final ScriptRepository scriptRepository) {
	  return  new ScriptServiceImpl(scriptRepository);	
	}

}
