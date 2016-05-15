package de.mq.pattern.gof.strategy;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.strategy.BshRealFunctionImpl;
import de.mq.pattern.gof.strategy.RealFunction;

public class BshFunctionTest {
	
	@Test
	public final void testFunction(){
		RealFunction realFunction = new BshRealFunctionImpl("x*x", "x");
		Assert.assertEquals(16d, realFunction.result(4));
	}

}
