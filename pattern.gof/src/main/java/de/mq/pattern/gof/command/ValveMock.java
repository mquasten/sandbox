package de.mq.pattern.gof.command;

import java.util.List;

public class ValveMock extends AbstractMock implements Valve {

	
	
	
	public ValveMock(final List<String> commands, final String name) {
		super(commands, name);
	}

	@Override
	public void close() {
		commands.add("Close Valve " +name);
		
	}

	@Override
	public void open() {
		commands.add("Open Valve " +name);
		
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ValveMock)) {
			return false;
		}
		return name.equals(((ValveMock) obj).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "name=" + name;
	}
	
	
	

}
