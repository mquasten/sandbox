package de.mq.pattern.gof.state.checker;


public interface Checker {
	
	CheckResult checkResult();
	
	
	
	
	public enum Checkers {
		StayOnStateChecker( new Checker() {

			@Override
			public CheckResult checkResult() {
				
				return CheckResult.Stay;
			} }) ,
		RandomChecker( new RandomCheckerImpl());
		
		
		private final Checker checker;
		Checkers(final Checker checker) {
			this.checker=checker;
		}
		
		public Checker checker(){
			return checker;
		}
	}
	

}
