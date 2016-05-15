package de.mq.pattern.gof.decorater;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class VehicleTest {
	
	private static final String NAME = "Tiger";

	@Test
	public final void testVehicle() {
		final ComponentsAware component = new VehicleImpl(NAME);
		
		final List<String> results = component.components();
		
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(NAME, results.get(0));
	}
	
	
	

}
