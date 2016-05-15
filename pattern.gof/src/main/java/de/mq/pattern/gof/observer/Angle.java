package de.mq.pattern.gof.observer;

public class Angle {
	
	double rad;
	
	public Angle(){
		this.rad=0;
	}
	
	public Angle(final double rad){
		this.rad=rad;
	}
	
	public final double degreesAsDouble() {
		return 180d*rad/Math.PI;
	}
	
	public final int degrees() {
		return (int) Math.floor(degreesAsDouble());
	}
	
	public final int minutes(){
		return (int)Math.floor((degreesAsDouble()-degrees())*60d);
	}
	
	public final int seconds(){
		return (int)Math.round((degreesAsDouble() - degrees() -  minutes()/60d)*3600d);
	}
	
	public final double rad() {
		return rad;
	}

	public final void assign(final Angle angle) {
		this.rad=angle.rad();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Angle)) {
			return false;
			
		}
		return rad==((Angle) obj).rad;
	}

	@Override
	public int hashCode() {
		return Double.valueOf(rad).hashCode();
	}

	@Override
	public String toString() {
		return  degrees() +"° " + minutes() +"' "  + seconds() +"''";
	}
	
	

	

}
