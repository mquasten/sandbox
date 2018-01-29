package de.mq.analysis.integration;

public interface DefiniteIntegral {
	long numberOfSamples();
	
	BoundsOfIntegration boundsOfIntegration();
	RealFunction realFunction();
	
	
	

	default double stepSize() {
		return (boundsOfIntegration().upperLimit() - boundsOfIntegration().lowerLimit()) / numberOfSamples();
	}


}
