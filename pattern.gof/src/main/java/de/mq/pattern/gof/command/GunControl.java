package de.mq.pattern.gof.command;

import de.mq.pattern.gof.observer.Position;

public interface GunControl {
	
	public void execute(final Position position) ;

}
