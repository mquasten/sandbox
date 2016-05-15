package de.mq.pattern.gof.strategy;

import org.junit.Assert;
import org.junit.Test;

import de.mq.pattern.gof.strategy.BeanFactory;
import de.mq.pattern.gof.strategy.BshRealFunctionImpl;
import de.mq.pattern.gof.strategy.Client;
import de.mq.pattern.gof.strategy.IntegrationService;
import de.mq.pattern.gof.strategy.Parabel;
import de.mq.pattern.gof.strategy.SimpleBeanFactoryImpl;
import de.mq.pattern.gof.strategy.SimpsonIntegrationServiceImpl;
import de.mq.pattern.gof.strategy.TrapezIntegrationServiceImpl;



public class SimpleBeanFactoryTest {
	
	 IntegrationService integrationService;
	
	@Test
	public final void testBeanFactorySimpson(){
		final BeanFactory beanFactory = new SimpleBeanFactoryImpl().withBeans(SimpsonIntegrationServiceImpl.class, Parabel.class, Client.class);
		
		beanFactory.build();
		
		final Client client =   (Client) beanFactory.bean(Client.class);
		
		
		Assert.assertEquals(3333, Math.round(client.calculateArea(0, 1, 100) *1e4));
		
	}
	
	@Test
	public final void testBeanFactoryRechteck(){
		final BeanFactory beanFactory = new SimpleBeanFactoryImpl().withBeans(TrapezIntegrationServiceImpl.class, Parabel.class, Client.class);
		
		beanFactory.build();
		
		final Client client =   (Client) beanFactory.bean(Client.class);
		Assert.assertEquals(3333, Math.round(client.calculateArea(0, 1, 1000) *1e4));
	}
	
	
	@Test
	public final void testBeanFactoryWithObjects(){
		final BeanFactory beanFactory = new SimpleBeanFactoryImpl().withBeans(new SimpsonIntegrationServiceImpl(), new Parabel() , new Client() );
	
		beanFactory.build();
		final Client client =   (Client) beanFactory.bean(Client.class);
		
		Assert.assertEquals(3333, Math.round(client.calculateArea(0, 1, 1000) *1e4));
	}
	
	@Test
	public final void testBeanFactoryRechteckBsh(){
		final BeanFactory beanFactory = new SimpleBeanFactoryImpl().withBeans(TrapezIntegrationServiceImpl.class,  Client.class).withBeans(new BshRealFunctionImpl("x*x", "x"));
		
		beanFactory.build();
		
		final Client client =   (Client) beanFactory.bean(Client.class);
		Assert.assertEquals(3333, Math.round(client.calculateArea(0, 1, 1000) *1e4));
	}
	
	@Test
	public final void testBeanFactorySimpsonBsh(){
		final BeanFactory beanFactory = new SimpleBeanFactoryImpl().withBeans(SimpsonIntegrationServiceImpl.class,  Client.class).withBeans(new BshRealFunctionImpl("x*x", "x"));
		
		beanFactory.build();
		
		final Client client =   (Client) beanFactory.bean(Client.class);
		Assert.assertEquals(3333, Math.round(client.calculateArea(0, 1, 1000) *1e4));
	}
	

}
