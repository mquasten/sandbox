package de.mq.analysis.integration.support;

import java.util.Observable;

import org.springframework.util.Assert;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.Script;


class DefiniteIntegralAO  extends Observable{

	private  Double lowerLimit = null;
	private  Double upperLimit = null;
	

	private IntegrationService.CalculationAlgorithm calculationAlgorithm;
	

	private Double result; 

	
	private Long numberOfSamples =  1000000L; 
	
	
	private Script script  ; 
	




	Script getScript() {
		Assert.notNull(script, "Script is mandatory");
		return script;
	}




	boolean validate() {
		return lowerLimit!=null && upperLimit!=null && script != null && numberOfSamples != null && calculationAlgorithm != null;
	}
	
	
	long getNumberOfSamples() {
		Assert.notNull(numberOfSamples, "NumberOfSamples is mandatory");
		return numberOfSamples;
	}
	
	BoundsOfIntegration getBoundsOfIntegration(){
		Assert.notNull(lowerLimit, "LowerLimit is mandatory");
		Assert.notNull(upperLimit, "UpperLimit is mandatory");
		return new BoundsOfIntegrationImpl(lowerLimit, upperLimit);
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
	
	void setScript(final Script script){
		this.script=script;
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
	
	
	boolean hasResult(){
		return result!=null;
	}
	
	boolean hasScript() {
		return script != null;
	}
	
}
