package de.mq.analysis.integration.support;

import java.util.Observer;

import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.RealFunction;
import junit.framework.Assert;

public class DefiniteIntegralAOTest {
	
	
	private static final double RESULT = 47.11d;
	private static final long NUMBER_OF_SAMPLES = 1000L;
	private static final Double UPPER_LIMIT = 1d;
	private static final Double LOWER_LIMIT = -1d;
	private final DefiniteIntegralAO definiteIntegralAO = new DefiniteIntegralAO();
	private final RealFunction realFunction = Mockito.mock(RealFunction.class);
	
	@Test
	public final void getDefiniteIntegral() {
		
		definiteIntegralAO.setLowerLimit(LOWER_LIMIT);
		definiteIntegralAO.setUpperLimit(UPPER_LIMIT);
		definiteIntegralAO.setRealFunction(realFunction);
		definiteIntegralAO.setNumberOfSamples(NUMBER_OF_SAMPLES);
		
		DefiniteIntegral result = definiteIntegralAO.getDefiniteIntegral();
		Assert.assertEquals(LOWER_LIMIT, result.boundsOfIntegration().lowerLimit());
		Assert.assertEquals(UPPER_LIMIT, result.boundsOfIntegration().upperLimit());
		Assert.assertEquals(NUMBER_OF_SAMPLES, result.numberOfSamples());
		Assert.assertEquals(realFunction, result.realFunction());
	}
	
	@Test
	public final void calculationAlgorithm() {
		definiteIntegralAO.setCalculationAlgorithm(IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertEquals(IntegrationService.CalculationAlgorithm.Simpson, definiteIntegralAO.getCalculationAlgorithm());
	}
	
	@Test
	public final void result() {
		final Observer observer = Mockito.mock(Observer.class);
		definiteIntegralAO.addObserver(observer);
		definiteIntegralAO.setResult(RESULT);
		
		Assert.assertEquals(RESULT, definiteIntegralAO.getResult());
		
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
	}
	
	@Test
	public final void  validate() {
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, realFunction, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertTrue(definiteIntegralAO.validate());
		
		setValues(null, UPPER_LIMIT, realFunction, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, null, realFunction, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, null, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, realFunction, null, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, realFunction, NUMBER_OF_SAMPLES, null);
		Assert.assertFalse(definiteIntegralAO.validate());
		
	}

	private  void setValues(final Double lowerLimit, final Double upperLimit, final RealFunction realFunction, final Long numberOfSamples, final IntegrationService.CalculationAlgorithm calculationAlgorithm) {
		definiteIntegralAO.setLowerLimit(lowerLimit);
		definiteIntegralAO.setUpperLimit(upperLimit);
		definiteIntegralAO.setRealFunction(realFunction);
		definiteIntegralAO.setNumberOfSamples(numberOfSamples);
		definiteIntegralAO.setCalculationAlgorithm(calculationAlgorithm);
	}

}
