package de.mq.pattern.gof.proxy;

import junit.framework.Assert;

import org.junit.Test;

import de.mq.pattern.gof.proxy.CGLIBProxyPersonServiceImpl;
import de.mq.pattern.gof.proxy.PersonService;

public class CglibProxyTest {

	private PersonService personService = new CGLIBProxyPersonServiceImpl();

	@Test
	public final void testBoyAndGirl() {
		Assert.assertEquals("I'm a boy, I kissed the girl", personService.boy().kiss(personService.girl()));
	}

	@Test
	public final void testGirlAndBoy() {
		Assert.assertEquals("I'm a girl, I kissed the boy", personService.girl().kiss(personService.boy()));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGirlAndGirl() {
		personService.girl().kiss(personService.girl());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testBoyAndBoy() {
		personService.girl().kiss(personService.girl());
	}

}
