package de.mq.analysis.integration.support;

import java.util.stream.LongStream;

import org.springframework.util.Assert;

import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;

class SimpsonIntegrationServiceImpl implements IntegrationService {

	@Override
	public double calculate(final DefiniteIntegral definiteIntegral) {
		
		Assert.isTrue(definiteIntegral.numberOfSamples() %2 ==0, "Number of samples should be divisible by  2.");
		
		final double integral01 = simpson(definiteIntegral);
		final double integral02 = simpson(new DefiniteIntegralImpl(definiteIntegral.boundsOfIntegration(), definiteIntegral.realFunction(), 2* definiteIntegral.numberOfSamples()));
		return integral02+((integral02-integral01)/15d);
	}


	private double simpson(final DefiniteIntegral definiteIntegral) {
		final double[] x  = { definiteIntegral.boundsOfIntegration().lowerLimit()};
		final double h = definiteIntegral.stepSize() ;
		double sum = definiteIntegral.realFunction().f(definiteIntegral.boundsOfIntegration().lowerLimit()) + definiteIntegral.realFunction().f(definiteIntegral.boundsOfIntegration().upperLimit())+ LongStream.range(1, definiteIntegral.numberOfSamples()).mapToDouble(i -> {
			
			return  k(i) * definiteIntegral.realFunction().f(x[0]+=h); 
			
			
		}).sum();
		
		
		
		return sum * h/3;
	}

	
	private double k(long i) {
		
		return i%2==0 ? 2d:4d;
		
	}




	@Override
	public CalculationAlgorithm calculationAlgorithm() {
		return IntegrationService.CalculationAlgorithm.Simpson;
	}
}
