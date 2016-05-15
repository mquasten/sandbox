package de.mq.pattern.gof.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.command.GunControl;
import de.mq.pattern.gof.command.GunControlFactory;
import de.mq.pattern.gof.command.GunControlMacroFactoryImpl;
import de.mq.pattern.gof.command.GunControlMarcoImpl;
import de.mq.pattern.gof.command.Priority;
import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.LocationImpl;
import de.mq.pattern.gof.observer.Position;
import de.mq.pattern.gof.observer.PositionCalculatorImpl;
import de.mq.pattern.gof.observer.gui.Locations;

public class GunControlMarcoTest {
	
	
	private List<String> commands = new ArrayList<String>();
	
	
	final GunControlFactory gunControlFactory = new GunControlMacroFactoryImpl();
	
	
	final Position position  =  new PositionCalculatorImpl().position( new LocationImpl(new Number[] { 51, 19, 2 }, new Number[] { 6, 34, 16 }), Locations.DUE.location());
	
	@Test
	public final void testPriority() {
		
		final GunControl gunControl = gunControlFactory.create(commands, new Angle(), new Angle() );
		
		final List<GunControl> commands = ((GunControlMarcoImpl)gunControl).commands();
		int lastPriority=-1;
		for(final GunControl gc : commands){
			int priority = ((Priority)gc).priority();
			Assert.assertTrue(lastPriority <= priority);
			Assert.assertEquals(priority, priorityFromClass(gc.getClass().getSimpleName()));
			lastPriority=priority;
		}
		
	}
	
	
	@Test
	public final void testExecuteMarco() {
		final Angle towerAngle = new Angle();
		final Angle gunElevation = new Angle();
		final GunControl gunControl = gunControlFactory.create(commands, towerAngle, gunElevation);
		gunControl.execute(position);
		int time=0;
		final Map<Action,Integer>actions=new HashMap<Action,Integer>();
		for(String command : commands){
			
			
			final Action action = parseCommand(command);
			
			Assert.assertFalse(actions.containsKey(action));
			actions.put(action, time);
			time++;
		}
		
		for(final Action action : Action.values()){

			if ( ! action.isExpected()){
				Assert.assertFalse(actions.containsKey(action));
				continue;
			}
			Assert.assertTrue(actions.containsKey(action));
		}
		
		Assert.assertTrue(actions.get(Action.PositionTower) > actions.get(Action.ScaleTower));
		Assert.assertTrue(actions.get(Action.PositionElevation) > actions.get(Action.ScaleElevation));
		
		Assert.assertTrue(actions.get(Action.ValveTurn) > actions.get(Action.PositionTower));
		Assert.assertTrue(actions.get(Action.ValveGun) > actions.get(Action.PositionElevation));
		
		Assert.assertTrue(actions.get(Action.CloseValveGunUp) > actions.get(Action.ValveGun));
		Assert.assertFalse(actions.containsKey(Action.CloseValveGunDown) );
		
		Assert.assertTrue(actions.get(Action.CloseValveTurnRight) > actions.get(Action.ValveTurn));
		Assert.assertFalse(actions.containsKey(Action.CloseValveTurnLeft) );
		Assert.assertEquals(96, towerAngle.degrees());
		Assert.assertEquals(10, gunElevation.degrees());
		
	}
	
	
	public final Action parseCommand(final String text) {
		if( text.startsWith("Set scale AngleSwitchMock")&& text.endsWith("Tower")) {
			return Action.ScaleTower;
		}
		if( text.startsWith("Set scale AngleSwitchMock")&& text.endsWith("Gun")) {
			return Action.ScaleElevation;
		}
		if( text.startsWith("Set stop position AngleSwitchMock")&& text.endsWith("Tower")) {
			return Action.PositionTower;
		}
		if( text.startsWith("Set stop position AngleSwitchMock")&& text.endsWith("Gun")) {
			return Action.PositionElevation;
		}
		if( text.startsWith("Open Valve Turn") ){
			return Action.ValveTurn;
		}
		if( text.startsWith("Open Valve Gun") ){
			return Action.ValveGun;
		}
		if( text.startsWith("Close Valve Gun Up") ){
			return Action.CloseValveGunUp;
		}
		if( text.startsWith("Close Valve Gun Down") ){
			return Action.CloseValveGunDown;
		}
		if( text.startsWith("Close Valve Turn Right") ){
			return Action.CloseValveTurnRight;
		}
		if( text.startsWith("Close Valve Turn Left") ){
			return Action.CloseValveTurnLeft;
		}
		Assert.fail("No action for command " + text + " found");
		return null;
	}
	
	
	enum Action {
		ScaleTower(true), 
		ScaleElevation(true),
		PositionTower(true),
		PositionElevation(true),
		ValveTurn(true),
		ValveGun(true),
		CloseValveGunUp(true),
		CloseValveGunDown(false),
		CloseValveTurnRight(true),
		CloseValveTurnLeft(false);
		
		private final boolean expected;
		private Action(final boolean expected) {
			this.expected=expected;
		}
	
		boolean isExpected() {
			return expected;
		}
		
	}
	
	private int priorityFromClass(final String name) {
		
		if ( name.endsWith("StopPositionControlImpl")){
			return 0;
		}
		if ( name.endsWith("OpenValveControlImpl")){
			return 1;
		}
		if ( name.endsWith("CloseValveControlImpl")){
			return 2;
		}
		
		
		Assert.fail("No priority for class " + name + " found");
		return -1;
		
	}

}
