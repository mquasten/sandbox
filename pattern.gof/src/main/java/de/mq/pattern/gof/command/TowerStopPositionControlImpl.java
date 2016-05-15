package de.mq.pattern.gof.command;

import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.Position;

public class TowerStopPositionControlImpl  extends AbstractStopPositionControl{

	public TowerStopPositionControlImpl(AngleSwitch angleSwitch, double scale) {
		super(angleSwitch, scale);
	}

	@Override
	Angle angle(final Position position) {
		return position.azimuth();
	}

}
