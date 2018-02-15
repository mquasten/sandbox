package de.mq.analysis.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoundsOfIntegrationTest {

	static final Double LOWER_LIMIT = -1d;
	static final Double UPPER_LIMIT = 1d;

	private final BoundsOfIntegration boundsOfIntegration = BoundsOfIntegration.of(LOWER_LIMIT, UPPER_LIMIT);

	@Test
	public final void lowerLimit() {
		assertEquals(LOWER_LIMIT, (Double) boundsOfIntegration.lowerLimit());

	}

	@Test
	public final void upperLimit() {
		assertEquals(UPPER_LIMIT, (Double) boundsOfIntegration.upperLimit());

	}

}
