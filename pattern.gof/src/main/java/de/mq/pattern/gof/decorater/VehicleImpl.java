package de.mq.pattern.gof.decorater;

import java.util.Collections;
import java.util.List;


public class VehicleImpl extends AbstractComponent implements ComponentsAware {
	
	VehicleImpl(final String name){
		super(name);
	}
	
	
	public final List<String> components() {
		final List<String> components = nameAsList();
		return Collections.unmodifiableList(components);
	}


	

}
