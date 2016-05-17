package de.mq.pattern.di.state.support;

import java.util.Arrays;

import org.junit.Test;

import junit.framework.Assert;

public class CheckResultTest {
	
	@Test
	public final void checkresults() {
	
		Arrays.asList(CheckResult.values()).stream().forEach(cr -> Assert.assertEquals(cr, CheckResult.valueOf(cr.name())));
	}

}
