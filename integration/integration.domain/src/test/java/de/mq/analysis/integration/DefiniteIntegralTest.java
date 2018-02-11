package de.mq.analysis.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DefiniteIntegralTest {
	
	private final DefiniteIntegral definiteIntegral = Mockito.mock(DefiniteIntegral.class);
	
	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);
	
	
	@BeforeEach
	public final void setup() {
	Mockito.when(boundsOfIntegration.lowerLimit()).thenReturn(1d);
	Mockito.when(boundsOfIntegration.upperLimit()).thenReturn(2d);
	Mockito.when(definiteIntegral.boundsOfIntegration()).thenReturn(boundsOfIntegration);
	Mockito.when(definiteIntegral.stepSize()).thenCallRealMethod();
	Mockito.when(definiteIntegral.numberOfSamples()).thenReturn(100L);
	}
	
	
	@Test
	public final void stepSize() {
		assertEquals(Double.valueOf(1e-2), Double.valueOf(definiteIntegral.stepSize()));
	}

}
