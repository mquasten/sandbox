package de.mq.pattern.gof.proxy;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.proxy.BasicPersonServiceImpl;
import de.mq.pattern.gof.proxy.Boy;
import de.mq.pattern.gof.proxy.Girl;
import de.mq.pattern.gof.proxy.PersonService;


public class PersonServiceTest {
	
	private final PersonService personService = new BasicPersonServiceImpl();
	@Test
	public final void testCreateBoy() {
		Assert.assertEquals(Boy.class, personService.boy().getClass());
	}
	
	@Test
	public final void testCreateGirl() {
		Assert.assertEquals(Girl.class, personService.girl().getClass());
	}

}
