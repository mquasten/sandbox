package de.mq.analysis.integration.support;

import org.junit.Assert;
import org.junit.Test;

import de.mq.analysis.integration.RealFunction;
import de.mq.analysis.integration.support.RealFunctionImpl;


public class RealFunctionTest {
	
	private final RealFunction realFunction = new RealFunctionImpl();
	
	@Test
	public final void f() {
		Assert.assertEquals(Double.valueOf(1d/Math.E), Double.valueOf(realFunction.f(1d)));
	}
	
	@Test
	public final void f0(){
		Assert.assertEquals(Double.valueOf(1d), Double.valueOf(realFunction.f(0)));
	}

}
