package de.mq.pattern.gof.command;

import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.Position;

public  abstract class AbstractOpenValveControl implements GunControl, Priority {

	private final StatefullValve forwardValve;
	
	private final StatefullValve inverseValve;
	
	private  final Angle lastAngle;
	
	public AbstractOpenValveControl(final StatefullValve forwardValve , final StatefullValve inverseValve, final Angle lastAngle){
		this.forwardValve=forwardValve;
		this.inverseValve=inverseValve;
		this.lastAngle=lastAngle;
	}
	
	
	
	@Override
	public final void execute(final Position position) {
		if( (angle(position).rad() < lastAngle.rad())) {
			forwardValveIsNotOpenGuard();
			inverseValve.open();
			lastAngle.assign(angle(position));
			return ;
		}
		inverseValveIsNotOpenGuard();
		lastAngle.assign(angle(position));
		forwardValve.open();
		
	}



	private void inverseValveIsNotOpenGuard() {
		if( inverseValve.isOpen()){
			throw new IllegalStateException("InverseValve is still open, can't open forwardValve: " + forwardValve);
		}
	}



	private void forwardValveIsNotOpenGuard() {
		if(forwardValve.isOpen()){
			throw new IllegalStateException("ForwardValve is still open, can't open inverseValve: " + inverseValve);
		}
	}
	
	

	protected abstract Angle angle(final Position position);
	

	@Override
	public final  int priority() {
		return 1;
	}
	
	

}
