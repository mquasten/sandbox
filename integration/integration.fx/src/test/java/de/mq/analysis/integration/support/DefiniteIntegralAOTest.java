package de.mq.analysis.integration.support;

import java.util.Observer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.IntegrationService;

import de.mq.analysis.integration.Script;


public class DefiniteIntegralAOTest {
	
	
	private static final Double RESULT = 47.11d;
	private static final long NUMBER_OF_SAMPLES = 1000L;
	private static final Double UPPER_LIMIT = 1d;
	private static final Double LOWER_LIMIT = -1d;
	private final DefiniteIntegralAO definiteIntegralAO = new DefiniteIntegralAO();
	private final Script script = Mockito.mock(Script.class);
	
	
	@Test
	public final void boundsOfIntegration() {
		
		definiteIntegralAO.setLowerLimit(LOWER_LIMIT);
		definiteIntegralAO.setUpperLimit(UPPER_LIMIT);
		
		definiteIntegralAO.setNumberOfSamples(NUMBER_OF_SAMPLES);
		
		BoundsOfIntegration result = definiteIntegralAO.getBoundsOfIntegration();
		Assert.assertEquals(LOWER_LIMIT, Double.valueOf(result.lowerLimit()));
		Assert.assertEquals(UPPER_LIMIT, Double.valueOf(result.upperLimit()));
		
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
		
		Assert.assertEquals(RESULT, Double.valueOf(definiteIntegralAO.getResult()));
		
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
	}
	
	
	@Test
	public final void  validate() {
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, script, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertTrue(definiteIntegralAO.validate());
		
		setValues(null, UPPER_LIMIT, script, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, null, script, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, null, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, script, null, IntegrationService.CalculationAlgorithm.Simpson);
		Assert.assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, script, NUMBER_OF_SAMPLES, null);
		Assert.assertFalse(definiteIntegralAO.validate());
		
	}

	private  void setValues(final Double lowerLimit, final Double upperLimit, final Script script, final Long numberOfSamples, final IntegrationService.CalculationAlgorithm calculationAlgorithm) {
		definiteIntegralAO.setLowerLimit(lowerLimit);
		definiteIntegralAO.setUpperLimit(upperLimit);
		definiteIntegralAO.setScript(script);
		definiteIntegralAO.setNumberOfSamples(numberOfSamples);
		definiteIntegralAO.setCalculationAlgorithm(calculationAlgorithm);
	}
	
	@Test
	public final void script() {
		final Observer observer = Mockito.mock(Observer.class);
		definiteIntegralAO.addObserver(observer);
		
		definiteIntegralAO.setScript(script);
		Assert.assertEquals(script, definiteIntegralAO.getScript());
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
	}
	
	@Test
	public final void  numberOfSamples() {
		definiteIntegralAO.setNumberOfSamples(NUMBER_OF_SAMPLES);
		Assert.assertEquals(NUMBER_OF_SAMPLES, definiteIntegralAO.getNumberOfSamples());
	}
	
	@Test
	public final void hasResult() {
		Assert.assertFalse(definiteIntegralAO.hasResult());
		definiteIntegralAO.setResult(RESULT);
		Assert.assertTrue(definiteIntegralAO.hasResult());
	}
	
	@Test
	public final void hasScript() {
		Assert.assertFalse(definiteIntegralAO.hasScript());
		definiteIntegralAO.setScript(script);
		Assert.assertTrue(definiteIntegralAO.hasScript());
	}

}
