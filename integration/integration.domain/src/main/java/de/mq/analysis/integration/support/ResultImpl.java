package de.mq.analysis.integration.support;

class ResultImpl implements Result{
	
	
	private final double value;
	
	private final double error;
	
	ResultImpl(final double value, final double error) {
		this.value = value;
		this.error = error;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.support.Result#value()
	 */
	@Override
	public final double value() {
		return value;
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.analysis.integration.support.Result#error()
	 */
	@Override
	public final double error() {
		return error;
	}
	

}
