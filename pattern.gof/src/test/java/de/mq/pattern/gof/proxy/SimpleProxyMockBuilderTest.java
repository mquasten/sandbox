package de.mq.pattern.gof.proxy;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.proxy.SimpleDynamicProxyMockBuilder;
import de.mq.pattern.gof.strategy.IntegrationService;

public class SimpleProxyMockBuilderTest {
	
	@Test
	public final void testMockBuilder() {
		final SimpleDynamicProxyMockBuilder<IntegrationService> integrationServiceMockBuilder = new SimpleDynamicProxyMockBuilder<IntegrationService>().forClass(IntegrationService.class);
		integrationServiceMockBuilder.replay((double)1/3).area(0, 1, 100);	
		final IntegrationService integrationService = integrationServiceMockBuilder.mock();
		
		Assert.assertEquals((double)1/3, integrationService.area(0, 1, 100));
	}
	
	@Test(expected=IllegalStateException.class)
	public final void testMockBuilderMissingResult() {
		final IntegrationService integrationService = new SimpleDynamicProxyMockBuilder<IntegrationService>().forClass(IntegrationService.class).mock();
		
		Assert.assertEquals((double)1/3, integrationService.area(0, 1, 100));
	}

}
