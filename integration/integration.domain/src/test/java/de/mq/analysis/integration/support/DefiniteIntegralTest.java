package de.mq.analysis.integration.support;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.RealFunction;

public class DefiniteIntegralTest {

	private static final Long NUMBER_OF_SAMPLES = 100L;
	private final BoundsOfIntegration boundsOfIntegration = Mockito.mock(BoundsOfIntegration.class);
	private final RealFunction realFunction = Mockito.mock(RealFunction.class);

	private final DefiniteIntegral definiteIntegral = new DefiniteIntegralImpl(boundsOfIntegration, realFunction, NUMBER_OF_SAMPLES);

	@Test
	public final void numberOfSamples() {
		assertEquals(Long.valueOf(NUMBER_OF_SAMPLES), Long.valueOf(definiteIntegral.numberOfSamples()));
	}

	@Test
	public final void boundsOfIntegration() {
		assertEquals(boundsOfIntegration, definiteIntegral.boundsOfIntegration());
	}

	@Test
	public final void realFunction() {
		assertEquals(realFunction, definiteIntegral.realFunction());
	}

	
	@Test()
	public final void createWrongNumberOfSamples() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			new DefiniteIntegralImpl(boundsOfIntegration, realFunction, 0);
		});

	}
}
