package de.mq.pattern.gof.observer;

public interface PositionCalculator {

	Position position(final Location gunLocation, final Location targetLocation);

}