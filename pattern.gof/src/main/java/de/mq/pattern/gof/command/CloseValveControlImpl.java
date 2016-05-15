package de.mq.pattern.gof.command;

import java.util.ArrayList;
import java.util.List;

import de.mq.pattern.gof.observer.Position;

public class CloseValveControlImpl implements GunControl,  Priority{

		
	private final List<StatefullValve> valves = new ArrayList<StatefullValve>();
	
	
	
	public CloseValveControlImpl(final StatefullValve ... valves ){
		for(final StatefullValve valve : valves){
			this.valves.add(valve);
		}
	}
	
	
	@Override
	public void execute(final Position position) {
		for(final StatefullValve valve : valves){
			if( ! valve.isOpen()){
				continue;
			}
			
			valve.close();
		}
		
	}


	@Override
	public int priority() {
		return 2;
	}


	
	
	
	

}
