package de.mq.analysis.integration.support;

import java.io.IOException;
import java.net.URL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
@Configuration
class DefiniteIntegralConfigurationFX {
	
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


}
