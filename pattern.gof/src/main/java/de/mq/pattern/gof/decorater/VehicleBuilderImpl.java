package de.mq.pattern.gof.decorater;

import java.util.ArrayList;
import java.util.List;

public class VehicleBuilderImpl  implements VehicleBuilder{

	private ComponentsAware vehicle;
	
	private final List<String> features = new ArrayList<String>();
	
	public VehicleBuilder withComponents(final ComponentsAware components) {
		ComponentsAware x = components;
	   while (x instanceof VehicleDecoratorImpl) {
	      withDecoration(((AbstractComponent) x).name());
	   	
			x=((VehicleDecoratorImpl) x).parent;
			
		}
		
		if (x instanceof VehicleImpl) {
		    withVehicle(((AbstractComponent)x).name());
		}
		return this;
		
	}
	
	public final VehicleBuilder withoutDecoration(final String name) {
		
		 featureNotExistsGuard(name);
		 features.remove(name);
		 return this; 
	}

	private void featureNotExistsGuard(final String name) {
		if( ! features.contains(name)) {
			 throw new IllegalArgumentException("Feature " + name+  " is not asigned" );
		 }
	}
	
	@Override
	public VehicleBuilder withDecoration(final String decoration) {
		featureAlreadyAssignedGuard(decoration);
		features.add(decoration);
		return this;
	}

	private void featureAlreadyAssignedGuard(final String decoration) {
		if( features.contains(decoration)){
			throw new IllegalArgumentException("Component already assigned");
		}
	}

	@Override
	public VehicleBuilder withVehicle(final String vehicle) {
		vehileAlreadyAssignedGuard();
		this.vehicle=new VehicleImpl(vehicle);
		return this;
	}

	private void vehileAlreadyAssignedGuard() {
		if( this.vehicle != null){
			throw new IllegalArgumentException("Vehile already assigned");
		}
	}

	@Override
	public ComponentsAware build() {
		ComponentsAware decorater = vehicle;
		for(final String feature: features){
			
			decorater=new VehicleDecoratorImpl(feature, decorater);
		}
		return decorater;
	}

}
