package de.mq.analysis.integration.support;

import java.util.Collection;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.mq.analysis.integration.IntegrationService;

@Configuration class DefiniteIntegralConfiguration  {
	
	@Bean
	 DefiniteIntegralFX definiteIntegralFX(final DefiniteIntegralController definiteIntegralController) {
		return new DefiniteIntegralFX(definiteIntegralController);
	}
	
	@Bean
	DefiniteIntegralController definiteIntegralController(final Collection<IntegrationService> integrationServices, RealFunctionJRubyScriptEngineFactory scriptEngineFactory) {
		return new DefiniteIntegralController(integrationServices,scriptEngineFactory);
	}
	
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
		 final ScriptEngine result=  new ScriptEngineManager().getEngineByName("jruby");
		 result.eval( "true");
		 return result;
	}
	
	
	
	@Bean
	RealFunctionJRubyScriptEngineFactory realFunctionJRubyScriptEngineFactory(final ScriptEngine scriptEngine) {
		return new RealFunctionJRubyScriptEngineFactory(scriptEngine);		
	}
	

}
