package de.mq.pattern.gof.command;

import java.util.List;

public class AbstractMock {

protected final List<String> commands;
	
	protected final String name;
	
	public AbstractMock(final List<String> commands, final String name) {
		this.commands=commands;
		this.name=name;
	}

}