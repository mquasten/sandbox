package de.mq.pattern.gof.observer.gui;

import de.mq.pattern.gof.observer.Location;
import de.mq.pattern.gof.observer.LocationImpl;

public enum Locations {

	KR(new LocationImpl(new Number[] { 51, 19, 2 }, new Number[] { 6, 34, 16 },500)),
	LMA(new LocationImpl(new Number[] { 51, 22, 30 }, new Number[] { 6, 24, 5 },1000)),

	DUE(new LocationImpl(new Number[] { 51, 18, 30 }, new Number[] { 6, 41, 17 },1500)),
	LI( new LocationImpl(new Number[] { 51, 21, 35 }, new Number[] { 6, 53, 21 },2000)),

	DY(new LocationImpl(new Number[] { 51, 14, 21 }, new Number[] { 6, 39, 32 },2500)),
	
	LONDON(new LocationImpl( new Number[] { 51, 30, 0 }, new Number[] { 0, 0, 0 },3000));
	
	
	private Location location;
	 Locations(final Location location ) {
		this.location=location;
	 }
	 
	 public final Location location() {
		 return location;
	 }
}