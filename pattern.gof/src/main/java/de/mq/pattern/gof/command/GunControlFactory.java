package de.mq.pattern.gof.command;

import java.util.List;

import de.mq.pattern.gof.observer.Angle;


public  interface GunControlFactory {
	
	GunControl create(final List<String> commands, final Angle towerAngle, final Angle gunElevation);
	
	

}
