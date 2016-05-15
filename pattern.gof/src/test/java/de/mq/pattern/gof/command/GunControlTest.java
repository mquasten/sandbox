package de.mq.pattern.gof.command;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;


import org.junit.Test;

import de.mq.pattern.gof.command.AngleSwitchMock;
import de.mq.pattern.gof.command.CloseValveControlImpl;
import de.mq.pattern.gof.command.ElevationOpenValveControlImpl;
import de.mq.pattern.gof.command.ElevationStopPositionControlImpl;
import de.mq.pattern.gof.command.GunControl;
import de.mq.pattern.gof.command.StatefullValveImpl;
import de.mq.pattern.gof.command.TowerOpenValveControlImpl;
import de.mq.pattern.gof.command.TowerStopPositionControlImpl;
import de.mq.pattern.gof.command.ValveMock;
import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.LocationImpl;
import de.mq.pattern.gof.observer.Position;
import de.mq.pattern.gof.observer.PositionCalculator;
import de.mq.pattern.gof.observer.PositionCalculatorImpl;
import de.mq.pattern.gof.observer.gui.Locations;



public class GunControlTest {
	
	private PositionCalculator positionCalculator = new PositionCalculatorImpl();
	final Position position  = positionCalculator.position( new LocationImpl(new Number[] { 51, 19, 2 }, new Number[] { 6, 34, 16 }), Locations.DUE.location());
	
	@Test
	public final  void testStopPositionTower() {
		final List<String> commands = new ArrayList<String>();
		final GunControl gunControl = new TowerStopPositionControlImpl(new AngleSwitchMock(commands, "Tower") , 1d);
		
		
	   gunControl.execute(position);
	   
	  
	   Assert.assertEquals(2, commands.size());
	   Assert.assertEquals("Set scale AngleSwitchMock 1.0 for Tower", commands.get(0));
	   Assert.assertEquals("Set stop position AngleSwitchMock (-0.1199217899212146,0.9927833420752445) 97° for Tower", commands.get(1));
	}
	
	
	@Test
	public final  void testStopPositionElevation() {
		final List<String> commands = new ArrayList<String>();
		final GunControl gunControl = new ElevationStopPositionControlImpl(new AngleSwitchMock(commands, "Elevation") , 1d);
		
		
	   gunControl.execute(position);
	   Assert.assertEquals(2, commands.size());
	   Assert.assertEquals("Set scale AngleSwitchMock 1.0 for Elevation", commands.get(0));
	   
	   Assert.assertEquals("Set stop position AngleSwitchMock (0.9837501720560881,0.17954274972723655) 10° for Elevation", commands.get(1));
	  
	}
	
	@Test
	public final void testOpenValveControlTowerForward(){
		final List<String> commands = new ArrayList<String>();
		final GunControl gunControl = new TowerOpenValveControlImpl(new StatefullValveImpl(new ValveMock(commands, "Turn Right")), new StatefullValveImpl(new ValveMock(commands, "Turn Left")), new Angle() );
		
		gunControl.execute(position);
		
		Assert.assertEquals(1, commands.size());
		Assert.assertEquals("Open Valve Turn Right" , commands.get(0));
		
		
		
		
	}
	
	@Test
	public final void testOpenValveControlTowerInverse(){
		final List<String> commands = new ArrayList<String>();
		final GunControl gunControl = new TowerOpenValveControlImpl(new StatefullValveImpl(new ValveMock(commands, "Turn Right")), new StatefullValveImpl(new ValveMock(commands, "Turn Left")), new Angle(100*Math.PI/180) );
		
		gunControl.execute(position);
		
		Assert.assertEquals(1, commands.size());
		Assert.assertEquals("Open Valve Turn Left" , commands.get(0));
		
		
	}
	
	@Test
	public final void testElevationControlUp() {
		final List<String> commands = new ArrayList<String>();
		final GunControl gunControl = new ElevationOpenValveControlImpl(new StatefullValveImpl(new ValveMock(commands, "Gun Up")), new StatefullValveImpl(new ValveMock(commands, "Gun Down")), new Angle() );
		
		gunControl.execute(position);
		Assert.assertEquals(1, commands.size());
		Assert.assertEquals("Open Valve Gun Up" , commands.get(0));
		
	}
	
	@Test
	public final void testElevationControlDown() {
		final List<String> commands = new ArrayList<String>();
		final GunControl gunControl = new ElevationOpenValveControlImpl(new StatefullValveImpl(new ValveMock(commands, "Gun Up")), new StatefullValveImpl(new ValveMock(commands, "Gun Down")), new Angle(45*Math.PI/180) );
		
		gunControl.execute(position);
		
		Assert.assertEquals(1, commands.size());
		Assert.assertEquals("Open Valve Gun Down" , commands.get(0));
		
	}
	
	@Test
	public final void testCloseValves() {
		final List<String> commands = new ArrayList<String>();
		final StatefullValveImpl openValve = new StatefullValveImpl(new ValveMock(commands, "Gun Up"));
		openValve.open();
		commands.clear();
		final GunControl gunControl = new CloseValveControlImpl(openValve, new StatefullValveImpl( new ValveMock(commands, "Gun Down")) );
		gunControl.execute(position);
		Assert.assertEquals(1, commands.size());
		Assert.assertEquals("Close Valve Gun Up" , commands.get(0));
		
		
		
	}
	
	@Test(expected=IllegalStateException.class)
	public final void testForwardGuard() {
		final List<String> commands = new ArrayList<String>();
		final StatefullValveImpl openValve = new StatefullValveImpl(new ValveMock(commands, "Gun Up"));
		openValve.open();
		final GunControl gunControl = new ElevationOpenValveControlImpl(openValve, new StatefullValveImpl(new ValveMock(commands, "Gun Down")), new Angle(45*Math.PI/180) );
		gunControl.execute(position);
		
	}
	
	@Test(expected=IllegalStateException.class)
	public final void testInverseGuard() {
		final List<String> commands = new ArrayList<String>();
		final StatefullValveImpl openValve = new StatefullValveImpl(new ValveMock(commands, "Gun Down"));
		openValve.open();
		final GunControl gunControl = new ElevationOpenValveControlImpl( new StatefullValveImpl(new ValveMock(commands, "Gun Up")),openValve, new Angle() );
		gunControl.execute(position);
	}
	 
	

}
