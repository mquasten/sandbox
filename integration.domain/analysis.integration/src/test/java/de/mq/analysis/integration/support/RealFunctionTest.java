package de.mq.analysis.integration.support;

import org.junit.Test;

import de.mq.analysis.integration.RealFunction;
import de.mq.analysis.integration.support.RealFunctionImpl;
import junit.framework.Assert;

public class RealFunctionTest {
	
	private final RealFunction realFunction = new RealFunctionImpl();
	
	@Test
	public final void f() {
		Assert.assertEquals(Math.E, realFunction.f(1d));
	}
	
	@Test
	public final void f0(){
		Assert.assertEquals(1d, realFunction.f(0));
	}

}
