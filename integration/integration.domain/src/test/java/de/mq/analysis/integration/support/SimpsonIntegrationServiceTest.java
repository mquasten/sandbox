package de.mq.analysis.integration.support;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.RealFunction;




public class SimpsonIntegrationServiceTest {
	
private final RealFunction realFunction = Mockito.mock(RealFunction.class);
	
	private final DefiniteIntegral definiteIntegral = Mockito.mock(DefiniteIntegral.class);
	
	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);
	
	final AbstractIntegrationService integrationService = new SimpsonIntegrationServiceImpl();
	
	@BeforeEach
	public final void setup() {
		
		Mockito.when(boundsOfIntegration.lowerLimit()).thenReturn(1d);
		Mockito.when(boundsOfIntegration.upperLimit()).thenReturn(2.6d);
		Mockito.doAnswer(answer -> {
			final double x = answer.getArgument(0); 
			return  Math.sqrt(1+Math.exp(0.5*Math.pow(x, 2)));
		}).when(realFunction).f(Mockito.anyDouble());
		
		Mockito.when(definiteIntegral.boundsOfIntegration()).thenReturn(boundsOfIntegration);
		Mockito.when(definiteIntegral.numberOfSamples()).thenReturn(8L);
		Mockito.when(definiteIntegral.realFunction()).thenReturn(realFunction);
		Mockito.doCallRealMethod().when(definiteIntegral).stepSize();
		
	}
	
	
	@Test
	public final void resolve() {
		
		assertEquals(Double.valueOf(4.4926d), cut(integrationService.resolveIntegral(definiteIntegral),4));
	}
	
	@Test()
	public final void inputParameterGuard(){
		Mockito.when(definiteIntegral.numberOfSamples()).thenReturn(5L);
		assertThrows(IllegalArgumentException.class, () -> {
		integrationService.inputParameterGuard(definiteIntegral);
		});
	}
	
	private Double cut(final double x, final int n) {
		final long y = Math.round( (x * Math.pow(10, n)));
		return y / Math.pow(10, n);
	}
	
	@Test
	public final void calculationAlgorithm() {
		assertEquals(IntegrationService.CalculationAlgorithm.Simpson, integrationService.calculationAlgorithm());
	}
	
	@Test
	public final void quality() {
		assertEquals(4, integrationService.quality());
	}

}
