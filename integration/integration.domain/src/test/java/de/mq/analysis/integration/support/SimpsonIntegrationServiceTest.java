package de.mq.analysis.integration.support;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.RealFunction;




public class SimpsonIntegrationServiceTest {
	
private final RealFunction realFunction = Mockito.mock(RealFunction.class);
	
	private final DefiniteIntegral definiteIntegral = Mockito.mock(DefiniteIntegral.class);
	
	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);
	
	final IntegrationService integrationService = new SimpsonIntegrationServiceImpl();
	
	@Before
	public final void setup() {
		
		Mockito.when(boundsOfIntegration.lowerLimit()).thenReturn(1d);
		Mockito.when(boundsOfIntegration.upperLimit()).thenReturn(2.6d);
		Mockito.doAnswer(answer -> {
			final double x = answer.getArgument(0); 
			return  Math.sqrt(1+Math.exp(0.5*Math.pow(x, 2)));
		}).when(realFunction).f(Mockito.anyDouble());
		
		Mockito.when(definiteIntegral.boundsOfIntegration()).thenReturn(boundsOfIntegration);
		Mockito.when(definiteIntegral.numberOfSamples()).thenReturn(4L);
		Mockito.when(definiteIntegral.realFunction()).thenReturn(realFunction);
		Mockito.doCallRealMethod().when(definiteIntegral).stepSize();
		
	}
	
	
	@Test
	public final void calculate() {
		
		Assert.assertEquals(Double.valueOf(4.4924d), cut(integrationService.calculate(definiteIntegral),4));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void calculateDoublyEven(){
		Mockito.when(definiteIntegral.numberOfSamples()).thenReturn(5L);
		integrationService.calculate(definiteIntegral);
	}
	
	private Double cut(final double x, final int n) {
		final long y = Math.round( (x * Math.pow(10, n)));
		return y / Math.pow(10, n);
	}
	
	@Test
	public final void calculationAlgorithm() {
		Assert.assertEquals(IntegrationService.CalculationAlgorithm.Simpson, integrationService.calculationAlgorithm());
	}

}
