package de.mq.analysis.integration.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ResultTest {
	
	private static final Double ERROR = 1e-6;


	static final Double VALUE = 47.11d;
	
	private Result result = new ResultImpl(VALUE, ERROR);
	
	@Test
	public final void value() {
		assertEquals(VALUE, Double.valueOf(result.value()));
	}
	
	@Test
	public final void error() {
		assertEquals(ERROR, Double.valueOf(result.error()));
		
		System.out.println("*****************");
	}

}
