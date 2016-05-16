package de.mq.pattern.di.state.support;


class RandomCheckerImpl implements Checker{

	private final double successRange;
	
	public RandomCheckerImpl(double successRange, double failedRange) {
		this.successRange = successRange;
		this.failedRange = failedRange;
	}



	private final double failedRange;
	
	
	
	@Override
	public final CheckResult checkResult() {
		
		final double result = Math.random();
		
		if( result <= successRange ){
			return CheckResult.Success;
		}
		if( result <= successRange +failedRange ) {
			return CheckResult.Failed;
		}
		return CheckResult.Stay;
	}

}
