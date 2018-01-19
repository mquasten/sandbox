package de.mq.analysis.integration;

@FunctionalInterface
/**
 * Calculate the result of a definite integral
 * @author Admin
 *
 */
public interface DefiniteIntegral {
	
	/**
	 * The result of a definite integral
	 * @param boundsOfIntegration lower and upper limit for the definite integral
	 * @return the result for the definite integral
	 */
	double calculate(final BoundsOfIntegration boundsOfIntegration);

}
