package de.mq.analysis.integration.support;

import java.util.stream.LongStream;

import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;


class TrapezoidIntegrationImpl extends AbstractIntegrationService {
	

	double resolveIntegral(final DefiniteIntegral definiteIntegral) {
		final double h = definiteIntegral.stepSize() ;
		final double[] x  = { definiteIntegral.boundsOfIntegration().lowerLimit()};
		return (LongStream.range(0, definiteIntegral.numberOfSamples() -1 ).mapToDouble(i -> definiteIntegral.realFunction().f(x[0]+=h)).sum()  
		+ 0.5*( definiteIntegral.realFunction().f(definiteIntegral.boundsOfIntegration().lowerLimit())+ definiteIntegral.realFunction().f(definiteIntegral.boundsOfIntegration().upperLimit()))) * h;
	}

	@Override
	public CalculationAlgorithm calculationAlgorithm(){	
		return IntegrationService.CalculationAlgorithm.Trapezoid;
	}

	
	@Override
	int quality() {
		return 2;
	}

	
	





	

}
