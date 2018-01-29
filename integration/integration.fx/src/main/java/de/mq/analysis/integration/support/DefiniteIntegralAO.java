package de.mq.analysis.integration.support;

import java.util.Observable;

import org.springframework.util.Assert;

import de.mq.analysis.integration.DefiniteIntegral;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.RealFunction;


class DefiniteIntegralAO  extends Observable{

	private  Double lowerLimit = null;
	private  Double upperLimit = null;
	
	private RealFunction realFunction=new RealFunctionImpl();

	private IntegrationService.CalculationAlgorithm calculationAlgorithm;
	

	private Double result; 

	
	private Long numberOfSamples =  100000L; 
	
	

	DefiniteIntegral getDefiniteIntegral() {
		Assert.notNull(lowerLimit, "LowerLimit is mandatory.");
		Assert.notNull(upperLimit, "UpperLimit is mandatory.");
		Assert.notNull(realFunction, "RealFunction is mandatory.");
		Assert.notNull(numberOfSamples, "NumberOfSamples is mandatory.");
		return new DefiniteIntegralImpl(new BoundsOfIntegrationImpl(lowerLimit, upperLimit), realFunction,  numberOfSamples);
		
	}
	
	boolean validate() {
		return lowerLimit!=null && upperLimit!=null && realFunction != null && numberOfSamples != null && calculationAlgorithm != null;
	}
	
	void setRealFunction(final RealFunction realFunction) {
		this.realFunction = realFunction;
	}
	
	
	void setNumberOfSamples(final Long numberOfSamples) {
		this.numberOfSamples = numberOfSamples;
	}
	
	void setCalculationAlgorithm(IntegrationService.CalculationAlgorithm calculationAlgorithm) {
		this.calculationAlgorithm = calculationAlgorithm;
	}
	
	IntegrationService.CalculationAlgorithm getCalculationAlgorithm() {
		Assert.notNull(calculationAlgorithm, "CalculationAlgorithm is mandatoy.");
		return calculationAlgorithm;
	}
	

	void setResult(final double result ) {
		this.result=result;
		setChanged();
		notifyObservers();
	}
	
	Double getResult() {
		return result;
	}
	
	void setLowerLimit(Double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	void setUpperLimit(Double upperLimit) {
		this.upperLimit = upperLimit;
	}
}
