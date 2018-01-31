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
	Parent scriptDialogParent(ScriptFX scriptFX) throws IOException {
		final URL url = getClass().getResource("/script.fxml");
		final FXMLLoader formLoader = new FXMLLoader(url);
		formLoader.setController(scriptFX);
		return formLoader.load();

	}

	@Bean
	@Scope("prototype")
	Parent definiteIntegralParent(final DefiniteIntegralFX definiteIntegralFX) throws IOException {
		final URL url = getClass().getResource("/definiteIntegral.fxml");
		final FXMLLoader formLoader = new FXMLLoader(url);
		formLoader.setController(definiteIntegralFX);
		return formLoader.load();
	}

}
