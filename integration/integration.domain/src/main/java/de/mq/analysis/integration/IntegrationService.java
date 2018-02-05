package de.mq.analysis.integration;

import de.mq.analysis.integration.support.Result;

/**
 * Calculate the result of a definite integral
 * @author Admin
 *
 */
public interface IntegrationService {
	
	
	/**
	 * Identification CalculationAlgorithm 
	 * @author Admin
	 *
	 */
	public enum  CalculationAlgorithm {
		Trapezoid,
		Simpson ; 
	}
	
	
	/**
	 * calculate the result of a definite integral
	 * @param definiteIntegral the definite Integral
	 * @return the result of the definite integral
	 */
	Result calculate(final DefiniteIntegral definiteIntegral);
	
	
	/**
	 * Identification of the  calculationAlgorithm for the Service
	 * @return
	 */
	CalculationAlgorithm calculationAlgorithm();
}
