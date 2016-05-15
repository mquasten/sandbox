package de.mq.pattern.gof.proxy;

import org.junit.Test;

import junit.framework.Assert;

public class GOFProxyTest {

	private final PersonService personService = new GOFKissProxyServiceImpl();
	
	
	
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
	public final void testBoysAndBoys() {
		personService.girl().kiss(personService.girl());
	}
	
}
