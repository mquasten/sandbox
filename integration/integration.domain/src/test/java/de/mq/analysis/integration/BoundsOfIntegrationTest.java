package de.mq.analysis.integration;


import org.junit.Assert;
import org.junit.Test;

import de.mq.analysis.integration.BoundsOfIntegration;

public class BoundsOfIntegrationTest {
	
	static final Double LOWER_LIMIT = -1d;
	static final Double UPPER_LIMIT = 1d;
	
	private final BoundsOfIntegration boundsOfIntegration = BoundsOfIntegration.of(LOWER_LIMIT, UPPER_LIMIT);
	
	@Test
	public final void lowerLimit() {
		Assert.assertEquals(LOWER_LIMIT, (Double) boundsOfIntegration.lowerLimit());
		
	}
	
	@Test
	public final void upperLimit() {
		Assert.assertEquals(UPPER_LIMIT, (Double) boundsOfIntegration.upperLimit());
		
	}

}
