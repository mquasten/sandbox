package de.mq.pattern.gof.command;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.mq.pattern.gof.command.StatefullValve;
import de.mq.pattern.gof.command.StatefullValveImpl;
import de.mq.pattern.gof.command.ValveMock;

public class StatefullValveTest {
	
	
	@Test
	public final void testOpen() {
		final List<String> commands = new ArrayList<String>();
		StatefullValve statefullValve = new StatefullValveImpl(new ValveMock(commands, "Gun Up"));
		Assert.assertFalse(statefullValve.isOpen());
		statefullValve.open();
		Assert.assertFalse(commands.isEmpty());
		Assert.assertEquals("Open Valve Gun Up", commands.get(0));
		Assert.assertTrue(statefullValve.isOpen());
		
	}
	
	@Test
	public final void testClose() {
		final List<String> commands = new ArrayList<String>();
		StatefullValve statefullValve = new StatefullValveImpl(new ValveMock(commands, "Gun Up"));
		
		statefullValve.open();
		Assert.assertTrue(statefullValve.isOpen());
		commands.clear();
		
		statefullValve.close();
		Assert.assertFalse(commands.isEmpty());
		
		Assert.assertEquals("Close Valve Gun Up", commands.get(0));
		Assert.assertFalse(statefullValve.isOpen());
		
	}

}
