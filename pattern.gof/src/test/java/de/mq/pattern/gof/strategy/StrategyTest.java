package de.mq.pattern.gof.strategy;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.strategy.BshRealFunctionImpl;
import de.mq.pattern.gof.strategy.Client;
import de.mq.pattern.gof.strategy.Parabel;
import de.mq.pattern.gof.strategy.SimpsonIntegrationServiceImpl;
import de.mq.pattern.gof.strategy.TrapezIntegrationServiceImpl;

public class StrategyTest {
	
	 
	
	@Test
	public void testTrapez(){
		
		final Client client = new Client(new TrapezIntegrationServiceImpl(new Parabel()));
		double result = client.calculateArea(0, 1, 1000);
		Assert.assertEquals(3333, Math.round(1e4 *result ));
		
	}
	@Test
	public void testSimpson(){
		final Client client = new Client(new SimpsonIntegrationServiceImpl(new Parabel()));
		double result = client.calculateArea(0, 1, 1000);
		Assert.assertEquals(3333333, Math.round(result*1e7));
	}
	
	@Test
	public void testTrapezWithBsh(){
		
		final Client client = new Client(new TrapezIntegrationServiceImpl(new BshRealFunctionImpl("x*x", "x")));
		double result = client.calculateArea(0, 1, 1000);
		Assert.assertEquals(3333, Math.round(1e4 *result ));
		
	}
	
	@Test
	public void testSimpsonWithBsh(){
		final Client client = new Client(new SimpsonIntegrationServiceImpl(new BshRealFunctionImpl("x*x", "x")));
		double result = client.calculateArea(0, 1, 1000);
		Assert.assertEquals(3333333, Math.round(result*1e7));
	}

}
