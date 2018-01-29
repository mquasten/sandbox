package de.mq.analysis.integration.support;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;

public class DefiniteIntegralControllerTest {
	
	private static final Double RESULT = 47.11d;

	private final IntegrationService integrationService = Mockito.mock(IntegrationService.class);
	
	private  DefiniteIntegralController definiteIntegralController;
	private final AbstractRealFunctionJRubyScriptEngineFactory scriptEngineFactory = Mockito.mock(AbstractRealFunctionJRubyScriptEngineFactory.class);
	
	private final DefiniteIntegralAO definiteIntegralAO = Mockito.mock(DefiniteIntegralAO.class);
	private final  DefiniteIntegral definiteIntegral = Mockito.mock(DefiniteIntegral.class);

	@Before
	public final void setup() {
	
		Mockito.when(definiteIntegralAO.getDefiniteIntegral()).thenReturn(definiteIntegral);
	  	Mockito.when(integrationService.calculationAlgorithm()).thenReturn(IntegrationService.CalculationAlgorithm.Trapezoid);
	  	Mockito.when(integrationService.calculate(Mockito.any(DefiniteIntegral.class))).thenReturn(RESULT);
	    Mockito.when(definiteIntegralAO.getCalculationAlgorithm()).thenReturn(IntegrationService.CalculationAlgorithm.Trapezoid);
	  	definiteIntegralController = new DefiniteIntegralController(Arrays.asList(integrationService), scriptEngineFactory);
	 
	}
	
	@Test
	public final void  integrate() {
		definiteIntegralController.integrate(definiteIntegralAO);
		
		Mockito.verify(definiteIntegralAO).setResult(RESULT);
	}
}
