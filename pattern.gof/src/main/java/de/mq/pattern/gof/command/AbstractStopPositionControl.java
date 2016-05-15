package de.mq.pattern.gof.command;

import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.Position;

public abstract class AbstractStopPositionControl implements GunControl, Priority{

	private final AngleSwitch angleSwitch;
	
	private final double scale ; 
	
	public AbstractStopPositionControl(final AngleSwitch angleSwitch, final double scale) {
		this.angleSwitch=angleSwitch;
		this.scale=scale;
	}
	
	@Override
	public void execute(final Position position) {
		angleSwitch.scale(scale);
		angleSwitch.stopPostion(Math.cos(angle(position).rad()), Math.sin(angle(position).rad()));
	}

	abstract Angle angle(final Position position);
	
	public final int priority(){
		return 0;
	}

}
