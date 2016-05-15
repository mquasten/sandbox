package de.mq.pattern.gof.observer;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.observer.Aircraft;
import de.mq.pattern.gof.observer.AircraftObserverImpl;
import de.mq.pattern.gof.observer.AircraftSubjectImpl;
import de.mq.pattern.gof.observer.Location;
import de.mq.pattern.gof.observer.LocationImpl;
import de.mq.pattern.gof.observer.Mig29;
import de.mq.pattern.gof.observer.Observer;
import de.mq.pattern.gof.observer.Position;
import de.mq.pattern.gof.observer.PositionCalculator;
import de.mq.pattern.gof.observer.PositionCalculatorImpl;
import de.mq.pattern.gof.observer.Subject;
import de.mq.pattern.gof.observer.TargetsAware;

public class ObserverTest {
	
	private PositionCalculator positionCalculator = new PositionCalculatorImpl();

	
	private Location KR = new LocationImpl(new Number[] { 51, 19, 2 }, new Number[] { 6, 34, 16 });

	private Location LMA = new LocationImpl(new Number[] { 51, 22, 30 }, new Number[] { 6, 24, 5 }, 1000);

	private Location DUE = new LocationImpl(new Number[] { 51, 18, 30 }, new Number[] { 6, 41, 17 }, 1000);
	private Location LI = new LocationImpl(new Number[] { 51, 21, 35 }, new Number[] { 6, 53, 21 }, 1000);

	private Location DY = new LocationImpl(new Number[] { 51, 14, 21 }, new Number[] { 6, 39, 32 }, 1000);
	
	private Location LONDON = new LocationImpl(new Number[] { 51, 30, 0 }, new Number[] { 0, 0, 0 }, 1000);
	
	@Test
	public final void testObserver() {
		final Subject subjectMig1 = new AircraftSubjectImpl(new Mig29(1));
		final Subject subjectMig2 = new AircraftSubjectImpl(new Mig29(2));
		final Observer observer = new AircraftObserverImpl(positionCalculator,KR, 100e3);
		observer.assign(subjectMig1);
		observer.assign(subjectMig2);
		
		Assert.assertEquals(0, ((TargetsAware)observer).targets().size());
		
		((Aircraft) subjectMig1).assignLocation(LMA);
		((Aircraft) subjectMig2).assignLocation(DUE);
		
		Assert.assertEquals(2, ((TargetsAware)observer).targets().size());
		
		checkPosition(((TargetsAware)observer).targets().get(subjectMig1), 13425, -61, 4);
		checkPosition(((TargetsAware)observer).targets().get(subjectMig2), 8188, 97, 7);
		
	
		((Aircraft) subjectMig1).assignLocation(LI);
		((Aircraft) subjectMig2).assignLocation(DY);
		
		checkPosition(((TargetsAware)observer).targets().get(subjectMig1), 22594,78, 2);
		checkPosition(((TargetsAware)observer).targets().get(subjectMig2), 10612, 145, 5);
		
		// Abschuss
		observer.unassign(subjectMig2);
		
		Assert.assertEquals(1, ((TargetsAware)observer).targets().size());
		
		//entkommen
		((Aircraft) subjectMig1).assignLocation(LONDON);
		
		Assert.assertEquals(0, ((TargetsAware)observer).targets().size());
		
		// Absturz nach Vernichtung, nicht mehr unter Beobachtung
		((Aircraft) subjectMig2).assignLocation(new LocationImpl(new Number[] { 51, 21, 35 }, new Number[] { 6, 53, 21 }, 0));
		
		Assert.assertEquals(0, ((TargetsAware)observer).targets().size());
		
		
		// gepennt, wieder da
		((Aircraft) subjectMig1).assignLocation(new LocationImpl(new Number[] { 51, 19, 2 }, new Number[] { 6, 34, 16 }, 1000));
		
		Assert.assertEquals(1, ((TargetsAware)observer).targets().size());
		checkPosition(((TargetsAware)observer).targets().get(subjectMig1), 0,0, 90);
		
		
	}

	private void checkPosition(Position positionForMig, int distance, int angle, int elevation) {
		
		Assert.assertEquals(distance, Math.round(positionForMig.distanceOverGround()));
		Assert.assertEquals(angle, Math.round(positionForMig.azimuth().rad()*180/Math.PI));
		Assert.assertEquals(elevation, Math.round(positionForMig.elevation().rad()*180/Math.PI));
	}

}
