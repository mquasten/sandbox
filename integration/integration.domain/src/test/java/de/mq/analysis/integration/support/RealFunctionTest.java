package de.mq.analysis.integration.support;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.mq.analysis.integration.RealFunction;
import de.mq.analysis.integration.support.RealFunctionImpl;

public class RealFunctionTest {

	private final RealFunction realFunction = new RealFunctionImpl();

	@Test
	public final void f() {
		assertEquals(Double.valueOf(1d / Math.E), Double.valueOf(realFunction.f(1d)));
	}

	@Test
	public final void f0() {
		assertEquals(Double.valueOf(1d), Double.valueOf(realFunction.f(0)));
	}

}
