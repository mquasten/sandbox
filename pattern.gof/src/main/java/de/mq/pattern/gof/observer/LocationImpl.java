package de.mq.pattern.gof.observer;

public class LocationImpl  implements Location{

	public final Angle latitude;
	
	public final Angle longitude;
	
	public final double altitude;
	
	public LocationImpl(double latitude, double longitude) {
		this(latitude, longitude, 0);
	}
	
	public LocationImpl(Number[] latitudes, Number[] longitudes, double altitude ) {
		latitude=new Angle(degressToDouble(latitudes));
		longitude=new Angle(degressToDouble(longitudes));
		this.altitude=altitude;
	}
	
	public LocationImpl(Number[] latitudes, Number[] longitudes) {
		this(latitudes, longitudes, 0);
	}

	
	public LocationImpl(double latitude, double longitude, double altitude) {
		this.latitude = new Angle(latitude);
		this.longitude = new Angle(longitude);
		this.altitude = altitude;
	}
	
	private double degressToDouble(Number[] results) {
		double result=0;
		for(int i=0; i < results.length; i++){
			result+= results[i].doubleValue()* Math.pow(60, -i);
		}
		return result*Math.PI/180;
	}

	@Override
	public double altitude() {
		return this.altitude;
	}

	@Override
	public Angle latitude() {
		return this.latitude;
	}

	@Override
	public Angle longitude() {
		return this.longitude;
	}

}
