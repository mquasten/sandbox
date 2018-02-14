package de.mq.analysis.integration.support;

import java.io.IOException;
import java.net.URL;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

import de.mq.analysis.integration.IntegrationService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

@ComponentScan(basePackages = { "de.mq.analysis.integration.support" })
@Configuration
class DefiniteIntegralConfiguration {

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

	@Bean()
	@Scope("prototype")
	Parent scriptDialogParent(final ScriptFX scriptFX) throws IOException  {
		//final URL url = getClass().getResource("/script.fxml");
		final FXMLLoader formLoader = newFXMLLoader("/script.fxml");
		formLoader.setController(scriptFX);
		
		return formLoader.load();
		
	}

	FXMLLoader newFXMLLoader(final String  resource) {
		final URL url = getClass().getResource(resource);
		return new FXMLLoader(url);
	}

	@Bean
	@Scope("prototype")
	Parent definiteIntegralParent(final DefiniteIntegralFX definiteIntegralFX) throws IOException {
		
		final FXMLLoader formLoader = newFXMLLoader("/definiteIntegral.fxml");
		formLoader.setController(definiteIntegralFX);
		return formLoader.load();
	}

	
	@Bean
	MongoOperations mongoTemplate() {
		try(final MongoClient mongoClient = new MongoClient()) {
		return new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "analysis"));
		}

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
