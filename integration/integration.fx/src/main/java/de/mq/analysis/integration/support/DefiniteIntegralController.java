package de.mq.analysis.integration.support;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collectors;


import org.springframework.util.Assert;

import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.RealFunction;
import de.mq.analysis.integration.Script;

class DefiniteIntegralController {
	
	private final Map<IntegrationService.CalculationAlgorithm, IntegrationService> algorithms = new HashMap<>();
	
	private final RealFunctionJRubyScriptEngineFactory scriptEngineFactory; 
	
	DefiniteIntegralController(final Collection<IntegrationService> integrationServices, final RealFunctionJRubyScriptEngineFactory scriptEngineFactory) {
		algorithms.putAll(integrationServices.stream().map(integrationService -> new AbstractMap.SimpleImmutableEntry<>(integrationService.calculationAlgorithm(), integrationService)).collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
	    this.scriptEngineFactory=scriptEngineFactory;
	}
	
	final void integrate(final DefiniteIntegralAO definiteIntegralAO) {
		Assert.isTrue(algorithms.containsKey(definiteIntegralAO.getCalculationAlgorithm()), "CalculationAlgorithm not found.");
		final RealFunction realFunction = scriptEngineFactory.realFunction(definiteIntegralAO.getScript().code());
		DefiniteIntegral definiteIntegral=  new DefiniteIntegralImpl(definiteIntegralAO.getBoundsOfIntegration(), realFunction, definiteIntegralAO.getNumberOfSamples());
		final double result =  algorithms.get(definiteIntegralAO.getCalculationAlgorithm()).calculate(definiteIntegral);
		definiteIntegralAO.setResult(result);
		
	}

	final void init(final DefiniteIntegralAO definiteIntegralAO) {
		definiteIntegralAO.setScript(new Script() {
			
			@Override
			public UUID id() {
				return UUID.randomUUID();
			}
			
			@Override
			public String code() {
				return "Math.exp(-x**2)";
			}
		});
		
	}
	
	
	
	

}
