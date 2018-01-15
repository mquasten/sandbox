package de.mq.analysis.integration;

/**
 * Bounds for the integration, lower and upper limit
 * @author Admin
 *
 */
public interface BoundsOfIntegration {

	/**
	 * the lowerLimit
	 * @return lower limit for the definite integral
	 */
	double lowerLimit();

	/**
	 * the upperLimit
	 * @return upper limit for the definite integral
	 */
	double upperLimit();
	
	/**
	 * an instance of BoundsOfIntegration with the given upper and lower limit
	 * @param lowerLimit the lower limit for the definite integral 
	 * @param upperLimit  the upper limit for the definite integral 
	 * @return an instance of BoundsOfIntegration with the given upper and lower limit
	 */
	public static BoundsOfIntegration of(final double lowerLimit, final double upperLimit) {
		return new BoundsOfIntegrationImpl(lowerLimit, upperLimit) ;
	}

}