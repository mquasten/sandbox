package de.mq.pattern.gof.command;

public interface AngleSwitch {
	
	
	void scale(final double k);
	
	void stopPostion(final double re, final double im);
	
	void resetStopPostion();

}
