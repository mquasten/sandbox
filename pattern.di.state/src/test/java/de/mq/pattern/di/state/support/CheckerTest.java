package de.mq.pattern.di.state.support;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.IntStream;

import org.junit.Test;

import junit.framework.Assert;

public class CheckerTest {
	
	private static final int MAX = 100;
	private Checker checker = new RandomCheckerImpl(0.33333d, 0.33333d);
	
	@Test
	public final void check() {
		final Collection<CheckResult> results = new HashSet<>();
		IntStream.rangeClosed(0, MAX).forEach(i -> results.add(checker.checkResult())) ;
		Assert.assertEquals(CheckResult.values().length, results.size());
		Arrays.asList(CheckResult.values()).forEach(cr -> Assert.assertTrue(results.contains(cr)));
		
	}

}
