package de.mq.pattern.gof.decorater;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.decorater.ComponentsAware;
import de.mq.pattern.gof.decorater.VehicleDecoratorImpl;
import de.mq.pattern.gof.decorater.VehicleImpl;

public class VehicleDecoratorTest {
	static final String KPZ = "Tiger";
	static final String HAUPT_WAFFE = "Fugabwehrkanone 8.8 cm";
	static final String BLENDEN_MG = "MG 42 in Blende";
	static final String FLUG_ABWEHR_MG = "MG 42 Lukenlafette Flugabwehr";

	
	@Test
	public final void testDecorator() {
		final ComponentsAware tiger =  new VehicleDecoratorImpl(FLUG_ABWEHR_MG,   new VehicleDecoratorImpl(BLENDEN_MG,  new VehicleDecoratorImpl(HAUPT_WAFFE , new VehicleImpl(KPZ))));
		final List<String> results =  tiger.components();
		
		Assert.assertEquals(4, results.size());
		Assert.assertEquals(KPZ, results.get(0));
		Assert.assertEquals(HAUPT_WAFFE, results.get(1));
		Assert.assertEquals(BLENDEN_MG, results.get(2));
		Assert.assertEquals(FLUG_ABWEHR_MG, results.get(3));
	}
	
	
	

}
