package de.mq.pattern.gof.decorater;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.decorater.ComponentsAware;
import de.mq.pattern.gof.decorater.VehicleBuilder;
import de.mq.pattern.gof.decorater.VehicleBuilderImpl;
import de.mq.pattern.gof.decorater.VehicleDecoratorImpl;
import de.mq.pattern.gof.decorater.VehicleImpl;

public class VehicleBuilderTest {
	
	@Test
	public final void testBuilder() {
		final VehicleBuilder builder = new VehicleBuilderImpl();
		
		Assert.assertEquals(builder, builder.withVehicle(VehicleDecoratorTest.KPZ));
		
		
		Assert.assertEquals(builder, builder.withDecoration(VehicleDecoratorTest.HAUPT_WAFFE));
				
		final List<String> results = builder.withDecoration(VehicleDecoratorTest.BLENDEN_MG).withDecoration(VehicleDecoratorTest.FLUG_ABWEHR_MG).build().components();
	
		Assert.assertEquals(4, results.size());
		Assert.assertEquals(VehicleDecoratorTest.KPZ, results.get(0));
		Assert.assertEquals(VehicleDecoratorTest.HAUPT_WAFFE, results.get(1));
		Assert.assertEquals(VehicleDecoratorTest.BLENDEN_MG, results.get(2));
		Assert.assertEquals(VehicleDecoratorTest.FLUG_ABWEHR_MG, results.get(3));
	}
	
	@Test
	public final void testDecoratorWithExisting() {
		final ComponentsAware tiger =  new VehicleDecoratorImpl(VehicleDecoratorTest.FLUG_ABWEHR_MG,   new VehicleDecoratorImpl(VehicleDecoratorTest.BLENDEN_MG,  new VehicleDecoratorImpl(VehicleDecoratorTest.HAUPT_WAFFE , new VehicleImpl(VehicleDecoratorTest.KPZ))));
	 
		Assert.assertEquals(4, tiger.components().size());
		
		
		
		final VehicleBuilder builder = new VehicleBuilderImpl();
		Assert.assertEquals(builder, builder.withComponents(tiger));
		Assert.assertEquals(4, builder.build().components().size());
		
		Assert.assertEquals(builder, builder.withoutDecoration(VehicleDecoratorTest.FLUG_ABWEHR_MG));
		Assert.assertEquals(builder, builder.withoutDecoration(VehicleDecoratorTest.BLENDEN_MG));
		Assert.assertEquals(builder, builder.withoutDecoration(VehicleDecoratorTest.HAUPT_WAFFE));
		
		final List<String> results =  builder.build().components();
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(VehicleDecoratorTest.KPZ, results.iterator().next());
		
	
	}

}
