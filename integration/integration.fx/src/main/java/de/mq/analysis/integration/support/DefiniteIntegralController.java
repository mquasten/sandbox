package de.mq.analysis.integration.support;

import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.DefiniteIntegralCaculation;

class DefiniteIntegralController {
	
	final double integrate(final DefiniteIntegral definiteIntegral) {
		
		final DefiniteIntegralCaculation  definiteIntegralCaculation = definiteIntegral.calculationAlgorithm().definiteIntegralCaculation();
		
		return definiteIntegralCaculation.calculate(definiteIntegral);
		
	}
	
	

}
