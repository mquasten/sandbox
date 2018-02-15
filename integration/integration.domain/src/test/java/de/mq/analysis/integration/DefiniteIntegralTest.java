package de.mq.analysis.integration;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DefiniteIntegralTest {

	private final DefiniteIntegral definiteIntegral = Mockito.mock(DefiniteIntegral.class);

	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);

	@Before
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
