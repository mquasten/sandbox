package de.mq.analysis.integration.support;

import org.springframework.util.Assert;

import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;


abstract class AbstractIntegrationService  implements IntegrationService{

	@Override
	public final double calculate(final DefiniteIntegral definiteIntegral) {
		
		inputParameterGuard(definiteIntegral);
	
		final double integral01 = resolveIntegral(definiteIntegral);
		final double integral02 = resolveIntegral(new DefiniteIntegralImpl(definiteIntegral.boundsOfIntegration(), definiteIntegral.realFunction(), 2* definiteIntegral.numberOfSamples()));
		Assert.isTrue(quality()>=1, "Quality should  at least 1." );
		final double k = Math.pow(2,quality()) -1 ; 
		return integral02+((integral02-integral01)/k);
	}
	
	
	
	void inputParameterGuard(final DefiniteIntegral definiteIntegral) {
	
	}

	abstract double resolveIntegral(DefiniteIntegral definiteIntegral);
	abstract int quality() ;
	

}
