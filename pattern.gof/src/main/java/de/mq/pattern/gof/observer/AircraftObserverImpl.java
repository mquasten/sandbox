package de.mq.pattern.gof.observer;

import java.util.HashMap;
import java.util.Map;

public class AircraftObserverImpl implements Observer, TargetsAware {

	private final Map<Aircraft, Position> positions = new HashMap<Aircraft, Position>();

	private final PositionCalculator gunPositionCalculator;

	private final Location gunLocation;

	private final double maxDstance;

	public AircraftObserverImpl(final PositionCalculator positionCalculator, final Location gunLocation, final double maxDstance) {
		this.gunPositionCalculator = positionCalculator;
		this.gunLocation = gunLocation;
		this.maxDstance = maxDstance;
	}

	@Override
	public void update(final Subject subject) {
		
		
		if (!(subject instanceof Aircraft)) {
			return;
		}
		
		final Aircraft aircraft = (Aircraft) subject;

		final Position position = gunPositionCalculator.position(gunLocation, aircraft.location());
		
		
		
		if (position.distanceToTarget() > maxDstance) {
			positions.remove(aircraft);
		} else {
			positions.put(aircraft, position);
		}

	}
	

	@Override
	public void assign(final Subject subject) {
		subject.addObserver(this);
	}
	
	public final Map<Aircraft, Position> targets() {
		return positions;
	}

	@Override
	public void unassign(Subject subject) {
		positions.remove(subject);
		subject.removeObserver(this);
		
	}

	

	
	
	

}
