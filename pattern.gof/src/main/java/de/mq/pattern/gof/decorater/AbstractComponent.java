package de.mq.pattern.gof.decorater;

import java.util.ArrayList;
import java.util.List;


abstract class AbstractComponent implements ComponentsAware {

	private final String name;

	AbstractComponent(final String name) {
		this.name=name;
	}
	
	
	protected final  List<String> nameAsList() {
		final List<String> components = new ArrayList<String>();
		components.add(name);
		return components;
	}


	@Override
	public abstract List<String> components();
	
	
	final protected String name() {
		return  name;
	}
	
	

}