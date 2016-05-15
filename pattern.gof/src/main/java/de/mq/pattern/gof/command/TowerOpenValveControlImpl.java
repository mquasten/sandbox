package de.mq.pattern.gof.command;

import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.Position;

public class TowerOpenValveControlImpl extends AbstractOpenValveControl {

	public TowerOpenValveControlImpl(final StatefullValve forwardValve, final StatefullValve inverseValve, final Angle lastAngle) {
		super(forwardValve, inverseValve, lastAngle);
	}

	@Override
	protected Angle angle(final Position position) {
		return position.azimuth() ;
	}

}
