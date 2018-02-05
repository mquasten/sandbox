package de.mq.analysis.integration.support;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;

import de.mq.analysis.integration.RealFunction;


public class AbstractIntegrationServiceTest {
	
	private static final double I2N = 4.4926d;


	private static final double IN = 4.4952;


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
			
			if( definiteIntegral.numberOfSamples()==4L)     {
				return IN;
			}
			if( definiteIntegral.numberOfSamples()==8L){
				return I2N;
			}
			Assert.fail("Unexpected numberOfSamples:" + definiteIntegral.numberOfSamples());
			return 0d; 
			
		}).when(integrationService).resolveIntegral(Mockito.any(DefiniteIntegral.class));
	}
	
	@Test
	public final void calculate() {
		final Result result = integrationService.calculate(definiteIntegral);
		Assert.assertEquals( Double.valueOf(I2N+ 1d/15d *(I2N- IN)), Double.valueOf(result.value()));
		Assert.assertEquals(Double.valueOf(1d/15d * (I2N-IN)), Double.valueOf(result.error()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void calculateWrongQuality() {
		Mockito.when(integrationService.quality()).thenReturn(0);
		integrationService.calculate(definiteIntegral);
	}
	
	
}
