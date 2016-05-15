package de.mq.pattern.gof.command;

import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.Position;

public class ElevationStopPositionControlImpl extends AbstractStopPositionControl{

	public ElevationStopPositionControlImpl(final AngleSwitch angleSwitch, final double scale) {
		super(angleSwitch, scale);
	}

	@Override
	Angle angle(final Position position) {
		return position.elevation() /* Math.PI / 180 */;
	}

}
