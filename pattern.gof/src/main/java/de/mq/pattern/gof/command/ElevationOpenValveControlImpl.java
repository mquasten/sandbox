package de.mq.pattern.gof.command;

import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.Position;

public class ElevationOpenValveControlImpl extends AbstractOpenValveControl{

	public ElevationOpenValveControlImpl(final StatefullValve forwardValve, StatefullValve inverseValve, Angle lastAngle) {
		super(forwardValve, inverseValve, lastAngle);
	}

	@Override
	protected Angle angle(final Position position) {
		return position.elevation()   ;
	}

}
