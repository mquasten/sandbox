package de.mq.analysis.integration.support;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;

import de.mq.analysis.integration.RealFunction;


public class AbstractIntegrationServiceTest {
	
	private final AbstractIntegrationService integrationService = Mockito.mock(AbstractIntegrationService.class, Mockito.CALLS_REAL_METHODS);

	
	private final DefiniteIntegral definiteIntegral = Mockito.mock(DefiniteIntegral.class); 
	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class); 
	private final RealFunction realFunction = Mockito.mock(RealFunction.class);
	
	@Before
	public final void setup() {
		Mockito.when(definiteIntegral.boundsOfIntegration()).thenReturn(boundsOfIntegration);
		Mockito.when(definiteIntegral.realFunction()).thenReturn(realFunction);
		Mockito.when(definiteIntegral.numberOfSamples()).thenReturn(4L);
		Mockito.when(integrationService.quality()).thenReturn(4);
		
		Mockito.doAnswer(answer -> {
			final DefiniteIntegral definiteIntegral = answer.getArgument(0);
			
			System.out.println(definiteIntegral.numberOfSamples());
			if( definiteIntegral.numberOfSamples()==4L)     {
				return 4.4952;
			}
			if( definiteIntegral.numberOfSamples()==8L){
				return 4.4926d;
			}
			Assert.fail("Unexpected numberOfSamples:" + definiteIntegral.numberOfSamples());
			return 0d; 
			
		}).when(integrationService).resolveIntegral(Mockito.any(DefiniteIntegral.class));
	}
	
	@Test
	public final void calculate() {
		Assert.assertEquals(Double.valueOf(4.4924), cut(integrationService.calculate(definiteIntegral), 4 ));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void calculateWrongQuality() {
		Mockito.when(integrationService.quality()).thenReturn(0);
		integrationService.calculate(definiteIntegral);
	}
	
	private Double cut(double x, int n) {
		final long y = (long) (x * Math.pow(10, n));
		return y / Math.pow(10, n);
	}
	
	
}
