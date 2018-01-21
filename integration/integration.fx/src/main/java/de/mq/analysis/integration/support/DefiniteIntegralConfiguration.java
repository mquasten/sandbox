package de.mq.analysis.integration.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration class DefiniteIntegralConfiguration {
	
	@Bean
	 DefiniteIntegralFX definiteIntegralFX(final DefiniteIntegralController definiteIntegralController) {
		return new DefiniteIntegralFX(definiteIntegralController);
	}
	
	@Bean
	DefiniteIntegralController definiteIntegralController() {
		return new DefiniteIntegralController();
	}
	

}
