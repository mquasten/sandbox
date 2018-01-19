package de.mq.analysis.integration;

@FunctionalInterface
/**
 * A real function x, y element R
 * @author Admin
 *
 */
public interface RealFunction {
	/**
	 * The Result of the function
	 * @param x  the variable x in maths, t in engineering, physics 
	 * @return the result of the function
	 */
	double f(final double x);

}
