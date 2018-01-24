package de.mq.analysis.integration;

@FunctionalInterface
/**
 * Calculate the result of a definite integral
 * @author Admin
 *
 */
public interface DefiniteIntegralCaculation {
	
	/**
	 * calculate the result of a definite integral
	 * @param definiteIntegral the definite Integral
	 * @return the result of the definite integral
	 */
	double calculate(final DefiniteIntegral definiteIntegral);

}
