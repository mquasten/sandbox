package de.mq.pattern.gof.observer;

public interface Aircraft {
	
	public String id();
	
	public Location location();
	
	public void assignLocation(final Location location);
	
	

}
