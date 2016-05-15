package de.mq.pattern.gof.decorater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VehicleDecoratorImpl extends AbstractComponent{

	final ComponentsAware parent;
	
	VehicleDecoratorImpl(final String name, final ComponentsAware component ) {
		super(name);
		this.parent=component;
	}
	
	@Override
	public final  List<String> components() {
		final List<String> components = new ArrayList<String>();
		components.addAll(parent.components());
		components.addAll(nameAsList());
		return Collections.unmodifiableList(components);
	}
	
	

}
