package de.mq.analysis.integration.support;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import de.mq.analysis.integration.IntegrationService;
@PropertySource("classpath:/analysis.properties")
@ComponentScan(basePackages = { "de.mq.analysis.integration.support" })
@Configuration
class DefiniteIntegralConfiguration {

	static final String MESSAGE_SOURCE_ENCODING = "UTF-8";
	static final String[] MESSAGE_SOURCE_BASENAME =  {"i18n/definiteintegral", "i18n/script"};

	@Bean
	IntegrationService trapezoidIntegration() {
		return new TrapezoidIntegrationImpl();
	}

	@Bean
	IntegrationService simpsonIntegrationService() {
		return new SimpsonIntegrationServiceImpl();
	}
	
	
	@Bean
	IntegrationService rectangleIntegrationService() {
		return new RectangleIntegrationServiceImpl();
		
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
	ScriptService scriptService(final ScriptRepository scriptRepository) {
	  return  new ScriptServiceImpl(scriptRepository);	
	}
	
	 @Bean
	 MessageSource messageSource() {
	    	final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	        messageSource.setBasenames(MESSAGE_SOURCE_BASENAME);
	        messageSource.setDefaultEncoding(MESSAGE_SOURCE_ENCODING);
	        return messageSource;
	    }
	
}
