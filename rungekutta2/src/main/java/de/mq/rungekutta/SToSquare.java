package de.mq.rungekutta;

@FunctionalInterface
/**
 * 
 * @author mq
 * 
 * 
 * time domain: y°°= f(t,y, y°)
 * complex variable domain: s^2 = f(t,y,s)
 */

public interface SToSquare {
	
	/**
	 * y°°= f(t,y, y°) / s^2 = f(t,y,s)
	 * @param solutionVector input/time value
	 
	 * @return second derivation
	 */
	double resolve(final SolutionVector solutionVector);

}
