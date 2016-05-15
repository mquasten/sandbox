package de.mq.pattern.gof.state.checker;


public class RandomCheckerImpl implements Checker{

	@Override
	public final CheckResult checkResult() {
		
		final double result = Math.random();
		
		if( result <= 0.25 ){
			return CheckResult.Success;
		}
		if( result <= 0.5 ) {
			return CheckResult.Failed;
		}
		return CheckResult.Stay;
	}

}
