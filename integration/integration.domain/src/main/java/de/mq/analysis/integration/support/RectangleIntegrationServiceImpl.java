package de.mq.analysis.integration.support;

import java.util.stream.LongStream;

import de.mq.analysis.integration.DefiniteIntegral;

public class RectangleIntegrationServiceImpl extends AbstractIntegrationService  {

	@Override
	public CalculationAlgorithm calculationAlgorithm() {
		return CalculationAlgorithm.Rectangle;
	}

	@Override
	double resolveIntegral(final DefiniteIntegral definiteIntegral) {
		final double h = definiteIntegral.stepSize() ;
		final double[] x  = { definiteIntegral.boundsOfIntegration().lowerLimit()- h/2};
		
		return h*(LongStream.range(0, definiteIntegral.numberOfSamples()  ).mapToDouble(i -> definiteIntegral.realFunction().f(x[0]+=h))).sum() ;
		
	
	}

	@Override
	int quality() {
		return 1;
	}
	
	

}
