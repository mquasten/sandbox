package de.mq.analysis.integration.support;

import java.util.stream.LongStream;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.RealFunction;

class TrapezoidIntegrationImpl implements DefiniteIntegral {
	
	
	private final long n;
	private final RealFunction realFunction;
	
	public TrapezoidIntegrationImpl(final long n, final RealFunction realFunction ) {
		if( n  < 2 ) {
			throw new IllegalArgumentException("n should be greather than 1.");
		}
		this.n = n;
		this.realFunction=realFunction;
	}






	@Override
	public double calculate(BoundsOfIntegration boundsOfIntegration) {
		final double h = delta(boundsOfIntegration) ;
		final double[] x  = { boundsOfIntegration.lowerLimit()};
		return (LongStream.range(0, n -1 ).mapToDouble(i -> realFunction.f(x[0]+=h)).sum()  
		+ 0.5*( realFunction.f(boundsOfIntegration.lowerLimit())+ realFunction.f(boundsOfIntegration.upperLimit()))) * h;
	}

	
	





	private double delta(BoundsOfIntegration boundsOfIntegration) {
		return (boundsOfIntegration.upperLimit() - boundsOfIntegration.lowerLimit()) /n;
	}

}
