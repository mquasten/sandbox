package de.mq.pattern.gof.observer;

public class Mig29 implements Aircraft{

	private Location location=new LocationImpl(0,0,0);
	
	private final String id;
	
	public Mig29(int identification){
		id = "Mig29-" +identification;
	}
	
	@Override
	public void assignLocation(final Location location) {
		this.location=location;
	}

	@Override
	public String id() {
		
		return id;
	}

	@Override
	public Location location() {
		return location;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Aircraft)) {
			return false;
		}
		return id.equals(((Aircraft) obj).id());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "id=" + id;
	}
	
	
	

}
