package de.mq.pattern.gof.observer;

import java.util.Map;


public interface TargetsAware {
	
	public Map<Aircraft,Position> targets();

}
