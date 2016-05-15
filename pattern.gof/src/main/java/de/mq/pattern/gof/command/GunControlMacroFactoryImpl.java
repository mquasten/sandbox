package de.mq.pattern.gof.command;

import java.util.List;

import de.mq.pattern.gof.observer.Angle;



public class GunControlMacroFactoryImpl  implements GunControlFactory{


	
	@Override
	public GunControl create(final List<String> commands, final Angle towerAngle, final Angle gunElevation) {
		final StatefullValve towerRightValve = createValve(commands, "Turn Right");
		final StatefullValve towerLeftValve = createValve(commands, "Turn Left");
		final StatefullValve gunUpValve = createValve(commands, "Gun Up");
		final StatefullValve gunDownValve = createValve(commands, "Gun Down");
		return new GunControlMarcoImpl(new GunControl[] { new CloseValveControlImpl(gunUpValve, gunDownValve), new CloseValveControlImpl(towerRightValve, towerLeftValve),
	         new TowerStopPositionControlImpl(createSwitch(commands, "Angle Tower"), 1), new ElevationStopPositionControlImpl(createSwitch(commands, "Angle Gun"), 1),
	         new TowerOpenValveControlImpl(towerRightValve, towerLeftValve, towerAngle) , new ElevationOpenValveControlImpl(gunUpValve, gunDownValve, gunElevation )});
	}

	
	private final StatefullValve createValve(final List<String> commands , final String text) {
		return new StatefullValveImpl(new ValveMock(commands ,text)); 
	}
	
	private final AngleSwitch createSwitch(final List<String> commands , final String text){
		return new AngleSwitchMock(commands, text);
	}

}
