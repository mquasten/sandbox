package de.mq.pattern.gof.observer;



public interface Observer  {
	void update(final Subject subject);
	
	void assign(final Subject subject); 
	
	void unassign(final Subject subject);
}
