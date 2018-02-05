package de.mq.analysis.integration.support;

import org.junit.Assert;
import org.junit.Test;


public class ResultTest {
	
	private static final Double ERROR = 1e-6;


	static final Double VALUE = 47.11d;
	
	private Result result = new ResultImpl(VALUE, ERROR);
	
	@Test
	public final void value() {
		Assert.assertEquals(VALUE, Double.valueOf(result.value()));
	}
	
	@Test
	public final void error() {
		Assert.assertEquals(ERROR, Double.valueOf(result.error()));
	}

}
