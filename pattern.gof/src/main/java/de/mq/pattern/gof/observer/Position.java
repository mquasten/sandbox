package de.mq.pattern.gof.observer;

public interface Position {
	
	double distanceOverGround();
	
	double distanceToTarget();
	
	Angle azimuth();
	
	Angle elevation();
	
	

}
