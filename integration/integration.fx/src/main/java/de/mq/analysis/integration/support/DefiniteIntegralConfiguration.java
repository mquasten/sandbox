package de.mq.analysis.integration.support;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.mq.analysis.integration.IntegrationService;

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

	
	
	@Bean
	ScriptService scriptService(final ScriptRepository scriptRepository) {
	  return  new ScriptServiceImpl(scriptRepository);	
	}

}
