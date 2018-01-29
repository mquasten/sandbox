package de.mq.analysis.integration.support;

import de.mq.analysis.integration.BoundsOfIntegration;

public class BoundsOfIntegrationImpl implements BoundsOfIntegration{
	
	private final double lowerLimit;

	private final double upperLimit;
	
	public BoundsOfIntegrationImpl(final double lowerLimit, final double upperLimit) {
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}
	
	
	
	/* (non-Javadoc)
	 * @see de.mq.analysis.integration.BoundsOfIntegration#lowerLimit()
	 */
	@Override
	public final double lowerLimit() {
		return lowerLimit;
	}
	/* (non-Javadoc)
	 * @see de.mq.analysis.integration.BoundsOfIntegration#upperLimit()
	 */
	@Override
	public final double upperLimit() {
		return upperLimit;
	}
	

	
}
