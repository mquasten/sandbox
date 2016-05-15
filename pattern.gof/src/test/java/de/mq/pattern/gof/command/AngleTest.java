package de.mq.pattern.gof.command;

import org.junit.Test;

import de.mq.pattern.gof.observer.Angle;
import junit.framework.Assert;

public class AngleTest {
	
	@Test
	public final void testDegreesAsDouble() {
		Assert.assertEquals(90d, new Angle(Math.PI/2).degreesAsDouble());
	}
	
	
	@Test
	public final void testDegrees() {
		Assert.assertEquals(51, new Angle(51.56789 *Math.PI/180d).degrees());
	}
	
	@Test
	public final void testMinutes() {
		Assert.assertEquals(34, new Angle(51.56789 *Math.PI/180d).minutes());
	}
	
	@Test
	public final void testSeconds() {
		Assert.assertEquals(4, new Angle(51.56789 *Math.PI/180d).seconds());
	}
	
	public final void testToString() {
		Assert.assertEquals("51° 34' 4''" , new Angle(51.56789 *Math.PI/180d));
	}

}
