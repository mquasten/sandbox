package de.mq.pattern.gof.command;

import java.util.List;

public class AngleSwitchMock  extends AbstractMock implements AngleSwitch, Priority{

	public AngleSwitchMock(final List<String> commands, final String name) {
		super(commands, name);
	}

	private double k = 1;
	
	@Override
	public void scale(final double k) {
		
		commands.add("Set scale AngleSwitchMock " + k + " for " + name);
		this.k=k;
	}

	@Override
	public void stopPostion(final double re, final double im) {
		commands.add("Set stop position AngleSwitchMock (" + k* re +"," + k*im + ") "+Math.round(Math.atan2(im, re)*180/Math.PI)+"° for " + name);
	}

	@Override
	public void resetStopPostion() {
		commands.add("Reset stop position AngleSwitchMock for " + name);
	}

	@Override
	public int priority() {
		return 0;
	}

}
