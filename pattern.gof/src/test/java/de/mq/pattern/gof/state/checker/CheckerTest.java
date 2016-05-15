package de.mq.pattern.gof.state.checker;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.state.checker.CheckResult;
import de.mq.pattern.gof.state.checker.Checker;

public class CheckerTest {
	
	static final  int MAX=1000;
	
	@Test
	public final void testStayOnStateChecker() {
		for(int i=0; i < MAX; i++){
			Assert.assertEquals(CheckResult.Stay, Checker.Checkers.StayOnStateChecker.checker().checkResult());
		}
	}
	@Test
	public final void testRandomChecker(){
		final Map<CheckResult,Integer> results = new HashMap<CheckResult,Integer>();
		
		for(int i=0; i < MAX; i++){
			final CheckResult result = Checker.Checkers.RandomChecker.checker().checkResult();
			if(!results.containsKey(result)){
				results.put(result, 0);
			}
			results.put(result, results.get(result)+1);
			
		}
		
		Assert.assertEquals(results.size(), CheckResult.values().length);
		for(final CheckResult checkResult : CheckResult.values()){
			Assert.assertTrue(results.containsKey(checkResult));
			Assert.assertTrue( results.get(checkResult) > MAX/10 );
		}
		
		
	}

}
