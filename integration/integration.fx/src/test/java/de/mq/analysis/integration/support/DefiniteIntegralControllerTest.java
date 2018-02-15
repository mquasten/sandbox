package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.RealFunction;
import de.mq.analysis.integration.Script;


public class DefiniteIntegralControllerTest {
	
	private static final String CODE = "exp(-x**2";

	private static final long NUMBER_OF_SAMPLES = 10000L;

	private static final Double UPPER_LIMIT = 1d;

	private static final Double LOWER_LIMIT = -1d;

	private static final Result result = Mockito.mock(Result.class);

	private final IntegrationService integrationService = Mockito.mock(IntegrationService.class);
	
	private  DefiniteIntegralController definiteIntegralController;
	private final RealFunctionJRubyScriptEngineFactory scriptEngineFactory = Mockito.mock(RealFunctionJRubyScriptEngineFactory.class);
	
	private final DefiniteIntegralAO definiteIntegralAO = Mockito.mock(DefiniteIntegralAO.class);
	private Script script = Mockito.mock(Script.class);

	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);
	
	private final RealFunction realFunction = Mockito.mock(RealFunction.class);
	
	private ArgumentCaptor<DefiniteIntegral> definiteIntegralArgumentCaptor = ArgumentCaptor.forClass(DefiniteIntegral.class);
	
	@Before
	public final void setup() {
		Mockito.when(script.code()).thenReturn(CODE);
		Mockito.when(boundsOfIntegration.lowerLimit()).thenReturn(LOWER_LIMIT);
		Mockito.when(boundsOfIntegration.upperLimit()).thenReturn(UPPER_LIMIT);
		Mockito.when(definiteIntegralAO.getScript()).thenReturn(script);
		Mockito.when(scriptEngineFactory.realFunction(CODE)).thenReturn(realFunction);
		Mockito.when(definiteIntegralAO.getBoundsOfIntegration()).thenReturn(boundsOfIntegration);
	  	Mockito.when(definiteIntegralAO.getNumberOfSamples()).thenReturn(NUMBER_OF_SAMPLES);
		Mockito.when(integrationService.calculationAlgorithm()).thenReturn(IntegrationService.CalculationAlgorithm.Trapezoid);
	  	Mockito.when(integrationService.calculate(definiteIntegralArgumentCaptor.capture())).thenReturn(result);
	    Mockito.when(definiteIntegralAO.getCalculationAlgorithm()).thenReturn(IntegrationService.CalculationAlgorithm.Trapezoid);
	  	
	    definiteIntegralController = new DefiniteIntegralController(Arrays.asList(integrationService), scriptEngineFactory);
	 
	}
	
	@Test
	public final void  integrate() {
		definiteIntegralController.integrate(definiteIntegralAO);
		
		assertEquals(realFunction, definiteIntegralArgumentCaptor.getValue().realFunction());
		assertEquals(LOWER_LIMIT, Double.valueOf(definiteIntegralArgumentCaptor.getValue().boundsOfIntegration().lowerLimit()));
		assertEquals(UPPER_LIMIT, Double.valueOf(definiteIntegralArgumentCaptor.getValue().boundsOfIntegration().upperLimit()));
		assertEquals(NUMBER_OF_SAMPLES, definiteIntegralArgumentCaptor.getValue().numberOfSamples());
		Mockito.verify(definiteIntegralAO).setResult(result);
	}
}
