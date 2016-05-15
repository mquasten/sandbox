package de.mq.pattern.gof.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.mq.pattern.gof.observer.Position;

public class GunControlMarcoImpl implements GunControl {

	private final List<GunControl> commands = new ArrayList<GunControl>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GunControlMarcoImpl(final GunControl ... commands ){
		for(final GunControl gunControl : commands){
			this.commands.add(gunControl);
		}
		Collections.sort((List)this.commands, new  Comparator<Priority>(){

			@Override
			public int compare(Priority p1, Priority p2) {
				return p1.priority()-p2.priority();
				
			}} );

			
		
	}
	
	@Override
	public void execute(final Position position) {
		for(final GunControl gunControl : commands){
			gunControl.execute(position);
		}
		
	}
	
	final List<GunControl> commands() {
		return commands;
	}
	
	
	

}
