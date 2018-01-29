package de.mq.analysis.integration.support;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


import org.springframework.util.Assert;

import de.mq.analysis.integration.IntegrationService;

class DefiniteIntegralController {
	
	private final Map<IntegrationService.CalculationAlgorithm, IntegrationService> algorithms = new HashMap<>();
	
	private final AbstractRealFunctionJRubyScriptEngineFactory scriptEngineFactory; 
	
	DefiniteIntegralController(final Collection<IntegrationService> integrationServices, final AbstractRealFunctionJRubyScriptEngineFactory scriptEngineFactory) {
		algorithms.putAll(integrationServices.stream().map(integrationService -> new AbstractMap.SimpleImmutableEntry<>(integrationService.calculationAlgorithm(), integrationService)).collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
	    this.scriptEngineFactory=scriptEngineFactory;
	}
	
	final void integrate(final DefiniteIntegralAO definiteIntegralAO) {
		Assert.isTrue(algorithms.containsKey(definiteIntegralAO.getCalculationAlgorithm()), "CalculationAlgorithm not found.");
		final double result =  algorithms.get(definiteIntegralAO.getCalculationAlgorithm()).calculate(definiteIntegralAO.getDefiniteIntegral());
		definiteIntegralAO.setResult(result);
		System.out.println(scriptEngineFactory);
		
	}
	
	
	
	

}
