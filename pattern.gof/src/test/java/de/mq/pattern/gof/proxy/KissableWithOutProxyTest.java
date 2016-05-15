package de.mq.pattern.gof.proxy;

import org.junit.Assert;
import org.junit.Test;

import de.mq.pattern.gof.proxy.Boy;
import de.mq.pattern.gof.proxy.Girl;
import de.mq.pattern.gof.proxy.Kissable;

public class KissableWithOutProxyTest {

	private final Kissable boy = new Boy();
	private final Kissable girl = new Girl();

	@Test
	public final void testBoyAndGirl() {
		Assert.assertEquals("I'm a boy, I kissed the girl", boy.kiss(girl));
	}

	@Test
	public final void testGirlAndBoy() {
		Assert.assertEquals("I'm a girl, I kissed the boy", girl.kiss(boy));
	}

	@Test
	public final void testGirlAndGirl() {
		Assert.assertEquals("I'm a girl, I kissed the girl", girl.kiss(girl));
	}

	@Test
	public final void testBoyAndBoy() {
		Assert.assertEquals("I'm a girl, I kissed the girl", girl.kiss(girl));
	}

}
