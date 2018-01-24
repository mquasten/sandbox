package de.mq.analysis.integration;

import de.mq.analysis.integration.support.CalculationAlgorithm;

public interface DefiniteIntegral {
	long numberOfSamples();
	
	BoundsOfIntegration boundsOfIntegration();
	RealFunction realFunction();
	CalculationAlgorithm calculationAlgorithm();
	
	

	default double stepSize() {
		return (boundsOfIntegration().upperLimit() - boundsOfIntegration().lowerLimit()) / numberOfSamples();
	}


}
