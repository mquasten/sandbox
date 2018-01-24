package de.mq.analysis.integration.support;

import org.springframework.util.Assert;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.RealFunction;

class DefiniteIntegralImpl implements DefiniteIntegral{
	
	
	
	private final BoundsOfIntegration boundsOfIntegration;
	private final RealFunction realFunction;
	private final CalculationAlgorithm calculationAlgorithm;
	private final long numberOfSamples;
	
	DefiniteIntegralImpl(final BoundsOfIntegration boundsOfIntegration, final RealFunction realFunction, final CalculationAlgorithm calculationAlgorithm, final long numberOfSamples) {
		Assert.notNull(boundsOfIntegration, "boundsOfIntegration is mandatory.");
		Assert.notNull(realFunction, "RealFunction is mandatory.");
		Assert.notNull(calculationAlgorithm, "CalculationAlgorithm is mandatory.");
		Assert.isTrue(numberOfSamples > 0, "NumberOfSamples should be > 0.");
		this.numberOfSamples = numberOfSamples;
		this.boundsOfIntegration = boundsOfIntegration;
		this.realFunction = realFunction;
		this.calculationAlgorithm = calculationAlgorithm;
	}
	

	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.DefiniteIntegral#numberOfSamples()
	 */
	@Override
	public final long numberOfSamples() {
		return numberOfSamples;
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.DefiniteIntegral#boundsOfIntegration()
	 */
	@Override
	public BoundsOfIntegration boundsOfIntegration() {
		return boundsOfIntegration;
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.DefiniteIntegral#realFunction()
	 */
	@Override
	public RealFunction realFunction() {
		return realFunction;
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.DefiniteIntegral#calculationAlgorithm()
	 */
	@Override
	public CalculationAlgorithm calculationAlgorithm() {
		return calculationAlgorithm;
	}

}
