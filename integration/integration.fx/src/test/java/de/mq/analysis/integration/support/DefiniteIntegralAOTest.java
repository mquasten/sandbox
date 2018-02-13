package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Observer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.IntegrationService;

import de.mq.analysis.integration.Script;


class DefiniteIntegralAOTest {
	
	
	private static final String ERROR_MESSAGE = "Operation sucks.";
	private static final Result result = Mockito.mock(Result.class);
	private static final long NUMBER_OF_SAMPLES = 1000L;
	private static final Double UPPER_LIMIT = 1d;
	private static final Double LOWER_LIMIT = -1d;
	private final DefiniteIntegralAO definiteIntegralAO = new DefiniteIntegralAO();
	private final Script script = Mockito.mock(Script.class);
	
	
	@Test
	final void boundsOfIntegration() {
		
		definiteIntegralAO.setLowerLimit(LOWER_LIMIT);
		definiteIntegralAO.setUpperLimit(UPPER_LIMIT);
		
		definiteIntegralAO.setNumberOfSamples(NUMBER_OF_SAMPLES);
		
		BoundsOfIntegration result = definiteIntegralAO.getBoundsOfIntegration();
		assertEquals(LOWER_LIMIT, Double.valueOf(result.lowerLimit()));
		assertEquals(UPPER_LIMIT, Double.valueOf(result.upperLimit()));
		
	}
	
	@Test
	final void calculationAlgorithm() {
		definiteIntegralAO.setCalculationAlgorithm(IntegrationService.CalculationAlgorithm.Simpson);
		assertEquals(IntegrationService.CalculationAlgorithm.Simpson, definiteIntegralAO.getCalculationAlgorithm());
	}
	
	@Test
	final void result() {
		
		setErrorMessage();
		
		final Observer observer = Mockito.mock(Observer.class);
		definiteIntegralAO.addObserver(observer);
		definiteIntegralAO.setResult(result);
		
		assertEquals(result, definiteIntegralAO.getResult());
		assertNull(definiteIntegralAO.getErrorMessage());
		
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
	}

	private void setErrorMessage() {
		ReflectionTestUtils.setField(definiteIntegralAO, "errorMessage" , ERROR_MESSAGE);
	}
	
	
	@Test
	final void  validate() {
		assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, script, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		assertTrue(definiteIntegralAO.validate());
		
		setValues(null, UPPER_LIMIT, script, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, null, script, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, null, NUMBER_OF_SAMPLES, IntegrationService.CalculationAlgorithm.Simpson);
		assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, script, null, IntegrationService.CalculationAlgorithm.Simpson);
		assertFalse(definiteIntegralAO.validate());
		
		setValues(LOWER_LIMIT, UPPER_LIMIT, script, NUMBER_OF_SAMPLES, null);
		assertFalse(definiteIntegralAO.validate());
		
	}

	void setValues(final Double lowerLimit, final Double upperLimit, final Script script, final Long numberOfSamples, final IntegrationService.CalculationAlgorithm calculationAlgorithm) {
		definiteIntegralAO.setLowerLimit(lowerLimit);
		definiteIntegralAO.setUpperLimit(upperLimit);
		definiteIntegralAO.setScript(script);
		definiteIntegralAO.setNumberOfSamples(numberOfSamples);
		definiteIntegralAO.setCalculationAlgorithm(calculationAlgorithm);
	}
	
	@Test
	final void script() {
		setErrorMessage();
		final Observer observer = Mockito.mock(Observer.class);
		definiteIntegralAO.addObserver(observer);
		
		definiteIntegralAO.setScript(script);
		assertEquals(script, definiteIntegralAO.getScript());
		assertNull(definiteIntegralAO.getErrorMessage());
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
	}
	
	@Test
	final void  numberOfSamples() {
		definiteIntegralAO.setNumberOfSamples(NUMBER_OF_SAMPLES);
		assertEquals(NUMBER_OF_SAMPLES, definiteIntegralAO.getNumberOfSamples());
	}
	
	@Test
	final void hasResult() {
		assertFalse(definiteIntegralAO.hasResult());
		definiteIntegralAO.setResult(result);
		assertTrue(definiteIntegralAO.hasResult());
	}
	
	@Test
	final void hasScript() {
		assertFalse(definiteIntegralAO.hasScript());
		definiteIntegralAO.setScript(script);
		assertTrue(definiteIntegralAO.hasScript());
	}
	
	@Test
	final void errorMessage() {
		assertNull(definiteIntegralAO.getErrorMessage());
		final Observer observer = Mockito.mock(Observer.class);
		definiteIntegralAO.addObserver(observer);
		
		
		definiteIntegralAO.setErrorMessage(ERROR_MESSAGE);
		
		assertEquals(ERROR_MESSAGE, definiteIntegralAO.getErrorMessage());
		Mockito.verify(observer).update(Mockito.any(), Mockito.any());
	}
	
	
	@Test
	final void update( ) {
		final Observer observer = Mockito.mock(Observer.class);
		final ScriptAO scriptAO = Mockito.mock(ScriptAO.class);
		Mockito.when(scriptAO.getSelectedScript()).thenReturn(script);
		definiteIntegralAO.setResult(result);
		assertFalse(definiteIntegralAO.hasScript());
		assertTrue(definiteIntegralAO.hasResult());
		
		definiteIntegralAO.addObserver(observer);
		definiteIntegralAO.update(scriptAO, null);
		
		assertEquals(script, definiteIntegralAO.getScript());
		assertFalse(definiteIntegralAO.hasResult());
		Mockito.verify(observer, Mockito.atLeast(1)).update(Mockito.any(), Mockito.any());
	}

}
