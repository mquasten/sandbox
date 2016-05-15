package de.mq.pattern.gof.decorater;

public interface VehicleBuilder {
	
	VehicleBuilder withVehicle(final String vehicle);
	
	VehicleBuilder withDecoration(final String decoration);
	
	VehicleBuilder withComponents(final ComponentsAware components); 
	
	VehicleBuilder withoutDecoration(final String name);
	
	ComponentsAware build();

}
