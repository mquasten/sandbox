package de.mq.pattern.gof.observer;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.observer.Location;
import de.mq.pattern.gof.observer.LocationImpl;
import de.mq.pattern.gof.observer.Position;
import de.mq.pattern.gof.observer.PositionCalculator;
import de.mq.pattern.gof.observer.PositionCalculatorImpl;

public class PositionCaculatorTest {

	private PositionCalculator gunPositionCalculator = new PositionCalculatorImpl();

	private Location KR = new LocationImpl(new Number[] { 51, 19, 2 }, new Number[] { 6, 34, 16 });

	private Location LMA = new LocationImpl(new Number[] { 51, 22, 30 }, new Number[] { 6, 24, 5 }, 1000);

	private Location DUE = new LocationImpl(new Number[] { 51, 18, 30 }, new Number[] { 6, 41, 17 }, 1000);
	private Location LI = new LocationImpl(new Number[] { 51, 21, 35 }, new Number[] { 6, 53, 21 }, 1000);

	private Location DY = new LocationImpl(new Number[] { 51, 14, 21 }, new Number[] { 6, 39, 32 }, 1000);

	@Test
	public final void testPositionLMA() {

		Position position = gunPositionCalculator.position(KR, LMA);

		Assert.assertEquals(13425, Math.round(position.distanceOverGround()));
		Assert.assertEquals(-61, Math.round(toDegrees(position.azimuth().rad())));
		Assert.assertEquals(4, Math.round(toDegrees(position.elevation().rad())));
	
	}

	private double toDegrees(double rad) {
		return rad*180/Math.PI;
	}

	@Test
	public final void testPositionDUE() {

		Position position = gunPositionCalculator.position(KR, DUE);

		Assert.assertEquals(8188, Math.round(position.distanceOverGround()));
		Assert.assertEquals(97, Math.round(toDegrees(position.azimuth().rad())));
		Assert.assertEquals(7, Math.round(toDegrees(position.elevation().rad())));
		

	}

	@Test
	public final void testPositionLI() {
		Position position = gunPositionCalculator.position(KR, LI);

		Assert.assertEquals(22594, Math.round(position.distanceOverGround()));
		Assert.assertEquals(78, Math.round(toDegrees(position.azimuth().rad())));
		
		
		Assert.assertEquals(2, Math.round(toDegrees(position.elevation().rad())));
	}

	@Test
	public final void testPositionDY() {
		Position position = gunPositionCalculator.position(KR, DY);

		Assert.assertEquals(10612, Math.round(position.distanceOverGround()));
		Assert.assertEquals(145, Math.round(toDegrees(position.azimuth().rad())));
		Assert.assertEquals(5, Math.round(toDegrees(position.elevation().rad())));

	}

}
