package de.mq.analysis.integration.support;

public interface Result {
	
	/**
	 * result 
	 * @return the result for an operation
	 */
	double value();
	
	/** 
	 * error 
	 * @return the error for an operation 
	 */
	double error();

}
