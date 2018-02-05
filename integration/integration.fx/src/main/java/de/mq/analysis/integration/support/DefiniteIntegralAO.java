package de.mq.analysis.integration.support;

import java.util.Observable;
import java.util.Observer;

import org.springframework.util.Assert;

import de.mq.analysis.integration.BoundsOfIntegration;
import de.mq.analysis.integration.IntegrationService;
import de.mq.analysis.integration.Script;


class DefiniteIntegralAO  extends Observable implements Observer{

	private  Double lowerLimit = null;
	private  Double upperLimit = null;
	

	private IntegrationService.CalculationAlgorithm calculationAlgorithm;
	

	private Result result; 

	
	private Long numberOfSamples =  1000000L; 
	
	
	private Script script  ; 
	


	private String errorMessage; 


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
	

	void setResult(final Result result ) {
		this.result=result;
		this.errorMessage=null;
		setChanged();
		notifyObservers();
	}
	
	void setScript(final Script script){
		this.script=script;
		this.errorMessage=null;
		setChanged();
		notifyObservers();
	}
	
	void setErrorMessage(final String errorMessage) {
		this.errorMessage=errorMessage;
		setChanged();
		notifyObservers();
	}
	
	String getErrorMessage() {
		return errorMessage;
	}
	
	Result getResult() {
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




	@Override
	public void update(final Observable observable, final Object arg) {
		setScript(((ScriptAO) observable).getSelectedScript());	
		setResult(null);
	}
	
}
