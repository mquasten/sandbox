package de.mq.pattern.gof.command;

public class StatefullValveImpl implements  StatefullValve{
	
	private boolean open;
	
	private final Valve valve;
	
	public StatefullValveImpl(final Valve valve) {
		this.valve=valve;
		this.open=false;
	}
	
	
	public boolean isOpen() {
		return open;
	}

	public Valve valve() {
		return valve;
	}

	@Override
	public void close() {
		valve.close();
		open=false;
	}

	@Override
	public void open() {
		valve.open();
		open=true;
	}


	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StatefullValveImpl)) {
			return false;
			
		}
		return this.valve.equals((StatefullValveImpl) obj);
	}


	@Override
	public int hashCode() {
		return valve.hashCode();
	}


	@Override
	public String toString() {
		return valve.toString();
	}


	
	
	
	
}
