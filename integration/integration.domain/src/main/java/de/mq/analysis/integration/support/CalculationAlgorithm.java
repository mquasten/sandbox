package de.mq.analysis.integration.support;

import org.springframework.beans.BeanUtils;

import de.mq.analysis.integration.DefiniteIntegralCaculation;

public enum  CalculationAlgorithm {
	Trapezoid(TrapezoidIntegrationImpl.class),
	Simpson(SimpsonIntegrationImpl.class); 
	
	 
	
	final Class<? extends DefiniteIntegralCaculation> clazz; 
	CalculationAlgorithm(Class<? extends DefiniteIntegralCaculation> clazz) {
		this.clazz=clazz;
	}
	
	
	
	DefiniteIntegralCaculation definiteIntegralCaculation() {
		return  BeanUtils.instantiateClass(clazz);
	}
	
}
