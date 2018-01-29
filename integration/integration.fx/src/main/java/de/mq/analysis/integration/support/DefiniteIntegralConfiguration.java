package de.mq.analysis.integration.support;

import java.util.Collection;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.mq.analysis.integration.IntegrationService;

@Configuration class DefiniteIntegralConfiguration  {
	
	@Bean
	 DefiniteIntegralFX definiteIntegralFX(final DefiniteIntegralController definiteIntegralController) {
		return new DefiniteIntegralFX(definiteIntegralController);
	}
	
	@Bean
	DefiniteIntegralController definiteIntegralController(final Collection<IntegrationService> integrationServices, AbstractRealFunctionJRubyScriptEngineFactory scriptEngineFactory) {
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
	@Scope("object")
	ScriptEngine scriptEngine(final ScriptEngineManager scriptEngineManager) {
		 return new ScriptEngineManager().getEngineByName("jruby");
	}
	
	@Bean
	ScriptEngineManager scriptEngineManager() {
		return new ScriptEngineManager();
	}
	
	@Bean
	AbstractRealFunctionJRubyScriptEngineFactory abstractRealFunctionJRubyScriptEngineFactory() {
		return new AbstractRealFunctionJRubyScriptEngineFactory() {

			@Override
			ScriptEngine scriptEngine() {
				// das ist mist, stoert aber nicht weiter ...
				return null;
			}
			
		};
	}
	

}
