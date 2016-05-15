package de.mq.pattern.gof.observer;

import java.util.HashSet;
import java.util.Set;

public class AircraftSubjectImpl implements Subject, Aircraft {

	private Aircraft aircraft;

	private final Set<Observer> observers = new HashSet<Observer>();

	public AircraftSubjectImpl(final Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	@Override
	public void addObserver(final Observer observer) {	
		observers.add(observer);
	}

	@Override
	public void removeObserver(final Observer observer) {
		observers.remove(observer);

	}

	private void updateObserver() {
		for (final Observer observer : observers) {
			observer.update(this);
		}

	}

	@Override
	public void assignLocation(final Location location) {
		aircraft.assignLocation(location);
		updateObserver();

	}

	@Override
	public String id() {
		return aircraft.id();
	}

	@Override
	public Location location() {
		return aircraft.location();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Aircraft)) {
			return false;
			
		}
		return this.id().equals(((Aircraft) obj).id());
	
	}

	@Override
	public int hashCode() {
		return this.id().hashCode();
	}

	@Override
	public String toString() {
		return "Aircraft-Observable id=" + id();
	}
	
	

}
